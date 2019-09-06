package com.example.androidxtest.ui.userlist;

import com.example.androidxtest.db.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private UserRepository mUserRepository;

    public UserListViewModelFactory(@NonNull UserRepository userRepository) {
        super();
        this.mUserRepository = userRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserListViewModel(mUserRepository);
    }
}
