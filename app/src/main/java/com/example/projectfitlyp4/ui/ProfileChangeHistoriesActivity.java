package com.example.projectfitlyp4.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.databinding.ActivityProfileChangeHistoriesBinding;
import com.example.projectfitlyp4.helper.ViewModelFactory;

//public class ProfileChangeHistoriesActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile_change_histories);
//    }
//}

public class ProfileChangeHistoriesActivity extends AppCompatActivity {
    private ActivityProfileChangeHistoriesBinding binding;
    private ProfileHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                ActivityProfileChangeHistoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ProfileHistoryProfileChangeHistoriesViewModel profileHistoryProfileChangeHistoriesViewModel =
                obtainViewModel(ProfileChangeHistoriesActivity.this);
        profileHistoryProfileChangeHistoriesViewModel.getAllProfileHistories().observe(this, notes -> {
            if (notes != null) {
                adapter.setListNotes(notes);
            }
        });
        adapter = new ProfileHistoryAdapter();
        binding.rvNotes.setLayoutManager(new
                LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);
        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(ProfileChangeHistoriesActivity.this,
                        ProfileEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @NonNull
    private static ProfileHistoryProfileChangeHistoriesViewModel
    obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity,
                factory).get(ProfileHistoryProfileChangeHistoriesViewModel.class);
    }
}