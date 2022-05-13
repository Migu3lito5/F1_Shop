package com.example.f1_shop.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
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
    private Button mCreateAccount, mToLandPage;

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

        mToLandPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFunds();
            }

        });

    }

    private void createAccount() {
        mUsers = new Users(userNameEntered, passwordEntered, nameEntered, 0.0, false);
        mUserDAO.registerUser(mUsers);
        Toast.makeText(getApplicationContext(), "Account Created",Toast.LENGTH_SHORT).show();
        mToLandPage.setVisibility(View.VISIBLE);

    }

    private boolean UserExist() {

        mUsers = mUserDAO.getUserByUsername(userNameEntered);

        if(mUsers != null){
            Toast.makeText(CreateActivity.this, "Username already exists!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;
    }

    private void getValuesFromScreen() {
        nameEntered = mInputName.getText().toString();
        userNameEntered = mInputUsername.getText().toString();
        passwordEntered = mInputPassword.getText().toString();
    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(getApplicationContext(), ShopDatabase.class, ShopDatabase.dbName)
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
        mToLandPage = mActivityCreateBinding.toLandPage;

    }

    private void addFunds(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateActivity.this);
        builder.setMessage("Do you want to add funds?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = FundsActivity.IntentFactory(getApplicationContext(), mUserDAO.getUserByUsername(userNameEntered).getId());
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = LandingPage.IntentFactory(getApplicationContext(), mUserDAO.getUserByUsername(userNameEntered).getId());
                startActivity(intent);
            }
        });
        builder.create();
        builder.show();
    }

}