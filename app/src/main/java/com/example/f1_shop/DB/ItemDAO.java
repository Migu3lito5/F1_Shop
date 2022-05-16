package com.example.f1_shop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
    references:
        For references look at UsersDAO

 */
@Dao
public interface ItemDAO {

    @Insert
    void createNewItem(Items... items);

    @Update
    void updateItem(Items... items);

    @Delete
    void deleteItem(Items items);

    @Query("DELETE FROM " + ShopDatabase.ITEM_TABLE + " WHERE mTeam =:team")
    int deleteTeam(String team);

    @Query("SELECT * FROM " + ShopDatabase.ITEM_TABLE)
    List<Items> getAllItems();

    @Query("SELECT * FROM " + ShopDatabase.ITEM_TABLE + " WHERE mType =:type AND mTeam =:team")
    Items getItemFromTypeAndTeam(String type, String team);


    @Query("SELECT * FROM " + ShopDatabase.ITEM_TABLE + " WHERE mId =:itemID")
    Items getItemFromId(Integer itemID);

    @Query("UPDATE " + ShopDatabase.ITEM_TABLE + "  SET mQty = mQty - 1 " + " WHERE mId =:itemID")
    void updateQty(Integer itemID);



}
