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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exercises;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inisialisasi Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("exercises");

        // Contoh data untuk ditambahkan ke Firebase
        saveExerciseToFirebase(new Exercise(R.drawable.img_renang, "Berenang", "Kalori 400-700 kkal/jam"));
        saveExerciseToFirebase(new Exercise(R.drawable.img_lari, "Lari", "Kalori 600-800 kkal/jam"));
        saveExerciseToFirebase(new Exercise(R.drawable.img_bersepeda, "Bersepeda", "Kalori 300-500 kkal/jam"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_exercise);
        exercises = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(exercises, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(exerciseAdapter);

        // Membaca data dari Firebase Realtime Database
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    exercises.clear(); // Menghapus data latihan sebelumnya
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Exercise exercise = childSnapshot.getValue(Exercise.class);
                        exercises.add(exercise);
                    }
                    exerciseAdapter.notifyDataSetChanged();
                } else {
                    // Tangani error
                }
            }
        });

        return view;
    }

    private void saveExerciseToFirebase(Exercise exercise) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(exercise)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Data berhasil disimpan
                        } else {
                            // Tangani error
                        }
                    }
                });
    }
}
