package com.example.f1_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.databinding.ActivityLoginBinding;
import com.example.f1_shop.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mLoginBinding;

    private TextView mLoginCommand;
    private EditText mUsernameInput;
    private EditText mPasswordInput;
    private Button mLoginButton;

    private UserDAO mUserDAO;

    private String mUsername, mPassword;
    private Users mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getDataBase();
        wireUpDisplay();



    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void wireUpDisplay() {

        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mLoginBinding.getRoot());

        mLoginCommand = mLoginBinding.loginCommand;
        mUsernameInput = mLoginBinding.loginUsername;
        mPasswordInput = mLoginBinding.loginPassword;
        mLoginButton = mLoginBinding.loginButtonToLandPage;

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromScreen();

                if(isUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Intent intent = LandingPage.IntentFactory(getApplicationContext(), mUsers.getId());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean validatePassword() {
        return mUsers.getPassword().equals(mPassword);
    }

    private void getValuesFromScreen(){
        mUsername = mUsernameInput.getText().toString();
        mPassword = mPasswordInput.getText().toString();
    }

    private boolean isUserInDatabase(){

        mUsers = mUserDAO.getUserByUsername(mUsername);

        if(mUsers == null){
            Toast.makeText(this, "a user " + mUsername + " does not exist", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }


        return true;
    }


}