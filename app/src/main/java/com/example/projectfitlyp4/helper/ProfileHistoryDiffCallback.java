package com.example.projectfitlyp4.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.example.projectfitlyp4.database.ProfileHistory;

import java.util.List;

public class ProfileHistoryDiffCallback extends DiffUtil.Callback {
    private final List<ProfileHistory> mOldProfileHistoryList;
    private final List<ProfileHistory> mNewProfileHistoryList;
    public ProfileHistoryDiffCallback(List<ProfileHistory> oldNoteList, List<ProfileHistory>
            newNoteList) {
        this.mOldProfileHistoryList = oldNoteList;
        this.mNewProfileHistoryList = newNoteList;
    }
    @Override
    public int getOldListSize() {
        return mOldProfileHistoryList.size();
    }
    @Override
    public int getNewListSize() {
        return mNewProfileHistoryList.size();
    }
    @Override

    public boolean areItemsTheSame(int oldItemPosition, int
            newItemPosition) {
        return mOldProfileHistoryList.get(oldItemPosition).getId() ==
                mNewProfileHistoryList.get(newItemPosition).getId();
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int
            newItemPosition) {
        final ProfileHistory oldEmployee =
                mOldProfileHistoryList.get(oldItemPosition);
        final ProfileHistory newEmployee =
                mNewProfileHistoryList.get(newItemPosition);
        return
                oldEmployee.getFromName().equals(newEmployee.getFromName()) &&
                        oldEmployee.getToName().equals(newEmployee.getToName(
                        ));
    }
}