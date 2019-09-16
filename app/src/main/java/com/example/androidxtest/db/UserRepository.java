package com.example.androidxtest.db;

import com.example.androidxtest.util.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UserRepository {
    private static UserRepository instance;
    private UserDao userDao;

    private UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserRepository getInstance(UserDao userDao) {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository(userDao);
                }
            }
        }
        return instance;
    }

    public void insertUsers(final User... users) {
        AppExecutors.getInstance().diskIO().execute(() -> userDao.insertUsers(users));
    }

    public void deleteAllUsers() {
        AppExecutors.getInstance().diskIO().execute(() -> userDao.deleteAllUsers());
    }

    public void deleteUsers(final User... users) {
        AppExecutors.getInstance().diskIO().execute(() -> userDao.deleteUsers(users));
    }

    public void updateUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(() -> userDao.updateUser(user));
    }

    public LiveData<List<User>> getUsers() {
        return userDao.loadAllUsers();
    }

    public List<User> getUsersSync() {
        return userDao.loadAllUsersSync();
    }

    public List<User> getUsersByIds(int[] userIds) {
        return userDao.loadAllUsersByIds(userIds);
    }

    public User findUserByName(String first, String last) {
        return userDao.findUserByName(first, last);
    }
}
