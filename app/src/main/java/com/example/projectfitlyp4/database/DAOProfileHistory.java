package com.example.projectfitlyp4.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAOProfileHistory {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProfileHistory profileHistory);
    @Update()
    void update(ProfileHistory profileHistory);
    @Delete()
    void delete(ProfileHistory profileHistory);
    @Query("SELECT * from profileHistory ORDER BY id ASC")
    LiveData<List<ProfileHistory>> getAllProfileHistories();
}
