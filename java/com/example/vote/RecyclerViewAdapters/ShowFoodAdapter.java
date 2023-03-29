package com.example.vote.RecyclerViewAdapters;

/**
 * Created by Don, Android Developer, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.squareup.picasso.Picasso;

public class ShowFoodAdapter extends FirebaseRecyclerAdapter<FoodModel, ShowFoodAdapter.ViewHolder> {

    Context context;
    private String hardCodedWaiterID;
    private DatabaseHelper db;

    public ShowFoodAdapter(@NonNull FirebaseRecyclerOptions<FoodModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FoodModel model) {

        Glide.with(context).load(model.getFoodUrl()).into(holder.foodView);
        holder.foodName.setText(model.getName());
        holder.ingredients.setText(model.getIngredients());
        holder.price.setText(model.getPrice());

        hardCodedWaiterID = "Waiter 1";

        // loading Animation from
        final Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                holder.save.startAnimation(animation);

                RemovableFoodModel rf = turnModelToRemovableFoodModel(model);

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_model, parent, false);
        return new ViewHolder(view);
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

        // setters
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

/*
   Typeface customFontGpName = Typeface.createFromAsset(context.getAssets(), "fonts/sriracha_regular.ttf");
            gpName.setTypeface(customFontGpName);

            Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/robotoslab_variable_font_wght.ttf");
            gpdescription.setTypeface(customFont);

 */
