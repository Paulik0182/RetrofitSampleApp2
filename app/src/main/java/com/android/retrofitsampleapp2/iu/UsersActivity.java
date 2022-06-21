package com.android.retrofitsampleapp2.iu;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.data.GitHubApi;
import com.android.retrofitsampleapp2.domain.GitUserEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient
            .Builder()// каждый раз вызова метода билдора будет возвращать его самого
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();

    //создаем Retrofit
    private final Retrofit retrofit = new Retrofit.Builder()
            //это начало адреса которая будет подставлятся для открытия списка пользователя и их репозитория
            .client(client)//сдесь можно передать например настройки
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())// это приобразователь объектов из одного типа в другой тип (здесь старонняя библиотека)
            .build();

    // создали gitHubApi для обращения к нему (create - творить). GitHubApi.class - тип данных который нужно создать
    private GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GitUsersAdapter adapter = new GitUsersAdapter();// создали адаптер Users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initView();

        showProgress(true);

        adapter.setOnItemClickListener(this::openUserScreen);// ::это ссылка на один метод,
        // а :: означает, что этот метод использовать как лямду чтобы передать его в адаптер
        // и приобразовать его в OnItemClickListener (это синтаксический сахр)

        loadUsers();

    }

    private void loadUsers() {
        //вызываем проект (репозиторий) конкретного пользователя. Вызов идет ассинхронно в том же потоке
        // (enqueue - ставить в очередь в () передаем Callback, execute - выполнять).
        // Callback состоит из двух методов: onResponse - получение ответа; onFailure - получение ошибки (нет сети ошибка сервера, DNS и т.д.).
        gitHubApi.getUsers().enqueue(new Callback<List<GitUserEntity>>() {

            @Override
            public void onResponse(Call<List<GitUserEntity>> call, Response<List<GitUserEntity>> response) {
                showProgress(false);
//                if (response.code()==200){  //проверка кода, код 200 означает успех
                if (response.isSuccessful()) { //isSuccessful - это уже проверка кодов от 200 до 300
                    List<GitUserEntity> user = response.body(); // body - это тело запроса, это будет список репозиториев которые мы ищем. Здесь мы получаем список проектов
                    adapter.setData(user);

                    //test
                    assert user != null;
                    Toast.makeText(UsersActivity.this, "Size" + user.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UsersActivity.this, "Error code" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GitUserEntity>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(UsersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openUserScreen(GitUserEntity user) {
//        Intent intent = ProjectsActivity.getLaunchIntent(this, user.getLogin());
//        startActivity(intent);
        Toast.makeText(this, "Нажали " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void showProgress(boolean shouldShow) {
        if (shouldShow) {
            recyclerView.setVisibility(View.GONE);//скрываем view со списком
            progressBar.setVisibility(View.VISIBLE);//показываем прогресс загрузки
        } else {
            recyclerView.setVisibility(View.VISIBLE);//показываем view со списком
            progressBar.setVisibility(View.GONE);//скрываем прогресс загрузки
        }
    }
}