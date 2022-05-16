package com.example.f1_shop.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/*
    References
        GymLog Assignment to get the database built
 */
@Database(entities = {Users.class,Items.class, Cart.class}, version = 3)
public abstract class ShopDatabase extends RoomDatabase {

    public static final String dbName = "f1_shop.db";
    public static final String USER_TABLE = "users";
    public static final String ITEM_TABLE = "items";
    public static final String CART_TABLE = "cart";

    private static volatile ShopDatabase instance;
    private static final Object LOCK = new Object();

    public abstract UserDAO UserDAO();
    public abstract ItemDAO ItemDAO();
    public abstract CartDAO CartDAO();

    public static ShopDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            ShopDatabase.class,
                            dbName).build();
                }
            }
        }
        return instance;
    }

}
