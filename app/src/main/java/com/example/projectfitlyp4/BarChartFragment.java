package com.example.projectfitlyp4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.ProgressUser;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartFragment extends Fragment {
    private BarChart barChart;
    private AppDatabase appDatabase;
    private ArrayList<String> datesList = new ArrayList<>();
    private int counter = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barchart, container, false);

        barChart = view.findViewById(R.id.bar_chart);

        appDatabase = AppDatabase.getInstance(requireContext());

        new LoadDataFromDatabaseTask().execute();
        return view;
    }

    private void setupChart(List<ProgressUser> progressUsers) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        datesList.clear();
        for (ProgressUser progressUser : progressUsers) {
            barEntries.add(new BarEntry(counter, (float) progressUser.getBmi()));
            datesList.add(progressUser.getDate());
            counter++;
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "BMI");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.animateY(5000);
        barChart.getDescription().setText("BMI LOG");

        // Set fixed size for X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(11f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(datesList));
    }

    private class LoadDataFromDatabaseTask extends AsyncTask<Void, Void, List<ProgressUser>> {
        @Override
        protected List<ProgressUser> doInBackground(Void... voids) {
            return appDatabase.progressUserDao().getAll();
        }

        @Override
        protected void onPostExecute(List<ProgressUser> progressUsers) {
            super.onPostExecute(progressUsers);

            setupChart(progressUsers);
        }
    }
}
