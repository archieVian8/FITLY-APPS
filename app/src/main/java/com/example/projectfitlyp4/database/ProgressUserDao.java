package com.example.projectfitlyp4.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProgressUserDao {
    @Query("SELECT * FROM progress_user")
    LiveData<List<ProgressUser>> getAllProgressUsers();

    @Insert
    void insert(ProgressUser progressUser);

    @Update
    void updateProgressUser(ProgressUser progressUser);

    @Delete
    void deleteProgressUser(ProgressUser progressUser);

    @Query("DELETE FROM progress_user WHERE firebase_id = :firebaseId")
    void deleteByFirebaseId(String firebaseId);

    @Query("SELECT * FROM progress_user")
    List<ProgressUser> getAll();

    @Query("SELECT * FROM progress_user WHERE firebase_id = :firebaseId")
    ProgressUser getByFirebaseId(String firebaseId);
}
