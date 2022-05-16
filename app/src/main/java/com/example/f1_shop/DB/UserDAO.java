package com.example.f1_shop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
    references:
        https://medium.com/@barryalan2633/what-does-columninfo-do-ad30874d4daa
        https://developer.android.com/reference/androidx/room/ColumnInfo
        https://www.w3schools.com/sql/sql_update.asp -- used this website to re-freshen my memory of SQL
        GymLog video and assignment was also used to build the Database and all its components
        https://developer.android.com/reference/android/arch/persistence/room/Dao use this documentation to read more on the database worked

 */


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


    @Query("UPDATE " + ShopDatabase.USER_TABLE + " SET mFunds = mFunds -:itemPrice WHERE mId =:userId")
    void decreaseFunds(Double itemPrice, Integer userId);

}
