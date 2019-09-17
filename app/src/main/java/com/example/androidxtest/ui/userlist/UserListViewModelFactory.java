package com.example.androidxtest.ui.userlist;

import android.app.Application;

import com.example.androidxtest.db.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private UserRepository mUserRepository;

    public UserListViewModelFactory(@NonNull Application application, @NonNull UserRepository userRepository) {
        super();
        mApplication = application;
        mUserRepository = userRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserListViewModel.class)) {
            return (T) new UserListViewModel(mApplication, mUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class" + modelClass);
    }
}
