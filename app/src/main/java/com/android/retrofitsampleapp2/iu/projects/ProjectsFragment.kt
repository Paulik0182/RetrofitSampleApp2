package com.android.retrofitsampleapp2.iu.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.retrofitsampleapp2.App
import com.android.retrofitsampleapp2.R
import com.android.retrofitsampleapp2.data.GitHubApi
import com.android.retrofitsampleapp2.domain.GitProjectEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectsFragment : Fragment() {
    private lateinit var adapter: GitProjectAdapter

    private val gitHubApi: GitHubApi by lazy { (requireActivity().application as App).gitHubApi }
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        // при этом арр берется в общем классе BaseActivity, ткак-как от этого класса мы наследуемся
        val login = requireArguments().getString(GIT_PROJECT_ENTITY_KEY) //получаем логин
        requireActivity().title = login //подставили имя в заголовок
        loadProjects(login)
    }

    private fun loadProjects(login: String?) {
        showProgress(true)
        gitHubApi.getProject(login!!).enqueue(object : Callback<List<GitProjectEntity>> {
            //скачиваем все проекты пользователя
            //получение ответа
            override fun onResponse(
                call: Call<List<GitProjectEntity>>,
                response: Response<List<GitProjectEntity>>
            ) {
                showProgress(false)
                if (response.isSuccessful) { //Если успех, достаем GitProjectEntity, делаем setData. isSuccessful - это уже проверка кодов от 200 до 300
                    val users =
                        response.body() // body - это тело запроса, это будет список репозиториев которые мы ищем. Здесь мы получаем список проектов
                    adapter.setData(users!!)
                    //test
                    Toast.makeText(context, "Size" + users.size, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error code" + response.code(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            //получение ошибки. чтото сломалось (нет сети и т.д.)
            override fun onFailure(call: Call<List<GitProjectEntity>>, t: Throwable) {
                showProgress(false)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initView(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun showProgress(shouldShow: Boolean) {
        if (shouldShow) {
            recyclerView.visibility = View.GONE //скрываем view со списком
            progressBar.visibility = View.VISIBLE //показываем прогресс загрузки
        } else {
            recyclerView.visibility = View.VISIBLE //показываем view со списком
            progressBar.visibility = View.GONE //скрываем прогресс загрузки
        }
    }

    companion object {
        private const val GIT_PROJECT_ENTITY_KEY = "GIT_PROJECT_ENTITY_KEY"

        @JvmStatic
        fun newInstance(login: String?): ProjectsFragment {
            return ProjectsFragment().apply {
                arguments = Bundle().apply {
                    putString(GIT_PROJECT_ENTITY_KEY, login)
                }
            }
        }
    }
}