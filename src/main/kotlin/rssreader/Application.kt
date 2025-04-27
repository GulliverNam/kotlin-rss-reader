package rssreader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rssreader.model.Channel
import rssreader.model.Post
import rssreader.model.noticeNewPost
import java.time.Duration

fun main(): Unit =
    runBlocking(SupervisorJob()) {
        val channels =
            listOf(
                Channel("https://aws.amazon.com/ko/blogs/aws/feed/"),
                Channel("https://developers.hyundaimotorgroup.com/blog/rss"),
                Channel("https://techblog.woowahan.com/feed"),
            )

        var posts = listOf<Post>()

        launch(Dispatchers.IO) {
            while (isActive) {
                if (posts.isNotEmpty()) {
                    println("검색어를 입력하세요 (없으면 전체 출력):")
                    val keyword = readln()
                    posts
                        .filter { it.title.contains(keyword) }
                        .take(10)
                        .forEachIndexed { index, post -> println("[${index + 1}] ${post.title} (${post.pubData}) - ${post.link}") }
                        .apply { println() }
                }
            }
        }

        launch {
            while (isActive) { // while(true) -> 외부에서 job 을 cancel 시켜도 종료되지 않음
                posts =
                    channels
                        .flatMap { it.findPosts() }
                        .toMutableList()
                        .apply {
                            sortByDescending { it.pubData }
                            noticeNewPost(posts)
                        }.toMutableList()
                delay(Duration.ofMinutes(10).toMillis())
            }
        }
    }
