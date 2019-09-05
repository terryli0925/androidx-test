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
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUsers(users);
            }
        });
    }

    public void deleteUser(final User user) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteUser(user);
            }
        });
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
