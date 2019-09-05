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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private ArrayList<User> mUserList;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    public UserAdapter() {
        mUserList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        UserViewHolder vh = new UserViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        holder.mFirstName.setText(user.firstName);
        holder.mLastName.setText(user.lastName);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public User getItem(int position) {
        return mUserList.get(position);
    }

    public void setUsers(List<User> users) {
        mUserList.clear();
        this.mUserList.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (int) v.getTag());
        }
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLognClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
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
