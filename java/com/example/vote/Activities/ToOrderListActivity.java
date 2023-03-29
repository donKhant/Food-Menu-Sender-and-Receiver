package com.example.vote.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vote.FragmentsToActivityAdapters.toOrderPagerAdapter;
import com.example.vote.R;

public class ToOrderListActivity extends AppCompatActivity {

    toOrderPagerAdapter PagerAdapter;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        viewPager = findViewById(R.id.pager);
        PagerAdapter = new toOrderPagerAdapter(this);

        viewPager.setAdapter(PagerAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Inventory");
        toolbar.setNavigationIcon(R.drawable.arrow_back_24);
        toolbar.setNavigationOnClickListener(view -> finish());

    }

}


/*
Typeface customFontTitle = Typeface.createFromAsset(this.getAssets(), "fonts/robotoslab_variable_font_wght.ttf");
        Typeface customFont = Typeface.createFromAsset(this.getAssets(), "fonts/poor_story_regular.ttf");

        loc.setTypeface(customFontTitle);
        contactUs.setTypeface(customFontTitle);

 */