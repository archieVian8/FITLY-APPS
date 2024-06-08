package com.example.projectfitlyp4.ui;

import android.os.Bundle;
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
public class DinnerFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Food> dinnerList = new ArrayList<>();
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
        FoodAdapter adapter = new FoodAdapter(getActivity(), dinnerList);
        recyclerView.setAdapter(adapter);

        databaseReference.child("Food").child("Dinner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dinnerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food dinner = dataSnapshot.getValue(Food.class);
                    dinnerList.add(dinner);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to load dinner dataset", Toast.LENGTH_SHORT).show();
            }
        });

        // Load breakfast data
//        FoodApiService api = FoodAPI.getClient().create(FoodApiService.class);
//        Call<List<Food>> dinner = api.getDinner();
//        dinner.enqueue(new Callback<List<Food>>() {
//            @Override
//            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
//                if (response.isSuccessful()) {
//                    dinnerList = response.body();
//                    if (dinnerList != null) {
//                        FoodAdapter dinnerAdapter = new FoodAdapter(getActivity(), dinnerList);
//                        recyclerView.setAdapter(dinnerAdapter);
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Food>> call, Throwable t) {
//                t.printStackTrace();
//                Toast.makeText(getActivity(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
//            }
//        });

        return view;
    }
}