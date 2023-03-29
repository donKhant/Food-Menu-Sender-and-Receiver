package com.example.vote.LocalDB;

/**
 * Created by Don, Android Developer, Date unknown
 * free to use for educational purposes
 *
 * contact me at donkhant1@gmail.com
 */

// This adapter is used with RemovableFoodModel for displaying data from local database.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vote.R;
import com.example.vote.Models.RemovableFoodModel;

import java.util.List;

public class LocalDBAdapter extends RecyclerView.Adapter<LocalDBAdapter.MyViewHolder> {

    private Context context;
    private List<RemovableFoodModel> foodList;
    private DatabaseHelper db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView foodView, remove;
        public TextView foodName, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodView = itemView.findViewById(R.id.foodView);
            foodName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            remove = itemView.findViewById(R.id.removeThis);
        }

        // setters
        public void setFoodName(String name) {
            foodName.setText(name);
        }

        public void setPrice(String p) {
            price.setText(p);
        }

    }

    public LocalDBAdapter(Context context, List<RemovableFoodModel> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.removable_food_model, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RemovableFoodModel model = foodList.get(position);

        //Toast.makeText(context, model.getName() + ", id = " + model.getId(), Toast.LENGTH_SHORT).show();
        Glide.with(context).load(model.getFoodUrl()).into(holder.foodView);
        holder.foodName.setText(model.getName());
        holder.price.setText(model.getPrice());

        // loading Animation from
        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.bounce);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = new DatabaseHelper(context);
                db.deleteFood(model);
                foodList.remove(model);

                LocalDBAdapter.this.notifyItemRemoved(holder.getBindingAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

}
