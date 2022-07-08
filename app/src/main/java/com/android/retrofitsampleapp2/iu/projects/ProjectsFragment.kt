package com.android.retrofitsampleapp2.iu.projects;

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
import com.android.retrofitsampleapp2.domain.GitProjectEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsFragment extends Fragment {

    private static final String GIT_PROJECT_ENTITY_KEY = "GIT_PROJECT_ENTITY_KEY";
    private final GitProjectAdapter adapter = new GitProjectAdapter();
    //    private final GitHubApi gitHubApi = ((App) getApplication()).getGitHubApi();//это не правильная запись
    private GitHubApi gitHubApi;//достаем из класса App из метода GitHubApi -> gitHubApi
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public static ProjectsFragment newInstance(String login) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putString(GIT_PROJECT_ENTITY_KEY, login);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        gitHubApi = ((App) getActivity().getApplication()).getGitHubApi();//достаем из класса App из метода GitHubApi -> gitHubApi.
        // при этом арр берется в общем классе BaseActivity, ткак-как от этого класса мы наследуемся

        final String login = getArguments().getString(GIT_PROJECT_ENTITY_KEY);//получаем логин

        getActivity().setTitle(login);//подставили имя в заголовок

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
                    Toast.makeText(getContext(), "Size" + users.size(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error code" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            //получение ошибки. чтото сломалось (нет сети и т.д.)
            @Override
            public void onFailure(Call<List<GitProjectEntity>> call, Throwable t) {
                showProgress(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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