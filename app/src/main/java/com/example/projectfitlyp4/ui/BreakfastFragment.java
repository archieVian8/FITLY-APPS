package com.example.projectfitlyp4.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.FoodAdapter;
import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.database.Food;
import com.example.projectfitlyp4.database.FoodAPI;
import com.example.projectfitlyp4.database.FoodApiService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    FoodAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        recyclerView = view.findViewById(R.id.rvBreakfast);
        FirebaseApp.initializeApp(requireContext());

        firebaseDatabase = FirebaseDatabase.getInstance("https://fitly-apps-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(getActivity(), breakfastList);
        recyclerView.setAdapter(adapter);

        fetchData();

        // Load breakfast data
//        FoodApiService api = FoodAPI.getClient().create(FoodApiService.class);
//        Call<List<Food>> breaky = api.getBreakfast();
//        breaky.enqueue(new Callback<List<Food>>() {
//            @Override
//            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
//                if (response.isSuccessful()) {
//                    breakfastList = response.body();
//                    // Update RecyclerView here
//                    if (breakfastList != null) {
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Food>> breaky, Throwable t) {
//                t.printStackTrace();
//                Toast.makeText(getActivity(), "Failed to fetch food data, failure", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }

    private void fetchData() {
        databaseReference.child("Food").child("Breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                breakfastList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food breaky = dataSnapshot.getValue(Food.class);
                    breakfastList.add(breaky);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to load breakfast dataset", Toast.LENGTH_SHORT).show();
            }
        });
    }
}