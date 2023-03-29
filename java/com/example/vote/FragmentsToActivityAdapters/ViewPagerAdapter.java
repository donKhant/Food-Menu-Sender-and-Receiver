package com.example.vote.FragmentsToActivityAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vote.FragmentsOfMainActivity.FragmentDrink;
import com.example.vote.FragmentsOfMainActivity.FragmentTrending;
import com.example.vote.FragmentsOfMainActivity.FragmentFood;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final String[] titles = new String[] {"Trending", "Food", "Drink"};

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new FragmentTrending();
            case 1:
                return new FragmentFood();
            case 2:
                return new FragmentDrink();
        }
        return new FragmentTrending();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}