package com.example.androidxtest.db;

import com.example.androidxtest.TestData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class UserRepositoryTest {

    @Mock
    private UserDao userDao;

    private UserRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new UserRepository(userDao);
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void testSameInstance() {
        UserRepository repository = UserRepository.getInstance(userDao);
        assertThat(repository, is(UserRepository.getInstance(userDao)));
    }

    @Test
    public void getUsers() {
        repository.getUsers();
        verify(userDao).loadAllUsers();
    }

    @Test
    public void getUsersSync() {
        repository.getUsersSync();
        verify(userDao).loadAllUsersSync();
    }

    @Test
    public void getUserByIds() {
        repository.getUserByIds(TestData.USER1.getId());
        verify(userDao).loadUserById(TestData.USER1.getId());
    }

    @Test
    public void findUserByName() {
        repository.findUserByName(TestData.USER1.getFirstName(), TestData.USER1.getLastName());
        verify(userDao).findUserByName(TestData.USER1.getFirstName(), TestData.USER1.getLastName());
    }
}