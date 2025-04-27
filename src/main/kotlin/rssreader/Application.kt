package rssreader

import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import rssreader.model.Channel

fun main() =
    runBlocking {
        val channels =
            listOf(
                Channel("https://aws.amazon.com/ko/blogs/aws/feed/"),
                Channel("https://developers.hyundaimotorgroup.com/blog/rss"),
                Channel("https://techblog.woowahan.com/feed"),
            )

        while (isActive) { // while(true) -> 외부에서 job 을 cancel 시켜도 종료되지 않음
            println("검색어를 입력하세요 (없으면 전체 출력):")
            val keyword = readln()

            channels
                .flatMap { it.findPosts() }
                .toMutableList()
                .apply { this.sortByDescending { it.pubData } }
                .filter { it.title.contains(keyword) }
                .take(10)
                .forEachIndexed { index, post -> println("[${index + 1}] ${post.title} (${post.pubData}) - ${post.link}") }
        }
    }
