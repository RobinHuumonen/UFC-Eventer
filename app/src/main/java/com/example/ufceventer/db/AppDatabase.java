package com.example.ufceventer.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ufceventer.models.PickAndScore;

@Database(entities = {PickAndScore.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract PickAndScoreDAO getPickAndScoreDAO();
}