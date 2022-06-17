package com.android.retrofitsampleapp2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //создаем Retrofit
    private final Retrofit retrofit = new Retrofit.Builder()
            //это начало адреса которая будет подставлятся для открытия списка пользователя и их репозитория
            .baseUrl("https://api.github.com/")
            .build();

    // создали gitHubApi для обращения к нему (create - творить). GitHubApi.class - тип данных который нужно создать
    private GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //вызываем проект (репозиторий) кнкретного пользователя. Вызов идет ассинхронно в том же потоке
        // (enqueue - ставить в очередь в () передаем коллбэк, execute - выполнять).
        gitHubApi.getProject("borhammere");
    }
}