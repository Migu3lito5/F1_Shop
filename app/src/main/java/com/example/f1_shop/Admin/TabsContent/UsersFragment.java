package com.example.f1_shop.Admin.TabsContent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.DB.UserDAO;
import com.example.f1_shop.DB.Users;
import com.example.f1_shop.R;
import com.example.f1_shop.Screens.CreateActivity;


public class UsersFragment extends Fragment {

    private EditText oldPassword, newPassword, oldUsername, newUsername, name, admininput;
    private Button addButton, editButton, deleteButton, confirm;

    String inputPassword, inputUsername, inputName, isAdmin;
    boolean Admin = false;
    Users users;
    private UserDAO mUserDAO;



    boolean clickedAddButton, clickedDeleteButton, clickedEditButton;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        getDataBase();
        wireUpDisplay(view);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();
                clickedAddButton = true;
                clickedDeleteButton = false;
                clickedEditButton = false;

                newPassword.setVisibility(View.VISIBLE);
                newUsername.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                admininput.setVisibility(View.VISIBLE);
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();
                clickedDeleteButton = true;
                clickedEditButton = false;
                clickedAddButton = false;

                oldUsername.setVisibility(View.VISIBLE);


            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDisappear();
                clickedDeleteButton = false;
                clickedEditButton = true;
                clickedAddButton = false;

                name.setVisibility(View.VISIBLE);
                oldUsername.setVisibility(View.VISIBLE);
                newUsername.setVisibility(View.VISIBLE);
                oldPassword.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                admininput.setVisibility(View.VISIBLE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clickedAddButton){
                    getValuesFromAdd();
                    if(userNotExist()){
                        createAccount();
                    }
                }

                if(clickedDeleteButton){
                    getValuesForDelete();
                    if(userExist()){
                        deleteAccount();
                    }
                }

                if(clickedEditButton){
                    getValuesFromEdit();
                    if(userExist()){
                        inputName = name.getText().toString();
                        inputUsername = newUsername.getText().toString();
                        inputPassword = newPassword.getText().toString();
                        isAdmin = admininput.getText().toString();

                        if(isAdmin.equals("Yes")){
                            Admin = true;
                        }


                        users.setName(inputName);
                        users.setPassword(inputPassword);
                        users.setUsername(inputUsername);
                        users.setAdmin(Admin);

                        if(mUserDAO.getUserByUsername(users.getUsername()) == null){
                            mUserDAO.updateUser(users);
                        } else {
                            Toast.makeText(getActivity(), "Username already exists!", Toast.LENGTH_SHORT)
                                    .show();
                        }

                    }
                }

            }
        });
        return view;
    }

    private boolean userExist() {
        users = mUserDAO.getUserByUsername(inputUsername);

        if(users != null){
            return true;
        }

        return false;
    }


    private boolean userNotExist() {
        users = mUserDAO.getUserByUsername(inputUsername);

        if(users != null){
            Toast.makeText(getActivity(), "Username already exists!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;

    }

    private void getValuesFromAdd() {
        inputName = name.getText().toString();
        inputUsername = newUsername.getText().toString();
        inputPassword = newPassword.getText().toString();
        isAdmin = admininput.getText().toString();

    }

    private void getValuesForDelete() {
        inputUsername = oldUsername.getText().toString();
    }

    private void getValuesFromEdit(){
        inputUsername = oldUsername.getText().toString();
    }

    private void wireUpDisplay(View view) {

        addButton = view.findViewById(R.id.addUserButton2);
        deleteButton = view.findViewById(R.id.deleteUserButton);
        editButton = view.findViewById(R.id.editUserButton);
        confirm = view.findViewById(R.id.confirmButton);
        oldPassword = view.findViewById(R.id.oldPassword);
        newPassword = view.findViewById(R.id.newPassword);
        oldUsername = view.findViewById(R.id.oldUsername);
        newUsername = view.findViewById(R.id.newUsername);
        name = view.findViewById(R.id.setName);
        admininput = view.findViewById(R.id.adminStatus);

    }

    private void getDataBase() {
        mUserDAO = Room.databaseBuilder(getActivity(), ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .UserDAO();
    }

    private void createAccount() {

        if(isAdmin.equals("Yes")){
            Admin = true;
        }
        users = new Users(inputUsername, inputPassword, inputName, 0.0, Admin);
        mUserDAO.registerUser(users);
        Toast.makeText(getActivity(), "Account Created",Toast.LENGTH_SHORT).show();

    }

    private void deleteAccount() {

        mUserDAO.deleteUser(mUserDAO.getUserByUsername(inputUsername));
        Toast.makeText(getActivity(), "User " + inputUsername + " Deleted!", Toast.LENGTH_SHORT).show();

    }

    private void makeDisappear(){
        oldPassword.setVisibility(View.INVISIBLE);
        newPassword.setVisibility(View.INVISIBLE);
        oldUsername.setVisibility(View.INVISIBLE);
        newUsername.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        admininput.setVisibility(View.INVISIBLE);
    }



}