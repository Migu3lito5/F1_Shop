package com.example.f1_shop.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityAdminBinding;

// old design, testActivity is the new design that went into final iteration
public class AdminActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.f1_shop.Admin";

    private ActivityAdminBinding mActivityAdminBinding;
    private TextView mAdminWelcome;
    private Button mEditTeams;
    private Button mEditItems;
    private Button mEditUsers;

    //Intent user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        wireUpDisplay();

        mEditItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mEditUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        mEditTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void wireUpDisplay() {
        mActivityAdminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(mActivityAdminBinding.getRoot());

        mAdminWelcome = mActivityAdminBinding.welcomeAdmin;
        mEditItems = mActivityAdminBinding.editItems;
        mEditTeams = mActivityAdminBinding.editTeams;
        mEditUsers = mActivityAdminBinding.editUsers;

    }

    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, AdminActivity.class);
        intent.putExtra(USER_ID_KEY,UserId);
        return intent;

    }
}