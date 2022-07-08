package com.android.retrofitsampleapp2.iu.projects;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitProjectEntity;

public class GitProjectViewHolder extends RecyclerView.ViewHolder {

    //Заводим переменные и сразу их инициализируем
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView subtitleTextView = itemView.findViewById(R.id.subtitle_text_view);

    public GitProjectViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(GitProjectEntity gitProjectEntity) {
        titleTextView.setText(gitProjectEntity.getName());
        subtitleTextView.setText(gitProjectEntity.getDescription());
    }
}
