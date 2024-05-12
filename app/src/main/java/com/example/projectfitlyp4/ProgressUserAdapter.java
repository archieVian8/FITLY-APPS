package com.example.projectfitlyp4;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.ProgressUser;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

public class ProgressUserAdapter extends RecyclerView.Adapter<ProgressUserAdapter.ProgressUserViewHolder> {
    private List<ProgressUser> progressUserList;
    private Context context;
    private AppDatabase appDatabase;
    private DecimalFormat decimalFormat;

    public ProgressUserAdapter(List<ProgressUser> progressUserList, Context context) {
        this.progressUserList = progressUserList;
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    public void setDecimalFormat(DecimalFormat format) {
        this.decimalFormat = format;
    }

    @NonNull
    @Override
    public ProgressUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_list, parent, false);
        return new ProgressUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressUserViewHolder holder, int position) {
        ProgressUser progressUser = progressUserList.get(position);
        holder.bind(progressUser);
    }

    @Override
    public int getItemCount() {
        return progressUserList.size();
    }

    public void setProgressUserList(List<ProgressUser> progressUserList) {
        this.progressUserList = progressUserList;
        notifyDataSetChanged();
    }

    public class ProgressUserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvWeight, tvHeight, tvBMI;
        private ImageButton btnDelete;

        public ProgressUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvRecyclerview_date);
            tvWeight = itemView.findViewById(R.id.tvReyclerView_Weight);
            tvHeight = itemView.findViewById(R.id.tvRecyclerView_Height);
            tvBMI = itemView.findViewById(R.id.tvRecyclerView_BMI);
            btnDelete = itemView.findViewById(R.id.btn_delete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ProgressUser progressUserToDelete = progressUserList.get(position);
                        progressUserList.remove(position);
                        notifyItemRemoved(position);
                        new DeleteDataTask(context, appDatabase, progressUserToDelete).execute();
                    }
                }
            });
        }

        public void bind(ProgressUser progressUser) {
            tvDate.setText("Date: " + progressUser.getDate());
            tvWeight.setText("Weight: " + progressUser.getWeight() + " kg");
            tvHeight.setText("Height: " + progressUser.getHeight() + " m");
            if (decimalFormat != null) {
                String formattedBMI = decimalFormat.format(progressUser.getBmi());
                tvBMI.setText("BMI: " + formattedBMI);
            } else {
                tvBMI.setText("BMI: " + progressUser.getBmi());
            }
        }
    }

    private static class DeleteDataTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<Context> contextReference;
        private AppDatabase appDatabase;
        private ProgressUser progressUserToDelete;

        DeleteDataTask(Context context, AppDatabase database, ProgressUser progressUser) {
            contextReference = new WeakReference<>(context);
            appDatabase = database;
            progressUserToDelete = progressUser;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.progressUserDao().delete(progressUserToDelete);
            return null;
        }
    }
}
