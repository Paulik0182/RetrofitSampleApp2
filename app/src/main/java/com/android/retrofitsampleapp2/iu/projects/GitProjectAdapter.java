package com.android.retrofitsampleapp2.iu.projects;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitProjectEntity;

import java.util.List;

public class GitProjectAdapter extends RecyclerView.Adapter<GitProjectViewHolder> {

    private List<GitProjectEntity> data;//Завели приватный список (закэшировали)

    public void setData(List<GitProjectEntity> project) {
        data = project;
        notifyDataSetChanged(); //для того чтобы у всех обнавлялось
    }

    // создаем сам ViewHolder
    @NonNull
    @Override
    public GitProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GitProjectViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_git_project, parent, false));
    }

    // кладем данные во ViewHolder
    @Override
    public void onBindViewHolder(@NonNull GitProjectViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private GitProjectEntity getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
