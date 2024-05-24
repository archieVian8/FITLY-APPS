package com.example.projectfitlyp4.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.FoodAdapter;
import com.example.projectfitlyp4.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_makanan);

        loadFragments();
    }

    private void loadFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the container with the respective fragments
        fragmentTransaction.replace(R.id.container_breakfast, new BreakfastFragment());
        fragmentTransaction.replace(R.id.container_lunch, new LunchFragment());
        fragmentTransaction.replace(R.id.container_dinner, new DinnerFragment());

        fragmentTransaction.commit();
    }

}