package rssreader.model

import java.time.LocalDateTime

data class Post(
    val title: String,
    val link: String,
    val pubData: LocalDateTime,
)
