package com.example.vote.Activities;

/**
 * Created by Don, Android Developer, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vote.Models.FoodModel;
import com.example.vote.R;
import com.example.vote.RecyclerViewAdapters.ShowFoodAdapter;
import com.example.vote.FragmentsToActivityAdapters.ValueEventAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerSearchView;
    EditText searchBox;
    Button searchButton;
    private ShowFoodAdapter modelAdapter;
    private ArrayList<FoodModel> list;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        list = new ArrayList<>();
        recyclerSearchView = findViewById(R.id.recyclerSearchView);
        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        searchBox.requestFocus();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerSearchView.setLayoutManager(linearLayoutManager);
        recyclerSearchView.setHasFixedSize(true);
        recyclerSearchView.setItemAnimator(null);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    text = searchBox.getText().toString();

                    FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (list.size() > 0) {
                                list.clear();
                            }

                            for (DataSnapshot category: snapshot.getChildren()) {
                                for (DataSnapshot item: category.getChildren()) {
                                    if (item.child("name").getValue().toString().contains(text)) {
                                        FoodModel f = new FoodModel(item.child("view").getValue().toString(), item.child("name").getValue().toString(), item.child("ingredients").getValue().toString(), item.child("price").getValue().toString(), item.getKey());
                                        list.add(f);
                                    }
                                }
                            }
                            ValueEventAdapter valueEventAdapter = new ValueEventAdapter(list, SearchActivity.this);
                            recyclerSearchView.setAdapter(valueEventAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    return true;
                }
                return false;
            }
        });

        /*
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchBox.getText().toString();

                DatabaseReference ServerRef = FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child("food"); // note: .child("categories").child(food)....
                Query firebaseSearchQuery = ServerRef.orderByChild("name").startAt(text);
                //Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");

                FirebaseRecyclerOptions<FoodModel> options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<FoodModel>() {
                            @NonNull
                            public FoodModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new FoodModel(snapshot.child("view").getValue().toString(), snapshot.child("name").getValue().toString(), snapshot.child("ingredients").getValue().toString(), snapshot.child("price").getValue().toString());
                            }
                        }).build();
                modelAdapter = new ShowFoodAdapter(options, SearchActivity.this);
                recyclerSearchView.setAdapter(modelAdapter);
                modelAdapter.startListening();
                //modelAdapter.stopListening();
            }
        });

         */
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}


/*
public class SearchActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerSearchView;
    EditText searchBox;
    Button searchButton;
    private GroupModelAdapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerSearchView = findViewById(R.id.recyclerSearchView);
        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerSearchView.setLayoutManager(linearLayoutManager);
        recyclerSearchView.setHasFixedSize(true);
        recyclerSearchView.setItemAnimator(null);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = searchBox.getText().toString();

                DatabaseReference gpRef = FirebaseDatabase.getInstance().getReference().child("Groups");
                Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text);
                //Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");

                FirebaseRecyclerOptions<GroupModel> options = new FirebaseRecyclerOptions.Builder<GroupModel>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<GroupModel>() {
                            @NonNull
                            public GroupModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new GroupModel(snapshot.child("groupName").getValue().toString(), snapshot.child("groupDescription").getValue().toString());
                            }
                        }).build();
                modelAdapter = new GroupModelAdapter(options, SearchActivity.this);
                recyclerSearchView.setAdapter(modelAdapter);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        finish();
        return true;
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        modelAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        modelAdapter.stopListening();
    }

}*/


/*

public class SearchActivity extends AppCompatActivity {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerSearchView;
    private EditText searchBox;
    private Button searchButton;
    private GroupModelAdapter modelAdapter;

    private FirebaseAuth yourAuth;
    private String mGroup;
    private String gpName, gpDesc, gpKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        InitializeFields();

        recyclerSearchView = findViewById(R.id.recyclerSearchView);
        searchBox = findViewById(R.id.searchBox);
        //searchBox.requestFocus();
        searchButton = findViewById(R.id.searchButton);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerSearchView.setLayoutManager(linearLayoutManager);
        recyclerSearchView.setHasFixedSize(true);
        recyclerSearchView.setItemAnimator(null);

        DatabaseReference gpRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RetrieveAndDisplaySavedGroups();
                String text = searchBox.getText().toString();

                Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");
                //Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");

                FirebaseRecyclerOptions<GroupModel> options = new FirebaseRecyclerOptions.Builder<GroupModel>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<GroupModel>() {
                            @NonNull
                            public GroupModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new GroupModel(snapshot.child("groupName").getValue().toString(), snapshot.child("groupDescription").getValue().toString(), snapshot.getKey().toString());
                            }
                        }).build();
                modelAdapter = new GroupModelAdapter(options, SearchActivity.this);
                recyclerSearchView.setAdapter(modelAdapter);
            }
                    });
                    }

            @Override
            public void onClick(View view) {
                String text = searchBox.getText().toString();

                Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");
                //Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(text).endAt(text + "\uf8ff");

                FirebaseRecyclerOptions<GroupModel> options = new FirebaseRecyclerOptions.Builder<GroupModel>()
                        .setQuery(firebaseSearchQuery, new SnapshotParser<GroupModel>() {
                            @NonNull
                            public GroupModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new GroupModel(snapshot.child("groupName").getValue().toString(), snapshot.child("groupDescription").getValue().toString(), snapshot.getKey().toString());
                            }
                        }).build();
                modelAdapter = new GroupModelAdapter(options, SearchActivity.this);
                recyclerSearchView.setAdapter(modelAdapter);
            }
        });


private void RetrieveAndDisplaySavedGroups() {

        String currentUserId = yourAuth.getCurrentUser().getUid();
        DatabaseReference savedRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Saved");

        Query query = FirebaseDatabase.getInstance().getReference().child("Groups");

        FirebaseRecyclerOptions<GroupModel> options = new FirebaseRecyclerOptions.Builder<GroupModel>()
        .setQuery(query, new SnapshotParser<GroupModel>() {
@NonNull
public GroupModel parseSnapshot(@NonNull DataSnapshot snapshot) {

        gpName = snapshot.child("groupName").getValue().toString();
        gpDesc = snapshot.child("groupDescription").getValue().toString();
        gpKey = snapshot.getKey().toString();

        return new GroupModel(gpName, gpDesc, gpKey);
        }
        }).build();
        modelAdapter = new GroupModelAdapter(options, this);
        recyclerSearchView.setAdapter(modelAdapter);
        //modelAdapter.startListening();
        }

 */

