package com.android.retrofitsampleapp2.iu.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.App;
import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.data.GitHubApi;
import com.android.retrofitsampleapp2.domain.GitUserEntity;
import com.android.retrofitsampleapp2.iu.projects.ProjectsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    //    private final GitHubApi gitHubApi = ((App) getApplication()).getGitHubApi();//это не правильная запись
    private GitHubApi gitHubApi;//достаем из класса App из метода GitHubApi -> gitHubApi

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GitUsersAdapter adapter = new GitUsersAdapter();// создали адаптер Users

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        gitHubApi = ((App) getActivity().getApplication()).getGitHubApi();//достаем из класса App из метода GitHubApi -> gitHubApi.
        // при этом арр берется в общем классе BaseActivity, ткак-как от этого класса мы наследуемся

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
                    Toast.makeText(getContext(), "Size" + user.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error code" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GitUserEntity>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openUserScreen(GitUserEntity user) {
        Intent intent = ProjectsActivity.getLaunchIntent(getContext(), user.getLogin());
        startActivity(intent);
        Toast.makeText(getContext(), "Нажали " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    private void initView(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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