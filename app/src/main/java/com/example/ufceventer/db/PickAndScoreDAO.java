package com.example.ufceventer.db;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ufceventer.models.PickAndScore;

import java.util.List;

@Dao
public interface PickAndScoreDAO {
    @Insert
    public void insert(PickAndScore... picksAndScores);

    @Update
    public void update(PickAndScore... picksAndScores);

    @Delete
    public void delete(PickAndScore... pickAndScore);

    @Query("select * from pickAndScores")
    public List<PickAndScore> getPicksAndScores();

    @Query("select * from pickAndScores where id=:number")
    public PickAndScore getPickAndScoreWithId(int number);
}

