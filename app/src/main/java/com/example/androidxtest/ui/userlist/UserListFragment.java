package com.example.androidxtest.ui.userlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.androidxtest.R;
import com.example.androidxtest.adapter.UserAdapter;
import com.example.androidxtest.db.AppDatabase;
import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class UserListFragment extends Fragment {

    private static final String TAG = UserListFragment.class.getSimpleName();

    private UserListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserRepository repository = UserRepository.getInstance(AppDatabase.getInstance(getActivity().getApplicationContext()).getUserDao());
        UserListViewModelFactory factory = new UserListViewModelFactory(repository);
        mViewModel = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_list, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.insertUser();
            }
        });

        mRecyclerView = root.findViewById(R.id.users_list);
        mRecyclerView.setHasFixedSize(true);
        mUserAdapter = new UserAdapter();
        mRecyclerView.setAdapter(mUserAdapter);

        mUserAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, User user) {
                showUpdateUserDialog(user);
            }
        });
        mUserAdapter.setOnItemLognClickListener(new UserAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, User user) {
                mViewModel.deleteUser(user);
            }
        });

        mViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: " + users.size());
                mUserAdapter.submitList(users);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Clear menu before inflating new menu
        inflater.inflate(R.menu.menu_user_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                mViewModel.deleteAllUsers();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showUpdateUserDialog(final User user) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_update_user, null);
        final EditText firstName = view.findViewById(R.id.first_name);
        final EditText lastName = view.findViewById(R.id.last_name);

        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        user.setFirstName(firstName.getText().toString());
                        user.setLastName(lastName.getText().toString());
                        mViewModel.updateUser(user);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).create().show();
    }
}