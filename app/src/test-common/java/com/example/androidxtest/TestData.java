package com.example.androidxtest;

import com.example.androidxtest.db.User;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final User USER1 = new User("Terry1", "Li1");
    public static final User USER2 = new User("Terry2", "Li2");
    public static final User USER3 = new User("Terry3", "Li3");
    public static final List<User> USERS = Arrays.asList(USER1, USER2, USER3);
}
