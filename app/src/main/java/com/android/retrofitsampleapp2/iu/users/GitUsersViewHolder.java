package com.android.retrofitsampleapp2.iu.users;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitUserEntity;

public class GitUsersViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleUserTextView = itemView.findViewById(R.id.title_user_text_view);
    private final TextView subtitleUserTextView = itemView.findViewById(R.id.subtitle_user_text_view);

    private GitUserEntity user;//звсели user

    public GitUsersViewHolder(@NonNull View itemView, GitUsersAdapter.OnItemClickListener listener) {
        super(itemView);
        // обработка нажатия
        // при нажатии на элемент происходит вызов listener и передается в него user который положили в bind
        itemView.setOnClickListener(v -> {
            listener.onItemClick(user);//передали user в слушатель
        });
    }

    public void bind(GitUserEntity gitUserEntity) {
        user = gitUserEntity;//сохранили user в текущей карточке
        titleUserTextView.setText(gitUserEntity.getLogin());
        subtitleUserTextView.setText(gitUserEntity.getNodeId());
    }
}
