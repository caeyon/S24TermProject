package kr.ac.kumoh.s20220625.s24termproject

import retrofit2.http.GET
import retrofit2.http.Query

interface DBApi {
    @GET("author")
    suspend fun getAuthors(
        @Query("apikey") apiKey: String = DBApiConfig.API_KEY
    ): List<Author>

    @GET("webtoon")
    suspend fun getWebtoons(
        @Query("apikey") apiKey: String = DBApiConfig.API_KEY
    ): List<Webtoon>

    @GET("webtooninfo")
    suspend fun getWebtoonInfo(
        @Query("apikey") apiKey: String = DBApiConfig.API_KEY
    ): List<WebtoonInfo>
}