package com.example.vote.FragmentsToActivityAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.vote.FragmentsOfToOrderListActivity.FragmentToOrder;

public class toOrderPagerAdapter extends FragmentStateAdapter {

    private final String[] titles = new String[] {"To-order List"};

    public toOrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new FragmentToOrder();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
