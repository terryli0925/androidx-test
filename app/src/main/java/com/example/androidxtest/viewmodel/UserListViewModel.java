package com.example.androidxtest.viewmodel;

import android.app.Application;

import com.example.androidxtest.Constants;
import com.example.androidxtest.db.User;
import com.example.androidxtest.db.UserRepository;
import com.example.androidxtest.worker.InsertUserWorker;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class UserListViewModel extends AndroidViewModel {

    private static final String TAG = UserListViewModel.class.getSimpleName();

    private UserRepository mUserRepository;
    private LiveData<List<User>> mUsers;
    private WorkManager mWorkManager;


    public UserListViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);
        mUserRepository = userRepository;
        mUsers = mUserRepository.getUsers();
        mWorkManager = WorkManager.getInstance(application);
    }

    private static User generateUser() {
        Random random = new Random();
        User user = new User();
        user.setFirstName("Terry" + random.nextInt(100));
        user.setLastName("Li" + random.nextInt(100));
        return user;
    }

    public void insertUser() {
        mUserRepository.insertUsers(generateUser());
    }

    public void insertUsersFromLocal() {
        OneTimeWorkRequest insertWorkRequest = new OneTimeWorkRequest.Builder(InsertUserWorker.class).addTag(Constants.TAG_INSERT_USER_WORKER).build();
//        mWorkManager.enqueue(insertWorkRequest);
        mWorkManager.beginUniqueWork(Constants.INSERT_USER_WORKER_NAME, ExistingWorkPolicy.REPLACE, insertWorkRequest).enqueue();
    }

    public void updateUser(User user) {
        mUserRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        mUserRepository.deleteUsers(user);
    }

    public void deleteAllUsers() {
        mUserRepository.deleteAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return mUsers;
    }


}