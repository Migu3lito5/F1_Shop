package com.example.f1_shop.Admin.TabsContent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.f1_shop.DB.ItemDAO;
import com.example.f1_shop.DB.Items;
import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.R;

/*

    For reference page look at TestActivity and UsersFragment


 */

public class ItemsFragment extends Fragment {


    private Button addButton, editButton, deleteButton, confirmButton;
    private EditText typeOfItem, teamOfItem, priceOfItem, displayStatusOfItem, quantityOfItem;

    private String typeInput, teamInput, displayInput;
    private Integer qtyInput;
    private double priceInput;

    private Items anItem;

    private UserDAO mUserDAO;
    private ItemDAO mItemDAO;


    boolean clickedAddButton, clickedEditButton, clickedDeleteButton, showItemOnStore;
    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        getDatabase();
        wireUpDisplay(view);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();

                clickedAddButton = true;
                clickedEditButton = false;
                clickedDeleteButton = false;

                setUpForAdd();

            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();

                clickedEditButton = true;
                clickedAddButton = false;
                clickedDeleteButton = false;

                setUpForEdit();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();

                clickedDeleteButton = true;
                clickedAddButton = false;
                clickedEditButton = false;

                setUpForDelete();

            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clickedAddButton){
                    getValuesForAdd();

                    if(!itemExist(typeInput, teamInput)){
                        if(displayInput.equals("No")){
                           showItemOnStore = false;
                        } else {
                            showItemOnStore = true;
                        }
                        Items newItem = new Items(typeInput, teamInput, qtyInput, priceInput, showItemOnStore);
                        mItemDAO.createNewItem(newItem);
                        Toast.makeText(getActivity(),"Item " + typeInput + " for " + teamInput + " was added!", Toast.LENGTH_SHORT).show();
                    }
                }

                if(clickedDeleteButton){
                    getValuesForDelete();

                    if(itemExist2(typeInput, teamInput)){
                        mItemDAO.deleteItem(mItemDAO.getItemFromTypeAndTeam(typeInput, teamInput));
                        Toast.makeText(getActivity(), "Item " + typeInput + " for " + teamInput + " was deleted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Item " + typeInput + " for " + teamInput + " does not exist!", Toast.LENGTH_SHORT).show();
                    }
                }

                if (clickedEditButton){
                    getValuesForEdit();

                    if(itemExist2(typeInput, teamInput)){
                        mItemDAO.deleteItem(mItemDAO.getItemFromTypeAndTeam(typeInput, teamInput));
                        if(displayInput.equals("No")){
                            showItemOnStore = false;
                        } else {
                            showItemOnStore = true;
                        }
                        Items newItem = new Items(typeInput, teamInput, qtyInput, priceInput, showItemOnStore);
                        mItemDAO.createNewItem(newItem);
                        Toast.makeText(getActivity(),"Item " + typeInput + " for " + teamInput + " was changed!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Item " + typeInput + " for " + teamInput + " does not exist!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    private void setUpForAdd() {
        typeOfItem.setVisibility(View.VISIBLE);
        teamOfItem.setVisibility(View.VISIBLE);
        priceOfItem.setVisibility(View.VISIBLE);
        displayStatusOfItem.setVisibility(View.VISIBLE);
        quantityOfItem.setVisibility(View.VISIBLE);

    }

    private void getValuesForAdd(){
        typeInput = typeOfItem.getText().toString();
        teamInput = teamOfItem.getText().toString();
        displayInput = displayStatusOfItem.getText().toString();
        qtyInput = Integer.parseInt(quantityOfItem.getText().toString());
        priceInput = Double.parseDouble(priceOfItem.getText().toString());
    }

    private void setUpForEdit(){

        typeOfItem.setVisibility(View.VISIBLE);
        teamOfItem.setVisibility(View.VISIBLE);
        priceOfItem.setVisibility(View.VISIBLE);
        displayStatusOfItem.setVisibility(View.VISIBLE);
        quantityOfItem.setVisibility(View.VISIBLE);

    }

    private void getValuesForEdit(){
        typeInput = typeOfItem.getText().toString();
        teamInput = teamOfItem.getText().toString();
        displayInput = displayStatusOfItem.getText().toString();
        qtyInput = Integer.parseInt(quantityOfItem.getText().toString());
        priceInput = Double.parseDouble(priceOfItem.getText().toString());
    }

    private void setUpForDelete(){

        typeOfItem.setVisibility(View.VISIBLE);
        teamOfItem.setVisibility(View.VISIBLE);

    }

    private void getValuesForDelete(){
        typeInput = typeOfItem.getText().toString();
        teamInput = teamOfItem.getText().toString();
    }

    private boolean itemExist(String type, String team){
        if(mItemDAO.getItemFromTypeAndTeam(type,team) == null){
            return false;
        }

        Toast.makeText(getActivity(), "Item already Exists!", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean itemExist2(String type, String team){
        if(mItemDAO.getItemFromTypeAndTeam(type,team) == null){
            return false;
        }


        return true;
    }

    private void wireUpDisplay(View view) {
        addButton = view.findViewById(R.id.addItem);
        editButton = view.findViewById(R.id.editButton2);
        deleteButton = view.findViewById(R.id.delButton);
        confirmButton = view.findViewById(R.id.confirmButton3);

        typeOfItem = view.findViewById(R.id.inputItemType);
        teamOfItem = view.findViewById(R.id.inputTeam);
        priceOfItem = view.findViewById(R.id.inputPrice);
        displayStatusOfItem = view.findViewById(R.id.inputDisplay);
        quantityOfItem = view.findViewById(R.id.inputQty);

    }

    private void getDatabase() {
        mUserDAO = Room.databaseBuilder(getActivity(), ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        mItemDAO = Room.databaseBuilder(getActivity(), ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .ItemDAO();
    }

    private void  makeDisappear(){
        typeOfItem.setVisibility(View.INVISIBLE);
        teamOfItem.setVisibility(View.INVISIBLE);
        priceOfItem.setVisibility(View.INVISIBLE);
        displayStatusOfItem.setVisibility(View.INVISIBLE);
        quantityOfItem.setVisibility(View.INVISIBLE);
    }
}