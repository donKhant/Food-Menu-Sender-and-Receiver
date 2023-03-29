package com.example.vote.FragmentsOfMainActivity;

/**
 * Created by Don, Date unknown
 */

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vote.Models.FoodModel;
import com.example.vote.R;
import com.example.vote.RecyclerViewAdapters.ShowFoodAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentTrending extends Fragment {

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerTrendingView;

    private ShowFoodAdapter modelAdapter;

    private View fragmentTrendingView;

    public FragmentTrending() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentTrendingView = inflater.inflate(R.layout.fragment_explore_groups, container, false);

        InitializeFields();

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTrendingView.setLayoutManager(linearLayoutManager);
        recyclerTrendingView.setHasFixedSize(true);
        recyclerTrendingView.setItemAnimator(null);

        RetrieveAndDisplayFood();

        return fragmentTrendingView;
    }


    private void InitializeFields() {
        recyclerTrendingView = fragmentTrendingView.findViewById(R.id.recyclerExploreGroupView);
    }

    private void RetrieveAndDisplayFood() {

        FirebaseRecyclerOptions<FoodModel> options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child("Food"), snapshot -> {

                    //Toast.makeText(getContext(), "name" + snapshot.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    return new FoodModel(snapshot.child("view").getValue().toString(), snapshot.child("name").getValue().toString(), snapshot.child("ingredients").getValue().toString(), snapshot.child("price").getValue().toString(), snapshot.getKey());

                }).build();

        modelAdapter = new ShowFoodAdapter(options, getContext());
        recyclerTrendingView.setAdapter(modelAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        modelAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        modelAdapter.stopListening();
    }

}



