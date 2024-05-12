package com.example.projectfitlyp4.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ProgressUserDao {

    @Query("SELECT * FROM progress_user")
    List<ProgressUser> getAll();

    @Insert
    void insert(ProgressUser progressUser);

    @Delete
    void delete(ProgressUser progressUser);
}
