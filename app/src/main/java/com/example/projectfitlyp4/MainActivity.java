package com.example.projectfitlyp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.ProgressUser;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etDate, etWeight, etHeight;
    TextView tvWeight, tvHeight, tvBMI, tvClassification, tvDateView;
    AppDatabase appDatabase;
    BarChart barChart;
    ArrayList<String> datesList = new ArrayList<>();
    int counter = 1;
    boolean dataEnteredToday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDate = findViewById(R.id.et_date);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);

        tvWeight = findViewById(R.id.tvWeight);
        tvHeight = findViewById(R.id.tvHeight);
        tvBMI = findViewById(R.id.tvBMI);
        tvClassification = findViewById(R.id.tvClassification);
        tvDateView = findViewById(R.id.tvDateView);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "progress_database").build();

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        findViewById(R.id.btConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
            }
        });

        findViewById(R.id.btViewAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewHistoryDetail.class);
                startActivity(intent);
            }
        });

        // Configure chart
        barChart = findViewById(R.id.bar_chart);
        setupChart();

        // Buat dan jalankan AsyncTask untuk mengambil data dari database
        new LoadDataFromDatabaseTask().execute();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                etDate.setText(date);
                datesList.add(date);
                dataEnteredToday = false;
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void calculateBMI() {
        String weightText = etWeight.getText().toString();
        String heightText = etHeight.getText().toString();
        String date = etDate.getText().toString();

        if (!weightText.isEmpty() && !heightText.isEmpty()) {
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText) / 100; // Convert cm to m

            double bmi = weight / (height * height);
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedBMI = decimalFormat.format(bmi);

            tvWeight.setText("Weight: " + weightText + " kg");
            tvHeight.setText("Height: " + heightText + " cm");
            tvBMI.setText("BMI: " + formattedBMI);

            if (bmi < 18.5) {
                tvClassification.setText("Underweight");
            } else if (bmi >= 18.5 && bmi <= 22.9) {
                tvClassification.setText("Normal");
            } else {
                tvClassification.setText("Overweight");
            }

            // Insert data into database
            insertDataIntoDatabase(weight, height, bmi, date);
            addChartEntry(bmi);
            tvDateView.setText("Date: " + date);
            dataEnteredToday = true;
        } else {
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
            tvWeight.setText("Weight: ");
            tvHeight.setText("Height: ");
            tvBMI.setText("BMI: ");
            tvClassification.setText("-");
        }
    }

    private void insertDataIntoDatabase(double weight, double height, double bmi, String date) {
        ProgressUser progressUser = new ProgressUser(date, weight, height, bmi);
        new InsertDataTask().execute(progressUser);
    }

    private void resetInputs() {
        etWeight.setText("");
        etHeight.setText("");
        dataEnteredToday = false;
        Toast.makeText(MainActivity.this, "Data deleted, please enter new data", Toast.LENGTH_SHORT).show();
    }

    private class InsertDataTask extends AsyncTask<ProgressUser, Void, Void> {
        @Override
        protected Void doInBackground(ProgressUser... progressUsers) {
            appDatabase.progressUserDao().insert(progressUsers[0]);
            return null;
        }
    }

    private void setupChart() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
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

    private void addChartEntry(double bmi) {
        BarData barData = barChart.getData();
        if (barData != null) {
            BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(0);
            if (barDataSet != null) {
                int index = counter;
                barDataSet.addEntry(new BarEntry(index, (float) bmi));
                barData.notifyDataChanged();
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                counter++;

                // Update X-axis labels
                ArrayList<String> xValues = new ArrayList<>();
                for (int i = 0; i < datesList.size(); i++) {
                    xValues.add(String.valueOf(i));
                }
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
            }
        }
    }

    private class LoadDataFromDatabaseTask extends AsyncTask<Void, Void, List<ProgressUser>> {
        @Override
        protected List<ProgressUser> doInBackground(Void... voids) {
            // Lakukan query ke database untuk mengambil semua entri ProgressUser
            return appDatabase.progressUserDao().getAll();
        }

        @Override
        protected void onPostExecute(List<ProgressUser> progressUsers) {
            super.onPostExecute(progressUsers);

            // Tambahkan setiap entri dari database ke dalam grafik
            for (ProgressUser progressUser : progressUsers) {
                addChartEntry(progressUser.getBmi());
            }
        }
    }
}