package com.android.retrofitsampleapp2.iu.users;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.retrofitsampleapp2.R;
import com.android.retrofitsampleapp2.domain.GitUserEntity;

import java.util.List;

public class GitUsersAdapter extends RecyclerView.Adapter<GitUsersViewHolder> {

    private List<GitUserEntity> data;
    private OnItemClickListener listener;//завели слушатель

    public void setData(List<GitUserEntity> users) {
        data = users;
        notifyDataSetChanged();
    }

    //сохнанили слушатель. после интерфейса идем сюда и передаем его дальше во onCreateViewHolder
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GitUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GitUsersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_git_user, parent, false), listener);// listener - передаем далее во viewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull GitUsersViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private GitUserEntity getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //интерфейс со слушателем
    public interface OnItemClickListener {
        void onItemClick(GitUserEntity user);
    }
}
