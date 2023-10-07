package com.example.projectapi.Activities

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.projectapi.Api.ApiRequest
import com.example.projectapi.databinding.ActivityEmailBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class Email : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.btnNext.setOnClickListener()
        {
            SendEmail()
        }
    }

    fun SendEmail() {
        //interceptor - Перехватчик OkHttp, который регистрирует данные HTTP-запроса и ответа.
        //Создаем объект перехватчика
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        //Добавляем перехватчика в клиента
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())//Конвертер строк, обоих примитивов и их коробочных типов в text/plain тела.
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl("https://iis.ngknn.ru/NGKNN/МамшеваЮС/MedicMadlab/")
            .client(httpClient) //добавление http клиента в retrofit
            .build()
        val requestApi = retrofit.create(ApiRequest::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                requestApi.postEmail(binding.emailtext.text.toString())
                    .awaitResponse() //Запуск метода из ApiRequest с переданым параметром в Header (заголовок)
                Log.d("Response", "Success send Email") // Вывод информации на консоль
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString()) // Вывод информации на консоль
            }
        }
    }
}