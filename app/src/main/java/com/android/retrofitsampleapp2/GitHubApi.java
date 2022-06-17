package com.android.retrofitsampleapp2;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Здесь мы описываем простейшее App.
 * Call - это звонок. То-есть мы получаем список сущьностей.
 * GET("users") это анатации которые потом превращает интерфейс в реальные объекты. это GET запрос.
 * Path("user") String user) - это переменная которая является частью пути.
 * <p>
 * GitUserEntity - класс описывающий сущность, пользователя
 * <p>
 * GitProjectEntity - класс описывающий сущность (проект, репозиторий)
 */

public interface GitHubApi {

    //здесь мы получем список всех пользователей. Результат - https://api.github.com/users
    @GET("users")
    Call<List<GitUserEntity>> getUsers();

    //это конкретный запрос на репозиторий. Результат - https://api.github.com/users/borhammere/repos
    @GET("users/{user}/repos")
    Call<List<GitProjectEntity>> getProject(@Path("user") String user);
}
