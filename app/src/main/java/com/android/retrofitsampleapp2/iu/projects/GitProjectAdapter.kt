package com.android.retrofitsampleapp2.iu.projects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.retrofitsampleapp2.R
import com.android.retrofitsampleapp2.domain.GitProjectEntity

class GitProjectAdapter(
    private var data: List<GitProjectEntity>
) : RecyclerView.Adapter<GitProjectViewHolder>() {

    fun setData(project: List<GitProjectEntity>) {
        data = project
        notifyDataSetChanged() //для того чтобы у всех обнавлялось
    }

    // создаем сам ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitProjectViewHolder {
        return GitProjectViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_git_project, parent, false)
        )
    }

    // кладем данные во ViewHolder
    override fun onBindViewHolder(holder: GitProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(pos: Int): GitProjectEntity = data[pos]

    override fun getItemCount(): Int = data.size

}