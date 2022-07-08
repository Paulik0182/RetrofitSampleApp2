package com.android.retrofitsampleapp2.iu.users

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.retrofitsampleapp2.R
import com.android.retrofitsampleapp2.domain.GitUserEntity

class GitUsersViewHolder(itemView: View, listener: GitUsersAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val titleUserTextView = itemView.findViewById<TextView>(R.id.title_user_text_view)
    private val subtitleUserTextView = itemView.findViewById<TextView>(R.id.subtitle_user_text_view)
    private lateinit var user: GitUserEntity

    fun bind(gitUserEntity: GitUserEntity) {
        user = gitUserEntity //сохранили user в текущей карточке
        titleUserTextView.text = gitUserEntity.login
        subtitleUserTextView.text = gitUserEntity.nodeId
    }

    init {
        // обработка нажатия
        // при нажатии на элемент происходит вызов listener и передается в него user который положили в bind
        itemView.setOnClickListener { _ ->
            user.let {
                listener.onItemClick(
                    it
                )
            }
        }
    }
}