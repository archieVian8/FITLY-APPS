package com.example.projectfitlyp4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.databinding.ActivityRecordMakananBinding;

public class RecordMakananActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRecordMakananBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ibCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ibCancel){
            Intent intent = new Intent(this, RecommendFoodActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = this.getIntent();
        if (!intent.getStringExtra("bfoto").equals("")) { // If there's a valid resource ID
            Glide.with(this)
                    .load(intent.getStringExtra("bfoto"))
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true))
                    .into(binding.ivBackground);
            binding.tvRecordMenu.setText(intent.getStringExtra("bmenu"));
            binding.tvDescription.setText(intent.getStringExtra("bdesc"));
            binding.tvResep1.setText(intent.getStringExtra("bingredient"));
            binding.tvResep2.setText(intent.getStringExtra("tvResep2"));
            binding.tvResep3.setText(intent.getStringExtra("tvResep3"));
            binding.tvResep4.setText(intent.getStringExtra("tvResep4"));
        } else {
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
        }

    }
}