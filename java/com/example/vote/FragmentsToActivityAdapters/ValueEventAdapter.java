package com.example.vote.FragmentsToActivityAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vote.LocalDB.DatabaseHelper;
import com.example.vote.Models.FoodModel;
import com.example.vote.Models.RemovableFoodModel;
import com.example.vote.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueEventAdapter extends RecyclerView.Adapter<ValueEventAdapter.ViewHolder> {

    private ArrayList<FoodModel> foodModels;
    private Context context;
    private String hardCodedWaiterID;
    private DatabaseHelper db;

    public ValueEventAdapter(ArrayList<FoodModel> foodModels, Context context) {
        this.foodModels = foodModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = holder.getBindingAdapterPosition();

        Glide.with(context).load(foodModels.get(i).getFoodUrl()).into(holder.foodView);
        holder.foodName.setText(foodModels.get(i).getName());
        holder.ingredients.setText(foodModels.get(i).getIngredients());
        holder.price.setText(foodModels.get(i).getPrice());

        hardCodedWaiterID = "Waiter 1";

        // loading Animation from
        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                holder.save.startAnimation(animation);

                Map<String, Object> newOrder = new HashMap<>();
                newOrder.put("view", foodModels.get(i).getFoodUrl());
                newOrder.put("name", foodModels.get(i).getName());
                newOrder.put("ingredients", foodModels.get(i).getIngredients());
                newOrder.put("price", foodModels.get(i).getPrice());

                RemovableFoodModel rf = turnModelToRemovableFoodModel(foodModels.get(i));

                db = new DatabaseHelper(context);
                long id = db.insertFood(rf);

            }
        });

        holder.foodName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foodName.startAnimation(animation);
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.price.startAnimation(animation);
            }
        });

    }

    private RemovableFoodModel turnModelToRemovableFoodModel(FoodModel model) {
        RemovableFoodModel rf = new RemovableFoodModel();
        rf.setFoodUrl(model.getFoodUrl());
        rf.setName(model.getName());
        rf.setIngredients(model.getIngredients());
        rf.setPrice(model.getPrice());
        rf.setFirebaseId(model.getFirebaseId());
        return rf;
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView foodView, save;
        public TextView foodName, ingredients, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodView = itemView.findViewById(R.id.foodView);
            foodName = itemView.findViewById(R.id.name);
            ingredients = itemView.findViewById(R.id.ingredients);
            price = itemView.findViewById(R.id.price);
            save = itemView.findViewById(R.id.save);
        }

        public void setFoodName(String name) {
            foodName.setText(name);
        }

        public void setIngredients(String ingre) {
            ingredients.setText(ingre);
        }

        public void setPrice(String p) {
            price.setText(p);
        }
    }
}
