package kr.ac.kumoh.s20220625.s24termproject

class WebtoonModel(private val dBApi: DBApi) {

    suspend fun getAuthors(): List<Author> {
        return dBApi.getAuthors()
    }

    suspend fun getWebtoons(): List<Webtoon> {
        return dBApi.getWebtoons()
    }

    suspend fun getWebtoonInfo(): List<WebtoonInfo> {
        return dBApi.getWebtoonInfo()
    }
}