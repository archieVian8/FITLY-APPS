package com.example.projectfitlyp4;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseMain extends AppCompatActivity {

    private ImageView logoUser;
    private TextView text1, text2, text3, text4;
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_exercise);

        logoUser = findViewById(R.id.logo_user);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        recyclerView = findViewById(R.id.recyclerview);

        // Inisialisasi data latihan
        exercises = new ArrayList<>();
        exercises.add(new Exercise(R.drawable.img_renang, "Berenang", "Kalori 400-700 kkal/jam"));
        exercises.add(new Exercise(R.drawable.img_lari, "Lari", "Kalori 600-800 kkal/jam"));
        exercises.add(new Exercise(R.drawable.img_bersepeda, "Bersepeda", "Kalori 300-500 kkal/jam"));

        // Inisialisasi RecyclerView dan adapter
        exerciseAdapter = new ExerciseAdapter(exercises, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exerciseAdapter);
    }
}