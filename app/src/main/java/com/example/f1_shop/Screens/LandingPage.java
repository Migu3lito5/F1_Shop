package com.example.f1_shop.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f1_shop.Admin.AdminActivity;
import com.example.f1_shop.Admin.TabsContent.TestActivity;
import com.example.f1_shop.DB.Cart;
import com.example.f1_shop.DB.CartDAO;
import com.example.f1_shop.DB.ItemDAO;
import com.example.f1_shop.DB.Items;
import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityLandingPageBinding;

import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";
    private static final String PREFERENCES_KEY = "com.example.f1_shop.PREFERENCES_KEY";

    private ActivityLandingPageBinding mLandingPageBinding;

    private TextView mDisplayName;
    private Button mAdminButton;
    private Button mLogoutButton;

    private Button productButton, teamsButton, infoButton, cartButton, fundsButton, historyButton;

    private UserDAO mUserDAO;
    private ItemDAO mItemDAO;
    private CartDAO mCartDAO;

    private Integer mUserId;
    private Integer mItemId;
    private List<Cart> listForCart = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        getDatabase();
        wireUpDisplay();
        showAdminButton();
        displayUsername();


        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TestActivity.IntentFactory(getApplicationContext(),mUserId);
                startActivity(intent);
            }
        });

        fundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFunds();
            }
        });

        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTeams();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInfo();
            }
        });

        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductsPage();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseHistory(getValuesFromCart());
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPurchaseHistory2(getValuesFromCart2());
            }
        });

        logout();
    }

    private StringBuilder getValuesFromCart2() {
        StringBuilder sb = new StringBuilder("In Cart: \n");
        List<Cart> entriesNeeded = new ArrayList<>();
        List<Cart> AllEntries = mCartDAO.listOfCartEntries();
        mItemId = mCartDAO.getItemID(mUserId);
        Items item;
        if(AllEntries.size() <= 0){
            return sb;
        }

        if(mItemId == null){
            return sb;
        } else {

            for(int i = 0; i < AllEntries.size(); i++){
                if(AllEntries.get(i).getUserId().equals(mUserId) && !AllEntries.get(i).isBought()){
                    entriesNeeded.add(AllEntries.get(i));
                }
            }

            for(int i = 0; i < entriesNeeded.size(); i++){
                item = mItemDAO.getItemFromId(entriesNeeded.get(i).getItemId());
                sb.append("Item: " + item.getType() + ", " + item.getTeam() + ", " + item.getPrice() + "\n");
            }

        }

        listForCart = entriesNeeded;
        return sb;
    }

    private StringBuilder getValuesFromCart() {
        StringBuilder sb = new StringBuilder("Bought: \n");
        List<Cart> entriesNeeded = new ArrayList<>();
        List<Cart> AllEntries = mCartDAO.listOfCartEntries();
        mItemId = mCartDAO.getItemID(mUserId);
        Items item;
        if(AllEntries.size() <= 0){
            return sb;
        }

        if(mItemId == null){
            return sb;
        } else {

            // Fixed Bug
            for(int i = 0; i < AllEntries.size(); i++){
                if(AllEntries.get(i).getUserId().equals(mUserId) && AllEntries.get(i).isBought()){
                    entriesNeeded.add(AllEntries.get(i));
                }
            }

            for(int i = 0; i < entriesNeeded.size(); i++){
                item = mItemDAO.getItemFromId(entriesNeeded.get(i).getItemId());
                sb.append("Item: " + item.getType() + ", " + item.getTeam() + ", " + item.getPrice() + "\n");
            }

        }

        return sb;
    }

    private void showPurchaseHistory2(StringBuilder sb) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LandingPage.this);
        builder.setMessage(sb);

        builder.setPositiveButton("Purchase Items?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BuyAllItems();
            }
        });

        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        builder.create();
        builder.show();


    }

    private void BuyAllItems() {

        Double totalPrice = 0.0;
        Cart cart;
        for(int i = 0; i < listForCart.size(); i++){

            totalPrice = totalPrice + mItemDAO.getItemFromId(listForCart.get(i).getItemId()).getPrice();

        }

        if(mUserDAO.getUserById(mUserId).getFunds() >= totalPrice){
            mUserDAO.decreaseFunds(totalPrice, mUserId);
            Toast.makeText(this, "Items Purchased!", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < listForCart.size(); i++){
                mCartDAO.updateBoughtValue(true, listForCart.get(i).getItemId(), mUserId);

            }

        } else {
            Toast.makeText(this, "Not enough Funds!", Toast.LENGTH_SHORT).show();

        }


    }

    private void showPurchaseHistory(StringBuilder sb) {


        AlertDialog.Builder builder = new AlertDialog.Builder(LandingPage.this);
        builder.setMessage(sb);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();

    }

    private void startProductsPage() {
        Intent intent = ProductsActivity.IntentFactory(getApplicationContext(), mUserId);
        startActivity(intent);
    }

    private void displayInfo() {
        Users displayUser = mUserDAO.getUserById(mUserId);

        StringBuilder sb = new StringBuilder("Info: \n");
        sb.append("ID: " + displayUser.getId());
        sb.append("\nName: " + displayUser.getName());
        sb.append("\nUsername: " + displayUser.getUsername());
        sb.append("\nPassword : " + displayUser.getPassword());
        sb.append("\nFunds: " + displayUser.getFunds());


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(sb);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Delete Account?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               Intent intent = DeleteActivity.IntentFactory(getApplicationContext(), mUserId);
                startActivity(intent);
            }
        });
        builder.create();
        builder.show();


    }

    private void displayTeams() {
        List<Items> teamsList = mItemDAO.getAllItems();
        StringBuilder sb = new StringBuilder("");

        for(int i = 0; i < teamsList.size(); i++){
            sb.append(" ").append(teamsList.get(i).getTeam());
        }



        AlertDialog.Builder builder = new AlertDialog.Builder(LandingPage.this);
        builder.setMessage("Teams:\n" + sb);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        builder.create();
        builder.show();
    }


    private void addFunds() {

            AlertDialog.Builder builder = new AlertDialog.Builder(LandingPage.this);
            builder.setMessage("Your funds are " + mUserDAO.getUserById(mUserId).getFunds() + " Do you want to add funds?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = FundsActivity.IntentFactory(getApplicationContext(), mUserDAO.getUserById(mUserId).getId());
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create();
            builder.show();
        }

    private void displayUsername() {

        mDisplayName.setText("Welcome " + mUserDAO.getUserById(mUserId).getName().toString());

    }

    private void logout(){
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout();
            }
        });
    }

    private void confirmLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LandingPage.this,"Confirmed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LandingPage.this,"Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create();
        builder.show();
    }

    private void showAdminButton(){
        Intent intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);

        if(mUserDAO.getUserById(mUserId).isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }
    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        mItemDAO = Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .ItemDAO();

        mCartDAO =  Room.databaseBuilder(this, ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .CartDAO();

    }

    private void wireUpDisplay(){
        mLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        mAdminButton = mLandingPageBinding.adminButton;
        mLogoutButton = mLandingPageBinding.LogoutButton;
        mDisplayName = mLandingPageBinding.displayName;

        productButton = mLandingPageBinding.productButton;
        teamsButton = mLandingPageBinding.teamsButton;
        fundsButton = mLandingPageBinding.toFundsButton;
        infoButton = mLandingPageBinding.InfoButton;
        cartButton = mLandingPageBinding.cartButton;
        historyButton = mLandingPageBinding.historyButton;

    }


    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, LandingPage.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }


}