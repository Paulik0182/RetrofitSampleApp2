package com.android.retrofitsampleapp2.iu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitUserEntity;

public class GitUsersViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView subtitleTextView = itemView.findViewById(R.id.subtitle_text_view);

    public GitUsersViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(GitUserEntity gitUserEntity) {
        titleTextView.setText(gitUserEntity.getLogin());
        subtitleTextView.setText(gitUserEntity.getNodeId());
    }
}
