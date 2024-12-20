package kr.ac.kumoh.s20220625.s24termproject

data class WebtoonInfo(
    val id: Int,
    val webtoonid: Int,
    val genre: String,
    val favorites: Int,
    val status: String,
    val updateschedule: String,
    val description: String?,
    val webtoonurl: String,
)
