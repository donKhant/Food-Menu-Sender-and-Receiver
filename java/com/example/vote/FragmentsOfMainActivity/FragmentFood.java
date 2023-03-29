package com.example.vote.FragmentsOfMainActivity;

/**
 * Created by Don, Date unknown
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.vote.Activities.FoodActivity;
import com.example.vote.R;

/**
 * Created by Don
 */

public class FragmentFood extends Fragment {

    private View foodFragmentView;
    private ImageView meat, breakfast, salad;

    public FragmentFood() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        foodFragmentView = inflater.inflate(R.layout.fragment_food, container, false);

        InitializeFields();

        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FoodActivity.class);
                i.putExtra("category", "Food");
                startActivity(i);
            }
        });

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FoodActivity.class);
                i.putExtra("category", "Drink");
                startActivity(i);
            }
        });

        salad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FoodActivity.class);
                i.putExtra("category", "Food");
                startActivity(i);
            }
        });

        return foodFragmentView;
    }

    private void InitializeFields() {
        meat = foodFragmentView.findViewById(R.id.meatCategory);
        breakfast = foodFragmentView.findViewById(R.id.breakfast);
        salad = foodFragmentView.findViewById(R.id.vegan);
    }

}

