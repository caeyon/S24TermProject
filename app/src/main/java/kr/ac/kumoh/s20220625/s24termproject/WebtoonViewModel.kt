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
    private val _authorList = MutableLiveData<List<Author>>()
    val authorList: LiveData<List<Author>>
        get() = _authorList

    init { //처음에 ViewModel 생길 때 실행됨
        val retrofit = Retrofit.Builder()
            .baseUrl(DBApiConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dBApi = retrofit.create(DBApi::class.java)
        fetchData() //서버의 데이터 가져오는 함수
    }

    private fun fetchData() {
        // Coroutine 사용
        viewModelScope.launch {
            try {
                val response = dBApi.getAuthors()
                _authorList.value = response
            } catch(e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}