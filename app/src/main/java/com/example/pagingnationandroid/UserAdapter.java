package com.example.pagingnationandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    private List<User> list;
    private boolean isLoading;

    public void setData(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            User user = list.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tv.setText(user.getName() + " " + (position + 1));

        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && position == list.size() - 1 && isLoading) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }

    public void addLoading() {
        isLoading = true;
        list.add(new User(""));
    }

    public void removeLoading() {
        isLoading = false;
        int position = list.size() - 1;
        User user = list.get(position);
        if (user != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

}
