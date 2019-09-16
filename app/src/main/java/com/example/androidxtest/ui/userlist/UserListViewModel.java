package com.example.androidxtest.ui.userlist;

import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;

import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class UserListViewModel extends ViewModel {

    private UserRepository mUserRepository;
    private LiveData<PagedList<User>> mUsers;

    public UserListViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
        mUsers = new LivePagedListBuilder<>(
                mUserRepository.getUsersByPaging(), 5).build();
    }

    private static User generateUser() {
        Random random = new Random();
        User user = new User();
        user.setFirstName("Terry" + random.nextInt(100));
        user.setLastName("Li" + random.nextInt(100));
        return user;
    }

    public void insertUser() {
        mUserRepository.insertUsers(generateUser());
    }

    public void updateUser(User user) {
        mUserRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        mUserRepository.deleteUsers(user);
    }

    public void deleteAllUsers() {
        mUserRepository.deleteAllUsers();
    }

    public LiveData<PagedList<User>> getUsers() {
        return mUsers;
    }


}