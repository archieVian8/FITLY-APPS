package com.example.projectfitlyp4.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectfitlyp4.database.ProfileHistory;
import com.example.projectfitlyp4.repository.ProfileHistoryRepository;

import java.util.List;

public class ProfileHistoryProfileChangeHistoriesViewModel extends ViewModel {
    private final ProfileHistoryRepository mProfileHistoryRepository;
    public ProfileHistoryProfileChangeHistoriesViewModel(Application application) {
        mProfileHistoryRepository = new ProfileHistoryRepository(application);
    }
    LiveData<List<ProfileHistory>> getAllProfileHistories() {
        return mProfileHistoryRepository.getAllProfileHistories();
    }
}

