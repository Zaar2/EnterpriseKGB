package com.zaar.meatkgb2_m.model.local.api_room.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<T> obj);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(T obj);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(List<T> obj);

    @Delete
    int delete(T obj);

    @Delete
    int delete(List<T> obj);
}