package com.example.f1_shop.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding mActivityMainBinding;


    private Button mCreateButton;
    private Button mLoginButton;
    private TextView mWelcomeMessage;
    private ImageView mF1Logo;

    private UserDAO mUserDAO;

    private int mUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        mF1Logo = mActivityMainBinding.F1Logo;
        mWelcomeMessage = mActivityMainBinding.introMessage;
        mLoginButton = mActivityMainBinding.loginButton;
        mCreateButton = mActivityMainBinding.createButton;

        getDataBase();
        checkForUser();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void checkForUser() {
        if(mUserId != -1){
            return;
        }

        // SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY,Context.MODE_PRIVATE);

        List<Users> usersList = mUserDAO.getAllUsers();

        if(usersList.size() <= 0){
            Users adminUser = new Users("admin2","admin2", "Admin2", 1000.0, true);

            Users testUser = new Users("testuser123", "test1234", "Bob" , 0.0, false);


            mUserDAO.registerUser(adminUser);
            mUserDAO.registerUser(testUser);


        }

    }

}