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

import com.example.f1_shop.DB.Cart;
import com.example.f1_shop.DB.CartDAO;
import com.example.f1_shop.DB.ItemDAO;
import com.example.f1_shop.DB.Items;
import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.R;
import com.example.f1_shop.databinding.ActivityDeleteBinding;
import com.example.f1_shop.databinding.ActivityProductsBinding;

public class ProductsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.f1_shop.usersIdKey";
    private ActivityProductsBinding mActivityProductsBinding;

    private Button searchButton, cartButton, buyButton;
    private EditText teamInput, typeInput;
    private TextView displayItems;

    private UserDAO mUserDAO;
    private ItemDAO mItemDAO;
    private CartDAO mCartDAO;

    private Integer mUserId;
    private Integer mItemId;
    private String team, type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        getDatabase();
        wireUpDisplay();
        getIntentID();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromUser();
                if(valuesValid()){
                    updateDisplay();
                }
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromUser();
                if(valuesValid()){
                    mItemId = mItemDAO.getItemFromTypeAndTeam(type,team).getId();
                    Cart cart = new Cart(mUserId, mItemId, false );
                    mCartDAO.createNewEntry(cart);
                    Toast.makeText(getApplicationContext(),"Item added to Cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromUser();
                if(valuesValid()){
                    mItemId = mItemDAO.getItemFromTypeAndTeam(type,team).getId();

                    if(canPurchaseItem()){
                        Cart cart = new Cart(mUserId, mItemId, true );
                        mCartDAO.createNewEntry(cart);
                        mItemDAO.updateQty(mItemId);
                        mUserDAO.decreaseFunds(mItemDAO.getItemFromId(mItemId).getPrice(), mUserId);
                        Toast.makeText(getApplicationContext(),"Item Purchased!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),"Not enought funds to buy Item! ", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }

    private boolean canPurchaseItem() {

        if(mItemDAO.getItemFromTypeAndTeam(type,team).getPrice() <= mUserDAO.getUserById(mUserId).getFunds()){
            return true;
        }


        return false;
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        Items itemToDisplay = mItemDAO.getItemFromTypeAndTeam(type, team);
        sb.append("Item: \n");
        sb.append("Type: " + itemToDisplay.getType());
        sb.append("\nTeam: " + itemToDisplay.getTeam());
        sb.append("\nPrice: " + itemToDisplay.getPrice());
        sb.append("\nQTY: " + itemToDisplay.getQty());


        displayItems.setText(sb);

    }

    private boolean valuesValid() {

        if(mItemDAO.getItemFromTypeAndTeam(type,team) != null &&  mItemDAO.getItemFromTypeAndTeam(type,team).isOnDisplay()){
            return true;
        }
        return false;
    }

    private void getValuesFromUser() {
        type = typeInput.getText().toString();
        team = teamInput.getText().toString();
    }

    private void getIntentID() {
        Intent intent = getIntent();
        mUserId = intent.getIntExtra(USER_ID_KEY, -1);
    }

    private void wireUpDisplay() {

        mActivityProductsBinding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(mActivityProductsBinding.getRoot());

        searchButton = mActivityProductsBinding.searchItems;
        cartButton = mActivityProductsBinding.addToCartButton;
        buyButton = mActivityProductsBinding.buyNowButton;

        teamInput = mActivityProductsBinding.teamSearch;
        typeInput = mActivityProductsBinding.searchType;

        displayItems = mActivityProductsBinding.productDisplay;

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

    public static Intent IntentFactory(Context context, int UserId){
        Intent intent = new Intent(context, ProductsActivity.class);
        intent.putExtra(USER_ID_KEY, UserId);
        return intent;
    }
}