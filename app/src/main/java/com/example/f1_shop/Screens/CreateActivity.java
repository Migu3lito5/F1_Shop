package com.example.f1_shop.Screens;

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
import com.example.f1_shop.FundsActivity;
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityCreateBinding;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding mActivityCreateBinding;

    private TextView mCreateCommand;

    private EditText mInputName, mInputUsername, mInputPassword;
    private Button mCreateAccount;

    private UserDAO mUserDAO;
    private String nameEntered, userNameEntered, passwordEntered;
    private Users mUsers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getDataBase();
        wireUpDisplay();


        //maybe add one button that validates and another that takes to landing page
        mCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getValuesFromScreen();
                if(UserExist()){
                    createAccount();

                }
            }
        });

    }

    private void createAccount() {

        mUsers = new Users(userNameEntered, passwordEntered, nameEntered, 0.0, false);
        mUserDAO.registerUser(mUsers);

        Intent intent = FundsActivity.IntentFactory(getApplicationContext(), mUsers.getId());


    }

    private boolean UserExist() {

        mUsers = mUserDAO.getUserByUsername(userNameEntered);

        if(mUsers != null){
            Toast.makeText(CreateActivity.this, "Username already exists!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        mUsers = new Users(userNameEntered, passwordEntered, nameEntered, 0.0, false);
        return true;
    }

    private void getValuesFromScreen() {
        nameEntered = mInputName.getText().toString();
        userNameEntered = mInputUsername.getText().toString();
        passwordEntered = mInputPassword.getText().toString();
    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void wireUpDisplay() {
        mActivityCreateBinding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(mActivityCreateBinding.getRoot());

        mCreateCommand = mActivityCreateBinding.createCommand;
        mInputUsername = mActivityCreateBinding.createUsername;
        mInputName = mActivityCreateBinding.createNameForUser;
        mInputPassword = mActivityCreateBinding.createPassword;
        mCreateAccount = mActivityCreateBinding.registerButton;

    }


}