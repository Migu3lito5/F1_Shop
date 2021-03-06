package com.example.f1_shop.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    references:
        https://medium.com/@barryalan2633/what-does-columninfo-do-ad30874d4daa
        https://developer.android.com/reference/androidx/room/ColumnInfo
        https://www.w3schools.com/sql/sql_update.asp -- used this website to re-freshen my memory of SQL
        GymLog video and assignment was also used to build the Database and all its components

 */
@Entity(tableName = ShopDatabase.USER_TABLE)
public class Users {

    @PrimaryKey(autoGenerate = true)
    private Integer mId;

    // remember to add reference
    @ColumnInfo(name = "mUsername")
    private String mUsername;

    @ColumnInfo(name = "mPassword")
    private String mPassword;

    @ColumnInfo(name = "mName")
    private String mName;

    @ColumnInfo(name = "mFunds")
    private double mFunds;

    @ColumnInfo(name = "mIsAdmin")
    private boolean mIsAdmin;

    public Users(String username, String password, String name, double funds, boolean isAdmin) {
        mUsername = username;
        mPassword = password;
        mName = name;
        mFunds = funds;
        mIsAdmin = isAdmin;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
    }

    public double getFunds() {
        return mFunds;
    }

    public void setFunds(double funds) {
        mFunds = funds;
    }
}
