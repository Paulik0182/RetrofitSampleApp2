package com.android.retrofitsampleapp2.iu.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.retrofitsampleapp2.R
import com.android.retrofitsampleapp2.domain.GitUserEntity

class GitUsersAdapter(
    private var data: List<GitUserEntity>,
    private var listener: (GitUserEntity) -> Unit
) : RecyclerView.Adapter<GitUsersViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<GitUserEntity>) {
        data = users
        notifyDataSetChanged()
    }

    //сохнанили слушатель. после интерфейса идем сюда и передаем его дальше во onCreateViewHolder
    fun setOnItemClickListener(listener: (GitUserEntity) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitUsersViewHolder {
        return GitUsersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_git_user, parent, false),
            (listener as OnItemClickListener)
        ) // listener - передаем далее во viewHolder
    }

    override fun onBindViewHolder(holder: GitUsersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(pos: Int): GitUserEntity = data[pos]

    override fun getItemCount(): Int = data.size

    //интерфейс со слушателем
    interface OnItemClickListener : (OnItemClickListener) -> Unit {
        fun onItemClick(user: GitUserEntity)
    }
}