package com.example.f1_shop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void registerUser(Users... users);

    @Update
    void updateUser(Users... users);


    @Delete
    void deleteUser(Users users);

    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE + " WHERE mUsername = :username")
    Users getUserByUsername(String username);

    @Query("Select * FROM " + ShopDatabase.USER_TABLE + " WHERE mId = :userId")
    Users getUserById(Integer userId);

    @Query("SELECT * FROM " + ShopDatabase.USER_TABLE)
    List<Users> getAllUsers();

    @Query("UPDATE " + ShopDatabase.USER_TABLE + " SET mFunds = mFunds +:funds" + " WHERE mId = :userID")
    void updateFundsForUser(Double funds, Integer userID);


}
