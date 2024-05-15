package com.example.projectfitlyp4;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.projectfitlyp4.database.Food;
import com.example.projectfitlyp4.databinding.RowMakananBinding;
import com.example.projectfitlyp4.ui.RecordFoodActivity;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private final Context context;
    private final List<Food> food;

    public FoodAdapter(Context context, List<Food> food){
        this.context = context;
        this.food = food;
        Log.d("MakananList", "Makanan objects: " + food.toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RowMakananBinding binding;

        ViewHolder(RowMakananBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Food food) {
            binding.tvBreaky.setText(food.getNama());
            binding.tvCalBreaky.setText(food.getKalori() + " kkal");
            Glide.with(context)
                    .load(Base64.decode(food.getFoto(), Base64.DEFAULT))
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(binding.ivBreaky);
            binding.clBreaky.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), RecordFoodActivity.class);
                intent.putExtra("bmenu", food.getNama());
                intent.putExtra("bdesc", food.getDescription());
                intent.putExtra("bingredient", food.getIngredients());
                intent.putExtra("bfoto", food.getFoto());
                v.getContext().startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowMakananBinding binding = RowMakananBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(food.get(position));
    }

    @Override
    public int getItemCount() {
        return this.food.size();
    }
}
