package com.example.androidxtest.ui.send;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidxtest.R;
import com.example.androidxtest.adapter.UserAdapter;
import com.example.androidxtest.db.AppDatabase;
import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class SendFragment extends Fragment {

    private static final String TAG = SendFragment.class.getSimpleName();

    private SendViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private UserRepository mUserRepository;

    public static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.firstName = "Li" + i;
            user.lastName = "Terry" + i;
            users.add(user);
        }
        return users;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUserRepository = UserRepository.getInstance(AppDatabase.getInstance(getActivity().getApplicationContext()).getUserDao());
        mViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        mRecyclerView = root.findViewById(R.id.users_list);
        mRecyclerView.setHasFixedSize(true);
        mUserAdapter = new UserAdapter();
        mRecyclerView.setAdapter(mUserAdapter);
        mUserAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                User user = mUserAdapter.getItem(position);
                mUserRepository.deleteUser(user);
            }
        });

        mUserRepository.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: " + users.size());
                mUserAdapter.setUsers(users);
            }
        });

        mUserRepository.insertUsers(generateUsers().toArray(new User[10]));

        return root;
    }
}