package com.example.f1_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.databinding.ActivityLandingPageBinding;

import java.util.List;

public class LandingPage extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";
    private static final String PREFERENCES_KEY = "com.example.f1_shop.PREFERENCES_KEY";

    private ActivityLandingPageBinding mLandingPageBinding;

    private Button mAdminButton;
    private Button mLogoutButton;

    private UserDAO mUserDAO;

    private Integer mUserId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getDatabase();
        wireUpDisplay();

        showAdminButton();


    }

    private void showAdminButton(){
        Intent intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);

        if(mUserDAO.getUserById(mUserId).isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }

    }



    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void wireUpDisplay(){
        mLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        mAdminButton = mLandingPageBinding.adminButton;
        mLogoutButton = mLandingPageBinding.LogoutButton;
    }


    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }
}