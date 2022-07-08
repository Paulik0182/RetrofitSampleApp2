package com.android.retrofitsampleapp2

import android.app.Application
import com.android.retrofitsampleapp2.data.GitHubApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    //увеличили время по таймауту при загрузке из сети (без этого, по умолчанию 10сек.)
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client) // увеличение времени по таймауту
        .addConverterFactory(GsonConverterFactory.create()) // это приобразователь объектов из одного типа в другой тип (здесь старонняя библиотека)
        .build()

    val gitHubApi =
        retrofit.create(GitHubApi::class.java) //создаем gitHubApi. Автоматически обратится к интерфейсу
}