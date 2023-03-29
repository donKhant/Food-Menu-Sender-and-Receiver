package com.example.vote.Activities;

/**
 * Created by Don, Android Developer, Date unknown
 *
 * The code is no longer maintained.
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vote.Models.FoodModel;
import com.example.vote.R;
import com.example.vote.FragmentsToActivityAdapters.ValueEventAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private String category;
    private ArrayList<FoodModel> list;
    RecyclerView recyclerFood;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Bundle Extra = getIntent().getExtras();
        category = Extra.getString("category");

        InitializeFields();

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(category);
        toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerFood.setLayoutManager(linearLayoutManager);
        recyclerFood.setHasFixedSize(true);
        recyclerFood.setItemAnimator(null);

        RetrieveAndDisplayFood();

    }

    private void RetrieveAndDisplayFood() {

        FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren()) {
                    FoodModel f = new FoodModel(item.child("view").getValue().toString(), item.child("name").getValue().toString(), item.child("ingredients").getValue().toString(), item.child("price").getValue().toString(), item.getKey());
                    list.add(f);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ValueEventAdapter valueEventAdapter = new ValueEventAdapter(list, FoodActivity.this);
        recyclerFood.setAdapter(valueEventAdapter);
    }

    private void InitializeFields() {
        recyclerFood = findViewById(R.id.recyclerFood);
        list = new ArrayList<>();
    }

}
