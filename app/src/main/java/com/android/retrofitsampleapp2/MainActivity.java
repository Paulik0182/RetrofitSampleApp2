package com.android.retrofitsampleapp2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        //вызываем проект (репозиторий) конкретного пользователя. Вызов идет ассинхронно в том же потоке
        // (enqueue - ставить в очередь в () передаем Callback, execute - выполнять).
        // Callback состоит из двух методов: onResponse - получение ответа; onFailure - получение ошибки (нет сети ошибка сервера, DNS и т.д.).
        gitHubApi.getProject("borhammere").enqueue(new Callback<List<GitProjectEntity>>() {
            @Override
            public void onResponse(Call<List<GitProjectEntity>> call, Response<List<GitProjectEntity>> response) {
//                if (response.code()==200){  //проверка кода, код 200 означает успех
                    if (response.isSuccessful()) { //isSuccessful - это уже проверка кодов от 200 до 300
                        List<GitProjectEntity> projects = response.body(); // body - это тело запроса, это будет список репозиториев которые мы ищем. Здесь мы получаем список проектов

                    } else {
                        Toast.makeText(MainActivity.this, "Error code" + response.code(), Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<List<GitProjectEntity>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}