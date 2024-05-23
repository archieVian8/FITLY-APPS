package com.example.projectfitlyp4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ExerciseMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_execise);

        // Mendapatkan instance FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Memulai FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Menambahkan Fragment ke dalam Activity
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        fragmentTransaction.add(R.id.fragment_exercise, exerciseFragment);

        // Menyelesaikan FragmentTransaction
        fragmentTransaction.commit();
    }
}