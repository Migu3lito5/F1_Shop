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
public interface CartDAO {

    @Insert
    void createNewEntry(Cart... carts);


    @Delete
    void deleteEntry(Cart cart);

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE )
    List<Cart> listOfCartEntries();

    @Query("SELECT * FROM " + ShopDatabase.CART_TABLE + " WHERE UserID =:userId AND ItemID =:itemId")
    Cart getEntryByUserAndItemID(Integer userId, Integer itemId);

    @Query("SELECT ItemID FROM " + ShopDatabase.CART_TABLE + " WHERE UserID =:userId")
    Integer getItemID(Integer userId);

    @Query("UPDATE " + ShopDatabase.CART_TABLE + " SET Bought =:bool WHERE ItemID =:itemId AND UserID =:userId")
    void updateBoughtValue(boolean bool, Integer itemId, Integer userId);


}
