package com.android.retrofitsampleapp2.data

import com.android.retrofitsampleapp2.domain.GitProjectEntity
import com.android.retrofitsampleapp2.domain.GitUserEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Здесь мы описываем простейшее App.
 * Call - это звонок. То-есть мы получаем список сущьностей.
 * GET("users") это анатации которые потом превращает интерфейс в реальные объекты. это GET запрос.
 * Path("user") String user) - это переменная которая является частью пути.
 *
 *
 * GitUserEntity - класс описывающий сущность, пользователя
 *
 *
 * GitProjectEntity - класс описывающий сущность (проект, репозиторий)
 */
interface GitHubApi {
    //здесь мы получем список всех пользователей. Результат - https://api.github.com/users
    @GET("users")
    fun getUsers(): Call<List<GitUserEntity>>

    //это конкретный запрос на репозиторий. Результат - https://api.github.com/users/borhammere/repos
    @GET("users/{user}/repos")
    fun getProject(@Path("user") user: String): Call<List<GitProjectEntity>>
}