package com.example.androidxtest.db;

import org.junit.Test;

import static org.junit.Assert.*;

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