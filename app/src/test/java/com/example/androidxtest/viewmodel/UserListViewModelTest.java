package com.example.androidxtest.viewmodel;

import android.app.Application;

import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UserListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Application mApplication;
    @Mock
    private UserRepository mUserRepository;

    private UserListViewModel mUserViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mUserViewModel = new UserListViewModel(mApplication, mUserRepository);
    }

    @Test
    public void testGenerateUser() {
        User user = UserListViewModel.generateUser();
        assertThat(user.getFirstName(), notNullValue());
        assertThat(user.getLastName(), notNullValue());
    }

    @Test
    public void testInsertUser() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        mUserViewModel.insertUser();
        verify(mUserRepository).insertUsers(captor.capture());
    }

    @Test
    public void updateUser() {
        User user = UserListViewModel.generateUser();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        mUserViewModel.updateUser(user);
        verify(mUserRepository).updateUser(captor.capture());
        Assert.assertThat(captor.getValue().getFirstName(), is(user.getFirstName()));
        Assert.assertThat(captor.getValue().getLastName(), is(user.getLastName()));
    }

    @Test
    public void deleteUser() {
        User user = UserListViewModel.generateUser();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        mUserViewModel.deleteUser(user);
        verify(mUserRepository).deleteUsers(captor.capture());
    }

    @Test
    public void deleteAllUsers() {
        mUserViewModel.deleteAllUsers();
        verify(mUserRepository).deleteAllUsers();
    }

    @Test
    public void getUsers() {
        mUserViewModel.getUsers();
        verify(mUserRepository).getUsers();
    }

    @Test
    public void getUsersWhenObserved() {
        MutableLiveData<List<User>> users = new MutableLiveData<>();
        when(mUserViewModel.getUsers()).thenReturn(users);

        Observer<List<User>> observer = mock(Observer.class);
        mUserRepository.getUsers().observeForever(observer);

        List<User> userList = new ArrayList<>();
        users.postValue(userList);
        verify(observer).onChanged(userList);
    }
}