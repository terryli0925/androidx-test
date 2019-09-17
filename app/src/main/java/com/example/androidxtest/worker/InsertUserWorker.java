package com.example.androidxtest.worker;

import android.content.Context;

import com.example.androidxtest.Constants;
import com.example.androidxtest.db.AppDatabase;
import com.example.androidxtest.db.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InsertUserWorker extends Worker {

    public InsertUserWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            InputStream input = getApplicationContext().getAssets().open(Constants.USER_DATA_FILENAME);
            JsonReader reader = new JsonReader(new InputStreamReader(input));
            Type type = new TypeToken<List<User>>(){}.getType();
            List<User> userList = new Gson().fromJson(reader, type);
            input.close();

            AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            database.getUserDao().insertUsers(userList);

            return Result.success();
        } catch (IOException e) {
            return Result.failure();
        }
    }
}
