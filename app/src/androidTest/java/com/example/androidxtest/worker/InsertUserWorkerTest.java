package com.example.androidxtest.worker;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.ListenableWorker.Result;
import androidx.work.testing.TestWorkerBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InsertUserWorkerTest {

    private Context mContext;
    private Executor mExecutor;

    @Before
    public void setUp() {
        mContext = ApplicationProvider.getApplicationContext();
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void doWork() {
        InsertUserWorker worker = TestWorkerBuilder.from(mContext, InsertUserWorker.class, mExecutor).build();
        Result result = worker.doWork();
        assertThat(result, is(Result.success()));
    }
}