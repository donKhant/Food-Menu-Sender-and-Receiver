package com.example.vote.FragmentsOfToOrderListActivity;


/**
 * Created by Don
 * free to use for educational purposes
 */


// show data temporarily added to Sqlite local db.

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vote.LocalDB.DatabaseHelper;
import com.example.vote.LocalDB.LocalDBAdapter;
import com.example.vote.Models.RemovableFoodModel;
import com.example.vote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentToOrder extends Fragment {

    private String hardCodedWaiterID;
    private View FragmentView;
    private TextView clickToOrder, numOfOrders;
    private ImageView clearAll;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerOrderView;
    List<RemovableFoodModel> foodList;
    private DatabaseHelper db;
    private LocalDBAdapter localDBAdapter;
    private String[] strArr;
    private String tableNr;

    public FragmentToOrder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentView = inflater.inflate(R.layout.fragment_to_order, container, false);

        InitializeFields();

        // setting up recyclerview for displaying data
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerOrderView.setLayoutManager(linearLayoutManager);
        recyclerOrderView.setHasFixedSize(true);
        recyclerOrderView.setItemAnimator(null);

        howManyOrders(); // display count of items in db
        RetrieveAndDisplayLocalOrders();

        clickToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // confirm and then delete all the items from local db
                youWannaClearLocalDb();
            }
        });

        return FragmentView;
    }

    private void howManyOrders() {
        long count = db.getFoodCount();
        numOfOrders.setText("Total orders - " + count);
    }

    private void InitializeFields() {
        hardCodedWaiterID = "Waiter 1";

        recyclerOrderView = FragmentView.findViewById(R.id.recyclertoOrder);
        clickToOrder = FragmentView.findViewById(R.id.orderNow);
        clearAll = FragmentView.findViewById(R.id.clear);
        numOfOrders = FragmentView.findViewById(R.id.numOfOrders);

        db = new DatabaseHelper(getContext());
    }

    private void orderNow(String tableNr) {

        // retrieve data from local database and transfer it to Firebase Realtime Database.

        foodList = db.getFood();
        for (RemovableFoodModel removableFoodModel: foodList) {
            Map<String, Object> order = new HashMap<>();
            order.put("view", removableFoodModel.getFoodUrl());
            order.put("name", removableFoodModel.getName());
            order.put("ingredients", removableFoodModel.getIngredients());
            order.put("price", removableFoodModel.getPrice());

            String id = FirebaseDatabase.getInstance().getReference().child("waiters").child(hardCodedWaiterID).child(tableNr).child("orders").push().getKey();
            FirebaseDatabase.getInstance().getReference().child("waiters").child(hardCodedWaiterID).child(tableNr).child("orders").child(id).updateChildren(order);
        }

        db.deleteAllFood();
        foodList.clear();
        localDBAdapter.notifyDataSetChanged();

    }

    private void youWannaClearLocalDb() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Would you like to clear the inventory?");

        //builder.setTitle("The orders have been sent!");

        //builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();

            db.deleteAllFood();
            foodList.clear();
            localDBAdapter.notifyDataSetChanged();
            //Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showConfirmDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext().getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.confirm_dialog, null);

        // Spinner element
        Spinner spino = view.findViewById(R.id.coursesspinner);

        strArr = getResources().getStringArray(R.array.programming_languages);

        ArrayAdapter ad
                = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                strArr);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spino.setAdapter(ad);

        // Spinner click listener
        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableNr = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getContext(), "chosen " + tableNr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(view);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "chosen " + autocompleteTV.getText(), Toast.LENGTH_SHORT).show();

                if (tableNr.equals("ပွဲနံပါတ်၁") || tableNr.equals("ပွဲနံပါတ်၂") || tableNr.equals("ပွဲနံပါတ်၃") || tableNr.equals("ပွဲနံပါတ်၄") || tableNr.equals("ပွဲနံပါတ်၅")) {
                    alertDialog.dismiss();
                    //alreadyOrdered();
                    if (db.getFoodCount() > 0) {
                        orderNow(tableNr);
                    } else {
                        Toast.makeText(getContext(), "Inventory is Empty.", Toast.LENGTH_SHORT).show();
                    }
                    //youWannaClearLocalDb();
                } else {
                    Toast.makeText(getContext(), "choose table first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean orderAgain() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(tableNr + " is already occupied.\nDo you want to proceed wth new orders?");

        builder.setTitle("Warning !");

        builder.setCancelable(false);
        builder.setPositiveButton("Yes, proceed", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
            orderNow(tableNr);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return false;
    }

    private void alreadyOrdered() {
        FirebaseDatabase.getInstance().getReference().child("waiters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(hardCodedWaiterID).hasChild(tableNr)) {
                    orderAgain();
                } else {
                    orderNow(tableNr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void RetrieveAndDisplayLocalOrders() {
        foodList = db.getFood();
        localDBAdapter = new LocalDBAdapter(getContext(), foodList);
        recyclerOrderView.setAdapter(localDBAdapter);

    }


}
