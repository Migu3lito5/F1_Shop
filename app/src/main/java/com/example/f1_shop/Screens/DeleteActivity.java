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
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityDeleteBinding;
import com.example.f1_shop.databinding.ActivityFundsBinding;

public class DeleteActivity extends AppCompatActivity {

   private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";

    private ActivityDeleteBinding mActivityDeleteBinding;

    private Button deleteButton;
    private EditText usernameInput, passwordInput;
    private TextView deleteMessage;

    private UserDAO mUserDAO;
    private String username, password;
    private int mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        getDatabase();
        wireUpDisplay();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();

                if(verifyInformation()){
                    confirmDeletion();
                }

            }
        });

    }

    private void confirmDeletion() {

        if(password.equals(mUserDAO.getUserByUsername(username).getPassword())){
            mUserDAO.deleteUser(mUserDAO.getUserByUsername(username));
            Toast.makeText(getApplicationContext(), "You have deleted your account! Goodbye :(", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please make sure you info is correct to delete!", Toast.LENGTH_SHORT).show();
        }




    }

    private boolean verifyInformation() {

        Intent intent = getIntent();

        mUserId = intent.getIntExtra(USER_ID_KEY, -1);


        if(mUserDAO.getUserById(mUserId) != null){
            return true;
        }


        return false;
    }

    private void wireUpDisplay() {

       mActivityDeleteBinding = ActivityDeleteBinding.inflate(getLayoutInflater());
        setContentView(mActivityDeleteBinding.getRoot());


        deleteButton = mActivityDeleteBinding.deleteConfirm;
        usernameInput = mActivityDeleteBinding.deleteUsernameInput;
        passwordInput = mActivityDeleteBinding.deletePasswordInput;
        deleteMessage = mActivityDeleteBinding.deleteMessage;


    }

    private void getDatabase() {

        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

    }

    private void getInput(){

        password = passwordInput.getText().toString();
        username = usernameInput.getText().toString();

    }

    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, DeleteActivity.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }
}