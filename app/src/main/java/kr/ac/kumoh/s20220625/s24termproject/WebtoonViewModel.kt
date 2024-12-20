package kr.ac.kumoh.s20220625.s24termproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebtoonViewModel : ViewModel() {
    private val dBApi: DBApi
    private val webtoonModel: WebtoonModel

    private val _authorList = MutableLiveData<List<Author>>()
    val authorList: LiveData<List<Author>>
        get() = _authorList

    private val _webtoonList = MutableLiveData<List<Webtoon>>()
    val webtoonList: LiveData<List<Webtoon>>
        get() = _webtoonList

    private val _webtoonInfoList = MutableLiveData<List<WebtoonInfo>>()
    val webtoonInfoList: LiveData<List<WebtoonInfo>>
        get() = _webtoonInfoList

    init { //처음에 ViewModel 생길 때 실행됨
        val retrofit = Retrofit.Builder()
            .baseUrl(DBApiConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dBApi = retrofit.create(DBApi::class.java)
        webtoonModel = WebtoonModel(dBApi)
        fetchData()
    }

    private fun fetchData() {
        // Coroutine 사용
        viewModelScope.launch {
            try {
                _authorList.value = webtoonModel.getAuthors()
                _webtoonList.value = webtoonModel.getWebtoons()
                _webtoonInfoList.value = webtoonModel.getWebtoonInfo()
            } catch(e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}