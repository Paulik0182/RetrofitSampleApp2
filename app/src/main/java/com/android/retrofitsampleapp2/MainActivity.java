package com.android.retrofitsampleapp2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GitHubApi gitHubApi = null; // создали gitHubApi для обращения к нему

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //вызываем проект (репозиторий) кнкретного пользователя. Вызов идет ассинхронно в том же потоке
        // (enqueue - ставить в очередь в () передаем коллбэк, execute - выполнять).
        gitHubApi.getProject("borhammere").enqueue();
    }
}