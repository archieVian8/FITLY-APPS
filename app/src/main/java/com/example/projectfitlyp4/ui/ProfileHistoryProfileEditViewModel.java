package com.example.projectfitlyp4.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.example.projectfitlyp4.database.ProfileHistory;
import com.example.projectfitlyp4.repository.ProfileHistoryRepository;


public class ProfileHistoryProfileEditViewModel extends ViewModel {
    private final ProfileHistoryRepository mProfileHistoryRepository;
    public ProfileHistoryProfileEditViewModel(Application application) {
        mProfileHistoryRepository = new ProfileHistoryRepository(application);
    }
    public void insert(ProfileHistory profileHistory) {
        mProfileHistoryRepository.insert(profileHistory);
    }
    public void update(ProfileHistory profileHistory) {
        mProfileHistoryRepository.update(profileHistory);
    }
    public void delete(ProfileHistory profileHistory) {
        mProfileHistoryRepository.delete(profileHistory);
    }
}