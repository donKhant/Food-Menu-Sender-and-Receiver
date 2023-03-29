package com.example.vote.FragmentsOfMainActivity;

/**
 * Created by Don
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vote.Models.FoodModel;
import com.example.vote.R;
import com.example.vote.RecyclerViewAdapters.ShowFoodAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentDrink extends Fragment {

    private View DrinkFragmentView;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerDrinkView;

    private ShowFoodAdapter adapter;

    public FragmentDrink() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DrinkFragmentView = inflater.inflate(R.layout.fragment_drink, container, false);

        InitializeFields();

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerDrinkView.setLayoutManager(linearLayoutManager);
        recyclerDrinkView.setHasFixedSize(true);
        recyclerDrinkView.setItemAnimator(null);

        RetrieveAndDisplayDrink();

        return DrinkFragmentView;
    }

    private void InitializeFields() {
        recyclerDrinkView = DrinkFragmentView.findViewById(R.id.recyclerDrink);
    }

    private void RetrieveAndDisplayDrink() {

        FirebaseRecyclerOptions<FoodModel> options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Servers").child("Server1").child("Drink"), snapshot -> {

                    //Toast.makeText(getContext(), "name" + snapshot.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    return new FoodModel(snapshot.child("view").getValue().toString(), snapshot.child("name").getValue().toString(), snapshot.child("ingredients").getValue().toString(), snapshot.child("price").getValue().toString(), snapshot.getKey());

                }).build();

        adapter = new ShowFoodAdapter(options, getContext());
        recyclerDrinkView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
