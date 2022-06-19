package com.android.retrofitsampleapp2.iu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitUserEntity;

public class GitUsersViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleUserTextView = itemView.findViewById(R.id.title_user_text_view);
    private final TextView subtitleUserTextView = itemView.findViewById(R.id.subtitle_user_text_view);

    public GitUsersViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(GitUserEntity gitUserEntity) {
        titleUserTextView.setText(gitUserEntity.getLogin());
        subtitleUserTextView.setText(gitUserEntity.getNodeId());
    }
}
