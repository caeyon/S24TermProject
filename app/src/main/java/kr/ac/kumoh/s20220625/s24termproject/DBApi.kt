package kr.ac.kumoh.s20220625.s24termproject

import retrofit2.http.GET
import retrofit2.http.Query

interface DBApi {
    @GET("author")
    suspend fun getAuthors(
        @Query("apikey") apiKey: String = DBApiConfig.API_KEY //여기에 진짜 apikey 넣는 거 아님.. 나도 알고 싶지 않았다..하 씨벌
    ): List<Author>

//    @GET("webtoon")
//    suspend fun getWebtoons(
//        @Query("apikey") apiKey: String = DBApiConfig.API_KEY //여기에 진짜 apikey 넣는 거 아님.. 나도 알고 싶지 않았다..하 씨벌
//    ): List<Webtoon>
}