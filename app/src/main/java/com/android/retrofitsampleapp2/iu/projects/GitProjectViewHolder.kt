package com.android.retrofitsampleapp2.iu.projects

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.retrofitsampleapp2.R
import com.android.retrofitsampleapp2.domain.GitProjectEntity

class GitProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Заводим переменные и сразу их инициализируем
    private val titleTextView = itemView.findViewById<TextView>(R.id.title_text_view)
    private val subtitleTextView = itemView.findViewById<TextView>(R.id.subtitle_text_view)
    fun bind(gitProjectEntity: GitProjectEntity) {
        titleTextView.text = gitProjectEntity.name
        subtitleTextView.text = gitProjectEntity.description
    }
}