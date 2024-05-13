package com.example.projectfitlyp4.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projectfitlyp4.database.DAOProfileHistory;
import com.example.projectfitlyp4.database.ProfileHistory;
import com.example.projectfitlyp4.database.ProfileHistoryRoomDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileHistoryRepository {
    private final DAOProfileHistory mDaoProfileHistories;

    private final ExecutorService executorService;
    public ProfileHistoryRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ProfileHistoryRoomDB db = ProfileHistoryRoomDB.getDatabase(application);
        mDaoProfileHistories = db.daoProfileHistory();
    }
    public LiveData<List<ProfileHistory>> getAllProfileHistories() {
        return mDaoProfileHistories.getAllProfileHistories();
    }
    public void insert(final ProfileHistory profileHistory) {
        executorService.execute(() -> mDaoProfileHistories.insert(profileHistory));
    }
    public void delete(final ProfileHistory profileHistory){
        executorService.execute(() -> mDaoProfileHistories.delete(profileHistory));
    }
    public void update(final ProfileHistory profileHistory){
        executorService.execute(() -> mDaoProfileHistories.update(profileHistory));
    }
}