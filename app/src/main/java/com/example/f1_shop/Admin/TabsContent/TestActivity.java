package com.example.f1_shop.Admin.TabsContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.f1_shop.Admin.AdminActivity;
import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.R;
import com.example.f1_shop.Screens.LandingPage;
import com.example.f1_shop.databinding.ActivityTestBinding;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TestActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.f1_shop.Admin.TabsContent";

    private ActivityTestBinding mActivityTestBinding;

    private Button backButton;

    private UserDAO mUserDAO;

    private Integer mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getDataBase();
        wireUpTabs();



    }




    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void wireUpTabs() {
        TabLayout tabLayout = findViewById(R.id.adminTabs);
        TabItem itemTab = findViewById(R.id.tabItems);
        TabItem teamTab = findViewById(R.id.tabTeams);
        TabItem userTab = findViewById(R.id.tabUsers);
        ViewPager viewPager = findViewById(R.id.viewPager);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static Intent IntentFactory( Context context){
        Intent intent = new Intent(context, TestActivity.class);
        return intent;
    }

    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra(USER_ID_KEY,UserId);
        return intent;

    }
}