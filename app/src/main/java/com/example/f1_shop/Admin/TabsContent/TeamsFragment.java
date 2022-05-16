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

import com.example.f1_shop.DB.ItemDAO;
import com.example.f1_shop.DB.ShopDatabase;
import com.example.f1_shop.R;


/*

    For reference page look at TestActivity and UsersFragment


 */
public class TeamsFragment extends Fragment {

    private Button deleteButton, confirmButton;
    private EditText inputTeam;

    String teamName;
    private ItemDAO mItemDAO;

    boolean clickedOnDelete, clickedOnAdd;

    public TeamsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        getDatabase();
        wireUpDisplay(view);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedOnAdd = false;
                clickedOnDelete = true;
               inputTeam.setVisibility(View.VISIBLE);

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickedOnDelete){
                    getValuesFromDelete();
                    if(deleteTeam(teamName)){
                        Toast.makeText(getActivity(), "Team " + teamName + " Deleted!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Team " + teamName + " does not Exist!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void getValuesFromDelete() {
        teamName = inputTeam.getText().toString();
    }

    private boolean deleteTeam(String teamName) {
        if(mItemDAO.deleteTeam(teamName) >= 1){
            return true;
        }
        return false;
    }

    private void wireUpDisplay(View view){

        deleteButton = view.findViewById(R.id.deleteTeam);
        confirmButton = view.findViewById(R.id.confirmButton4);
        inputTeam = view.findViewById(R.id.teamInput2);

    }



    private void getDatabase() {

        mItemDAO = Room.databaseBuilder(getActivity(), ShopDatabase.class, ShopDatabase.dbName)
                .allowMainThreadQueries()
                .build()
                .ItemDAO();
    }


}