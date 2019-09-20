package com.example.androidxtest.db;

import android.content.Context;

import com.example.androidxtest.util.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.example.androidxtest.util.TestData.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    /**
     * Avoid java.lang.IllegalStateException: Cannot invoke observeForever on a background thread
     */
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private UserDao mUserDao;
    private AppDatabase mDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();
        mUserDao = mDatabase.getUserDao();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void getUsersWhenNoUserInserted() throws InterruptedException {
        assertTrue(LiveDataTestUtil.getValue(mUserDao.loadAllUsers()).isEmpty());
    }

    @Test
    public void getUsersAfterInserted() throws InterruptedException {
        mUserDao.insertUsers(USERS);
        assertThat(LiveDataTestUtil.getValue(mUserDao.loadAllUsers()).size(), is(USERS.size()));
    }

    @Test
    public void getUserByName() throws InterruptedException {
        mUserDao.insertUsers(USERS);
        User user = LiveDataTestUtil.getValue(mUserDao.findUserByName(USER2.getFirstName(), USER2.getLastName()));
        assertThat(user.getFirstName(), is(USER2.getFirstName()));
        assertThat(user.getLastName(), is(USER2.getLastName()));
    }

    @Test
    public void updateUser() throws InterruptedException {
        mUserDao.insertUsers(USERS);
        User user = LiveDataTestUtil.getValue(mUserDao.findUserByName(USER2.getFirstName(), USER2.getLastName()));
        user.setFirstName("Terry3");
        user.setLastName("Li3");
        mUserDao.updateUser(user);
        User updatedUser = LiveDataTestUtil.getValue(mUserDao.loadUserById(user.getId()));
        assertThat(user.getId(), is(updatedUser.getId()));
        assertThat(user.getFirstName(), is(updatedUser.getFirstName()));
        assertThat(user.getLastName(), is(updatedUser.getLastName()));
    }

    @Test
    public void deleteAllUsers() throws InterruptedException {
        mUserDao.insertUsers(USERS);
        assertThat(LiveDataTestUtil.getValue(mUserDao.loadAllUsers()).size(), is(3));
        mUserDao.deleteAllUsers();
        assertThat(LiveDataTestUtil.getValue(mUserDao.loadAllUsers()).size(), is(0));
    }

    @Test
    public void deleteUser() throws InterruptedException {
        mUserDao.insertUsers(USERS);
        List<User> users = LiveDataTestUtil.getValue(mUserDao.loadAllUsers());
        assertThat(users.size(), is(3));
        mUserDao.deleteUsers(users.get(0));
        assertThat(LiveDataTestUtil.getValue(mUserDao.loadAllUsers()).size(), is(2));
    }
}