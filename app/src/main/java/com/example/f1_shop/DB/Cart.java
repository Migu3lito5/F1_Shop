package com.example.f1_shop.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    references:
        For references look at Users.class

 */
@Entity(tableName = ShopDatabase.CART_TABLE)
public class Cart {

    // ID | USERID | ITEMID | BOUGHT |

    @PrimaryKey(autoGenerate = true)
    private Integer mId;

    @ColumnInfo(name = "UserID")
    private Integer mUserId;

    @ColumnInfo(name = "ItemID")
    private Integer mItemId;

    @ColumnInfo(name = "Bought")
    private boolean mBought;


    public Cart(Integer userId, Integer itemId, boolean bought) {
        mUserId = userId;
        mItemId = itemId;
        mBought = bought;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        mUserId = userId;
    }

    public Integer getItemId() {
        return mItemId;
    }

    public void setItemId(Integer itemId) {
        mItemId = itemId;
    }

    public boolean isBought() {
        return mBought;
    }

    public void setBought(boolean bought) {
        mBought = bought;
    }
}
