package com.example.projectfitlyp4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.ProgressUser;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressUserAdapter adapter;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_history, container, false);

        recyclerView = view.findViewById(R.id.rvProgressUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        appDatabase = AppDatabase.getInstance(requireContext());

        new RetrieveDataTask(this, appDatabase).execute();

        return view;
    }

    private static class RetrieveDataTask extends AsyncTask<Void, Void, List<ProgressUser>> {
        private WeakReference<HistoryFragment> fragmentReference;
        private AppDatabase appDatabase;

        RetrieveDataTask(HistoryFragment fragment, AppDatabase database) {
            fragmentReference = new WeakReference<>(fragment);
            appDatabase = database;
        }

        @Override
        protected List<ProgressUser> doInBackground(Void... voids) {
            return appDatabase.progressUserDao().getAll();
        }

        @Override
        protected void onPostExecute(List<ProgressUser> progressList) {
            HistoryFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing())
                return;

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            fragment.adapter = new ProgressUserAdapter(progressList, fragment.requireActivity());
            fragment.adapter.setDecimalFormat(decimalFormat);
            fragment.recyclerView.setAdapter(fragment.adapter);
        }
    }
}
