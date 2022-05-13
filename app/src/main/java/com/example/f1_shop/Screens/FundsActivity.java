package com.example.f1_shop.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
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
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityFundsBinding;

public class FundsActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";

    private ActivityFundsBinding mActivityFundsBinding;

    private TextView mFundsMessage;
    private EditText mAddFundsInput, mInputPassword;
    private Button mConfirm;

    private UserDAO mUserDAO;
    private Users theUser;

    private String password;
    private Double funds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);

        wireUpDisplay();
        getDataBase();
        getUserFromIntent();

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();

                if(funds > 0.0 && funds < 1001.0){
                    if(theUser.getPassword().equals(password)){

                        mUserDAO.updateFundsForUser(funds, theUser.getId());
                        Toast.makeText(getApplicationContext(), funds + " have been added!", Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = LandingPage.IntentFactory(getApplicationContext(), theUser.getId());
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please make sure the password is correct",Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Make amounts bigger than 0 and less than 1000", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    private void getUserFromIntent() {

        Intent intent = getIntent();
        int userId = intent.getIntExtra(USER_ID_KEY, -1);

        theUser = mUserDAO.getUserById(userId);

    }

    private void getValuesFromDisplay() {

        //using password as temp variable to parse the input and put it into funds
        password = mAddFundsInput.getText().toString();
        funds = Double.parseDouble(password);

        password = mInputPassword.getText().toString();
    }


    private void wireUpDisplay() {

        mActivityFundsBinding = ActivityFundsBinding.inflate(getLayoutInflater());
        setContentView(mActivityFundsBinding.getRoot());

        mFundsMessage = mActivityFundsBinding.fundMessage;
        mAddFundsInput = mActivityFundsBinding.fundsToAdd;
        mInputPassword = mActivityFundsBinding.userPassword;
        mConfirm = mActivityFundsBinding.button;

    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, FundsActivity.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }
}