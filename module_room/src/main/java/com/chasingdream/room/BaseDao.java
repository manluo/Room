package com.chasingdream.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import io.reactivex.Completable;

/**
 * @author nyl
 * @des 基础类
 * @date 2020/6/8
 */
@Dao
public interface BaseDao<T> {
    //增
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(T... ts);

    //删
    @Delete()
    Completable delete(T... ts);

    @Delete()
    void deleteNoRx(T... ts);

    //改
    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(T... ts);

    //改
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNoRx(T... ts);
}
