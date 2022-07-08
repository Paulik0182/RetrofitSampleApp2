package com.android.retrofitsampleapp2.iu.users

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
import com.android.retrofitsampleapp2.domain.GitUserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private val gitHubApi: GitHubApi by lazy { (requireActivity().application as App).gitHubApi }
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val adapter = GitUsersAdapter() // создали адаптер Users

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        // при этом арр берется в общем классе BaseActivity, ткак-как от этого класса мы наследуемся
        showProgress(true)
        adapter.setOnItemClickListener { user: GitUserEntity -> openUserScreen(user) } // ::это ссылка на один метод,
        // а :: означает, что этот метод использовать как лямду чтобы передать его в адаптер
        // и приобразовать его в OnItemClickListener (это синтаксический сахр)
        loadUsers()
    }

    private fun loadUsers() {
        //вызываем проект (репозиторий) конкретного пользователя. Вызов идет ассинхронно в том же потоке
        // (enqueue - ставить в очередь в () передаем Callback, execute - выполнять).
        // Callback состоит из двух методов: onResponse - получение ответа; onFailure - получение ошибки (нет сети ошибка сервера, DNS и т.д.).
        gitHubApi.getUsers().enqueue(object : Callback<List<GitUserEntity>> {
            override fun onResponse(
                call: Call<List<GitUserEntity>>,
                response: Response<List<GitUserEntity>>
            ) {
                showProgress(false)
                //                if (response.code()==200){  //проверка кода, код 200 означает успех
                if (response.isSuccessful) { //isSuccessful - это уже проверка кодов от 200 до 300
                    val user =
                        response.body() // body - это тело запроса, это будет список репозиториев которые мы ищем. Здесь мы получаем список проектов
                    adapter.setData(user!!)
                    Toast.makeText(context, "Size" + user.size, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error code" + response.code(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<GitUserEntity>>, t: Throwable) {
                showProgress(false)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun openUserScreen(user: GitUserEntity) {
        controller.openProjectFragment(user)
        Toast.makeText(context, "Нажали " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun initView(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.setAdapter(adapter)
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

    private val controller: Controller by lazy { activity as Controller }

    interface Controller {
        fun openProjectFragment(user: GitUserEntity)
    }
}