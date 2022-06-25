package com.android.retrofitsampleapp2.iu.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.data.GitHubApi;
import com.android.retrofitsampleapp2.domain.GitProjectEntity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectsActivity extends AppCompatActivity {

    private static final String LOGIN_EXTRA_KEY = "LOGIN_EXTRA_KEY";

    //увеличили время по таймауту при загрузке из сети (без этого, по умолчанию 10сек.)
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)// увеличение времени по таймауту
            .addConverterFactory(GsonConverterFactory.create())// это приобразователь объектов из одного типа в другой тип (здесь старонняя библиотека)
            .build();
    private final GitHubApi gitHubApi = retrofit.create(GitHubApi.class); //создаем gitHubApi. Автоматически обратится к интерфейсу

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private final GitProjectAdapter adapter = new GitProjectAdapter();


    public static Intent getLaunchIntent(Context context, String login) {
        Intent intent = new Intent(context, ProjectsActivity.class);
        intent.putExtra(LOGIN_EXTRA_KEY, login);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        initView();

        final String login = getIntent().getStringExtra(LOGIN_EXTRA_KEY);//получаем логин

        setTitle(login);//подставили имя в заголовок

        loadProjects(login);
    }

    private void loadProjects(String login) {
        showProgress(true);
        gitHubApi.getProject(login).enqueue(new Callback<List<GitProjectEntity>>() {//скачиваем все проекты пользователя
            //получение ответа
            @Override
            public void onResponse(Call<List<GitProjectEntity>> call, Response<List<GitProjectEntity>> response) {
                showProgress(false);
                if (response.isSuccessful()) { //Если успех, достаем GitProjectEntity, делаем setData. isSuccessful - это уже проверка кодов от 200 до 300
                    List<GitProjectEntity> users = response.body(); // body - это тело запроса, это будет список репозиториев которые мы ищем. Здесь мы получаем список проектов

                    adapter.setData(users);
                    //test
                    Toast.makeText(ProjectsActivity.this, "Size" + users.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProjectsActivity.this, "Error code" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            //получение ошибки. чтото сломалось (нет сети и т.д.)
            @Override
            public void onFailure(Call<List<GitProjectEntity>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(ProjectsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
