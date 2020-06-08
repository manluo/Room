package com.chasingdream.room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao extends BaseDao<User> {

    //一次查询
    @Query("SELECT * FROM user")
    Single<List<User>> getAllUsers();


    //查
    @Query("SELECT * FROM user WHERE name = :name")
    Single<User> getAllUsersByName(String name);

    /**
     * 清空所有数据
     */
    @Query("DELETE FROM user")
    Completable deleteAllUsers();

    /**
     * 根据id 查询数据
     *
     * @param id
     * @return
     */
    @Query("SELECT * FROM user WHERE id = :id")
    Single<User> getUserById(int id);

}