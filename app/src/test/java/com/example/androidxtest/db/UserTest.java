package com.example.androidxtest.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserTest {

    @Test
    public void test_user() {
        User user = new User();
        user.setFirstName("Terry");
        user.setLastName("Li");
        assertEquals("Terry", user.getFirstName());
        assertEquals("Li", user.getLastName());
    }
}