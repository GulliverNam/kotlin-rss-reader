package rssreader.model

import java.time.LocalDateTime

data class Post(
    val title: String,
    val link: String,
    val pubData: LocalDateTime,
)

fun List<Post>.noticeNewPost(oldPosts: List<Post>) {
    if (oldPosts.isNotEmpty() && this[0].pubData != oldPosts[0].pubData) {
        var index = 0
        while (this[index].pubData == oldPosts[0].pubData) {
            println("새로운 글이 등록되었습니다!")
            println("[NEW] ${this[index].title} (${this[index].pubData}) - ${this[index].link}")
            index++
        }
    }
}
