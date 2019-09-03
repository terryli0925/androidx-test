package com.example.androidxtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidxtest.R;
import com.example.androidxtest.db.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> mUserList;

    public UserAdapter() {
        mUserList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        UserViewHolder vh = new UserViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.mFirstName.setText(user.firstName);
        holder.mLastName.setText(user.lastName);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void setUsers(List<User> users) {
        mUserList.clear();
        this.mUserList.addAll(users);
        notifyDataSetChanged();
    }


    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView mFirstName;
        TextView mLastName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mFirstName = itemView.findViewById(R.id.first_name);
            mLastName = itemView.findViewById(R.id.last_name);
        }

    }
}
