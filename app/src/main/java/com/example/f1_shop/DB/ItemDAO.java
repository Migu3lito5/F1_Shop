package com.example.f1_shop.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    void createNewItem(Items... items);

    @Update
    void updateItem(Items... items);

    @Delete
    void deleteItem(Items items);

    @Query("SELECT * FROM " + ShopDatabase.ITEM_TABLE)
    List<Items> getAllItems();







}
