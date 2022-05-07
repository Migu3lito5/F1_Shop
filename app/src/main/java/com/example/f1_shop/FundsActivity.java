package com.example.f1_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FundsActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);
    }




    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, FundsActivity.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }
}