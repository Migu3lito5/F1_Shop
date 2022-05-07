package com.example.f1_shop.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = ShopDatabase.ITEM_TABLE)
public class Items {
    // id | type | team | qty | price | onDisplay |
    @PrimaryKey(autoGenerate = true)
    private Integer mId;

    @ColumnInfo(name = "mType")
    private String mType;

    @ColumnInfo(name = "mTeam")
    private String mTeam;

    @ColumnInfo(name = "mQty")
    private Integer mQty;

    @ColumnInfo(name = "mPrice")
    private Double mPrice;

    @ColumnInfo(name = "OnDisplay")
    private boolean OnDisplay;



    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTeam() {
        return mTeam;
    }

    public void setTeam(String team) {
        mTeam = team;
    }

    public Integer getQty() {
        return mQty;
    }

    public void setQty(Integer qty) {
        mQty = qty;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public boolean isOnDisplay() {
        return OnDisplay;
    }

    public void setOnDisplay(boolean onDisplay) {
        OnDisplay = onDisplay;
    }
}
