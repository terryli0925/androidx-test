package com.example.androidxtest.ui.userlist;

import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;

import java.util.List;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserListViewModel extends ViewModel {

    private UserRepository mUserRepository;
    private LiveData<List<User>> mUsers;

    public UserListViewModel(UserRepository userRepository) {
        mUserRepository = userRepository;
        mUsers = mUserRepository.getUsers();
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

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }


}