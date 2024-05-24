package com.example.projectfitlyp4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_exercise);
        exercises = new ArrayList<>();
        exercises.add(new Exercise(R.drawable.img_renang, "Berenang", "Kalori 400-700 kkal/jam"));
        exercises.add(new Exercise(R.drawable.img_lari, "Lari", "Kalori 600-800 kkal/jam"));
        exercises.add(new Exercise(R.drawable.img_bersepeda, "Bersepeda", "Kalori 300-500 kkal/jam"));

        exerciseAdapter = new ExerciseAdapter(exercises, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(exerciseAdapter);

        return view;
    }
}