package com.example.projectfitlyp4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.ProgressUser;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

public class ViewHistoryDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressUserAdapter adapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history_detail);

        recyclerView = findViewById(R.id.rvProgressUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appDatabase = AppDatabase.getInstance(this);

        new RetrieveDataTask(this, appDatabase).execute();

        ImageView imHome = findViewById(R.id.imHome);
        imHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ViewHistoryDetail", "ImageView imHome clicked");
                Intent intent = new Intent(ViewHistoryDetail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private static class RetrieveDataTask extends AsyncTask<Void, Void, List<ProgressUser>> {
        private WeakReference<ViewHistoryDetail> activityReference;
        private AppDatabase appDatabase;

        RetrieveDataTask(ViewHistoryDetail context, AppDatabase database) {
            activityReference = new WeakReference<>(context);
            appDatabase = database;
        }

        @Override
        protected List<ProgressUser> doInBackground(Void... voids) {
            return appDatabase.progressUserDao().getAll();
        }

        @Override
        protected void onPostExecute(List<ProgressUser> progressList) {
            ViewHistoryDetail activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            activity.adapter = new ProgressUserAdapter(progressList, activity);
            activity.adapter.setDecimalFormat(decimalFormat);
            activity.recyclerView.setAdapter(activity.adapter);
        }
    }
}
