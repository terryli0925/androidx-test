package com.example.androidxtest.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM user")
    List<User> loadAllUsersSync();

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> loadUserById(int userId);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    LiveData<User> findUserByName(String first, String last);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findUserByNameSync(String first, String last);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Insert
    void insertUsers(User... users);

    @Insert
    void insertUsers(List<User> users);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUsers(User... users);
}
