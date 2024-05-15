package com.example.projectfitlyp4.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.FoodAdapter;
import com.example.projectfitlyp4.database.FoodApiService;
import com.example.projectfitlyp4.database.Food;
import com.example.projectfitlyp4.database.FoodAPI;
import com.example.projectfitlyp4.databinding.ActivityRecommendMakananBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendFoodActivity extends AppCompatActivity {

    private List<Food> Breaky = new ArrayList<>();
    private List<Food> Lunch = new ArrayList<>();
    private List<Food> Dinner = new ArrayList<>();
    private ActivityRecommendMakananBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecommendMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView.LayoutManager breakyLayoutManager = new LinearLayoutManager(RecommendFoodActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager lunchLayoutManager = new LinearLayoutManager(RecommendFoodActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager dinnerLayoutManager = new LinearLayoutManager(RecommendFoodActivity.this, LinearLayoutManager.HORIZONTAL, false);

        binding.rvBreaky.setLayoutManager(breakyLayoutManager);
        binding.rvLunch.setLayoutManager(lunchLayoutManager);
        binding.rvDinner.setLayoutManager(dinnerLayoutManager);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                FoodApiService api = FoodAPI.getClient().create(FoodApiService.class);
                Call<List<Food>> breaky = api.getBreakfast();
                breaky.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.isSuccessful()) {
                            Breaky = response.body();
                            // Update RecyclerView here
                            if (Breaky != null) {
                                FoodAdapter breakyAdapter = new FoodAdapter(RecommendFoodActivity.this, Breaky);
                                binding.rvBreaky.setAdapter(breakyAdapter);
                            } else {
                                Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> breaky, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                    }
                });

                Call<List<Food>> lunch = api.getLunch();
                lunch.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.isSuccessful()) {
                            Lunch = response.body();
                            if (Lunch != null) {
                                FoodAdapter lunchAdapter = new FoodAdapter(RecommendFoodActivity.this, Lunch);
                                binding.rvLunch.setAdapter(lunchAdapter);
                            }
                        } else {
                            Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                    }
                });

                Call<List<Food>> dinner = api.getDinner();
                dinner.enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.isSuccessful()) {
                            Dinner = response.body();
                            if (Dinner != null) {
                                FoodAdapter dinnerAdapter = new FoodAdapter(RecommendFoodActivity.this, Dinner);
                                binding.rvDinner.setAdapter(dinnerAdapter);
                            }
                        } else {
                            Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(RecommendFoodActivity.this, "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        Thread net = new Thread(runnable);
        net.start();
    }
}