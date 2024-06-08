package com.example.projectfitlyp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> exercises;
    private Context context;

    public ExerciseAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_exercises, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.ivExerciseImage.setImageResource(exercise.getImageResId());
        holder.tvExerciseName.setText(exercise.getName());
        holder.tvCalorieCount.setText(exercise.getCalorieCount());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExerciseImage;
        TextView tvExerciseName, tvCalorieCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExerciseImage = itemView.findViewById(R.id.iv_gambarlatihan);
            tvExerciseName = itemView.findViewById(R.id.tv_namalatihan);
            tvCalorieCount = itemView.findViewById(R.id.tv_jumlahkalori);
        }
    }
}