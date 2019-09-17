package com.example.androidxtest.ui.userlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.androidxtest.Constants;
import com.example.androidxtest.R;
import com.example.androidxtest.adapter.UserAdapter;
import com.example.androidxtest.databinding.FragmentUserListBinding;
import com.example.androidxtest.db.AppDatabase;
import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class UserListFragment extends Fragment {

    private static final String TAG = UserListFragment.class.getSimpleName();

    private UserListViewModel mViewModel;
    private UserAdapter mUserAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentUserListBinding binding = FragmentUserListBinding.inflate(inflater, container, false);
        UserRepository repository = UserRepository.getInstance(AppDatabase.getInstance(getActivity().getApplicationContext()).getUserDao());
        UserListViewModelFactory factory = new UserListViewModelFactory(getActivity().getApplication(), repository);
        mViewModel = ViewModelProviders.of(this, factory).get(UserListViewModel.class);
        binding.setViewModel(mViewModel);

        mUserAdapter = new UserAdapter();
        binding.usersList.setHasFixedSize(true);
        binding.usersList.setAdapter(mUserAdapter);
        mUserAdapter.setOnItemClickListener((view, user) -> showUpdateUserDialog(user));
        mUserAdapter.setOnItemLognClickListener((view, user) -> mViewModel.deleteUser(user));

        mViewModel.getUsers().observe(getViewLifecycleOwner(), (users) -> {
            Log.i(TAG, "onChanged: " + users.size());
            mUserAdapter.submitList(users);
        });

        WorkManager.getInstance(getActivity()).getWorkInfosByTagLiveData(Constants.TAG_INSERT_USER_WORKER)
                .observe(getViewLifecycleOwner(), listOfWorkInfo -> {
                    if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                        return;
                    }

                    // We only care about the first output status.
                    // Every continuation has only one worker tagged TAG_INSERT_USER_WORKER
                    WorkInfo workInfo = listOfWorkInfo.get(0);
                    boolean finished = workInfo.getState().isFinished();
                    Log.i(TAG, "WorkerStatus: " + listOfWorkInfo.size());
                    Log.i(TAG, "WorkerStatus: " + finished);
                });

        return binding.getRoot();
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
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    user.setFirstName(firstName.getText().toString());
                    user.setLastName(lastName.getText().toString());
                    mViewModel.updateUser(user);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                }).create().show();
    }
}