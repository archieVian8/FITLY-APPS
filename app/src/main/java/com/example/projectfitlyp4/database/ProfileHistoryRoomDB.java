package com.example.projectfitlyp4.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProfileHistory.class}, version = 1,exportSchema = false)
public abstract class ProfileHistoryRoomDB extends RoomDatabase {
    public abstract DAOProfileHistory daoProfileHistory();
    private static volatile ProfileHistoryRoomDB INSTANCE;
    public static ProfileHistoryRoomDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ProfileHistoryRoomDB.class){
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(),
                                ProfileHistoryRoomDB.class, "profileHistory_db").build();
            }
        }
        return INSTANCE;
    }
}