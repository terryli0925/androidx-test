package com.example.androidxtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidxtest.R;
import com.example.androidxtest.databinding.ListItemUserBinding;
import com.example.androidxtest.db.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    public UserAdapter() {
        super(new UserDiffCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
//        v.setOnClickListener(this);
//        v.setOnLongClickListener(this);
//        ViewHolder vh = new ViewHolder(v);
        return new ViewHolder(ListItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);
//        holder.mFirstName.setText(user.getFirstName());
//        holder.mLastName.setText(user.getLastName());
        holder.bind(user);
        holder.itemView.setTag(user);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (User) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (User) v.getTag());
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
        void onItemClick(View view, User user);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, User user);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ListItemUserBinding binding;

//
//        TextView mFirstName;
//        TextView mLastName;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mFirstName = itemView.findViewById(R.id.first_name);
//            mLastName = itemView.findViewById(R.id.last_name);
//        }

        public ViewHolder(ListItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(User user) {
//            binding.firstName.setText(user.getFirstName());
//            binding.lastName.setText(user.getLastName());
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }

    static class UserDiffCallback extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    }
}
