package com.example.f1_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.databinding.ActivityCreateBinding;

public class CreateActivity extends AppCompatActivity {

    ActivityCreateBinding mActivityCreateBinding;

    TextView mCreateCommand;

    EditText mInputName, mInputUsername, mInputPassword;
    Button mCreateAccount;

    String nameEntered, userNameEntered, passwordEntered;
    Users aUser;

    UserDAO mUserDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        wireUpDisplay();
        getDataBase();
        getValuesFromScreen();

        if(!UserExist()){

        } else {
            createAccount();
        }




    }

    private void createAccount() {
        //TODO: Finish CreateAccount
        // Insert all the data given by user into the database
        // Make a toast confirming account created
        // addFunds() method asks user to add funds
        // take users to Landing activity


    }

    private boolean UserExist() {
        //TODO : Finish UserExists()
        aUser = mUserDAO.getUserByUsername(userNameEntered);

        if(aUser.getUsername().equals(userNameEntered)){
            //Toast saying username already exists
            return false;
        } else {
            //Toast saying info is ok
            return true;
        }


    }

    private void getValuesFromScreen() {
        nameEntered = mInputName.getText().toString();
        userNameEntered = mInputPassword.getText().toString();
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
        mCreateAccount = mActivityCreateBinding.createButtonToLandPage;



    }

    private void addFunds(){
        //TODO: Finish addFunds()
    }


}