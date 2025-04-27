package rssreader.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import org.w3c.dom.Element
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

class Channel(
    private val link: String,
) {
    suspend fun findPosts(): List<Post> =
        withContext(Dispatchers.IO) {
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = builder.parse(link)
            val node = document.getElementsByTagName("channel").item(0)
            val items =
                List(node.childNodes.length) { node.childNodes.item(it) }
                    .filterIsInstance<Element>()
                    .filter { it.tagName == "item" }

            withContext(Dispatchers.Default) {
                items
                    .map {
                        async {
                            Post(
                                it.textOf("title"),
                                it.textOf("link"),
                                LocalDateTime.parse(it.textOf("pubDate"), DateTimeFormatter.RFC_1123_DATE_TIME),
                            )
                        }
                    }.awaitAll()
                    .toMutableList()
            }
        }

    private fun Element.textOf(tagName: String): String = getElementsByTagName(tagName).item(0)?.textContent.orEmpty()
}
