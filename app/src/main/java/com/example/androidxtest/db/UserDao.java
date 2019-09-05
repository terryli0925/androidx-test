package com.example.androidxtest.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM user")
    List<User> loadAllUsersSync();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllUsersByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findUserByName(String first, String last);

    @Insert
    void insertUsers(User... users);

    @Delete
    void deleteUser(User user);
}