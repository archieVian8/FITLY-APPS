package com.example.projectfitlyp4.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.FoodAdapter;
import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.database.Food;
import com.example.projectfitlyp4.database.FoodAPI;
import com.example.projectfitlyp4.database.FoodApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class BreakfastFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Food> breakfastList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        recyclerView = view.findViewById(R.id.rvBreakfast);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        FoodAdapter adapter = new FoodAdapter(getActivity(), breakfastList);
        recyclerView.setAdapter(adapter);

        // Load breakfast data
        FoodApiService api = FoodAPI.getClient().create(FoodApiService.class);
        Call<List<Food>> breaky = api.getBreakfast();
        breaky.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    breakfastList = response.body();
                    // Update RecyclerView here
                    if (breakfastList != null) {
                        FoodAdapter breakyAdapter = new FoodAdapter(getActivity(), breakfastList);
                        recyclerView.setAdapter(breakyAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Food>> breaky, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Failed to fetch food data, failure", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}