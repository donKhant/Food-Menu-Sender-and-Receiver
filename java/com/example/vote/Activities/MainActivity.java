package com.example.vote.Activities;

/**
 * Created by Don, Android Developer, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 *
 * Fourth Project created in my wonderful solo app dev journey
 * - This is a fairly complex project.
 * - Everything I learned is put together : Firebase, Sqlite, Animated Buttons, card view design and more...
 *
 *
 * Project Overview
 *
 * A pseudo food delivery app that implements Sqlite and Firebase in one app.
 *
 * User Guide :
 * The app display a nice looking food menu. Add any item you like to the inventory by clicking on animated buttons.
 * From inventory, you can confirm to send the orders to another application.
 *
 * Developer Guide
 * The app fetchs and display data from Firebase Realtime Database.
 * It uses Sqite to temporarily store the data.
 * When the user confirms that the orders will be sent,
 * the app will retrieve and transfer data from local database to Firebase db.
 *
 * references - @todo
 * documentation - @todo
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.vote.R;
import com.example.vote.FragmentsToActivityAdapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
    TabLayout tablayout;
    ViewPager2 viewPager2;
    Toolbar toolbarMain;

    public TextView textCartItemCount;
    public int mCartItemCount = 0;

    private final String[] titles = new String[] {"Trending", "Food", "Drink"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablayout = findViewById(R.id.main_tabs);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tablayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();

        toolbarMain = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("Food Ordering App");

        ActionBar actionBar = getSupportActionBar();

        /*Bundle tableIdExtra = getIntent().getExtras();
        String tableId = tableIdExtra.getString("tableId");

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child(tableId);

        //FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child(tableId);
        Map<String, Object> newGuest = new HashMap<>();
        newGuest.put("currentUserId", uid);
        tableRef.child("guests").updateChildren(newGuest);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.about);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent i = new Intent(MainActivity.this, ToOrderListActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /*
        if (back_pressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
         */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).removeValue();
        FirebaseAuth.getInstance().getCurrentUser().delete();
        Toast.makeText(MainActivity.this, "Quit", Toast.LENGTH_SHORT).show();
        //FirebaseAuth.getInstance().signOut();

         */
    }

}


    /*private void FirebaseSearch(String searchText) {
        String query = searchText.toLowerCase();

        recyclerExploreGroupView = findViewById(R.id.recyclerExploreGroupView);

        DatabaseReference gpRef = FirebaseDatabase.getInstance().getReference().child("Groups");
        Query firebaseSearchQuery = gpRef.orderByChild("groupName").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<GroupModel> options = new FirebaseRecyclerOptions.Builder<GroupModel>()
                .setQuery(firebaseSearchQuery, new SnapshotParser<GroupModel>() {
                    @NonNull
                    public GroupModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new GroupModel(snapshot.child("groupName").getValue().toString(), snapshot.child("groupDescription").getValue().toString());
                    }
                }).build();
        modelAdapter = new GroupModelAdapter(options, MainActivity.this);
        recyclerExploreGroupView.setAdapter(modelAdapter);
    }*/

// cause you don't think, I know, what you've done...an....