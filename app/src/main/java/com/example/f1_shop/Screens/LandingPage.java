package com.example.f1_shop.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityLandingPageBinding;

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
        logout();


    }

    private void logout(){
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout();
            }
        });
    }

    private void confirmLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LandingPage.this,"Confirmed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LandingPage.this,"Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
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