package com.example.projectfitlyp4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfitlyp4.database.AppDatabase;
import com.example.projectfitlyp4.database.FirebaseProgressUserDao;
import com.example.projectfitlyp4.database.ProgressUser;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressUserAdapter adapter;
    private AppDatabase appDatabase;
    private FirebaseProgressUserDao firebaseProgressUserDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_history, container, false);

        recyclerView = view.findViewById(R.id.rvProgressUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        appDatabase = AppDatabase.getInstance(requireContext());
        firebaseProgressUserDao = new FirebaseProgressUserDao(requireContext());

        new RetrieveDataTask(this, appDatabase).execute();

        return view;
    }

    private static class RetrieveDataTask extends AsyncTask<Void, Void, List<ProgressUser>> {
        private WeakReference<HistoryFragment> fragmentReference;
        private AppDatabase appDatabase;
        private FirebaseProgressUserDao firebaseProgressUserDao;

        RetrieveDataTask(HistoryFragment fragment, AppDatabase database) {
            fragmentReference = new WeakReference<>(fragment);
            appDatabase = database;
            firebaseProgressUserDao = new FirebaseProgressUserDao(fragment.requireContext());
        }

        @Override
        protected List<ProgressUser> doInBackground(Void... voids) {
            return null; // Kembalikan null, karena data akan diambil dari Firebase secara asynchronous
        }

        @Override
        protected void onPostExecute(List<ProgressUser> progressList) {
            HistoryFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing())
                return;

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            // Mendapatkan data dari Firebase dan Room database
            fragment.firebaseProgressUserDao.getAllProgressUsersFromFirebase(new FirebaseProgressUserDao.FirebaseProgressUserCallback() {
                @Override
                public void onDataLoaded(List<ProgressUser> progressUsers) {
                    // Combine data from Room database and Firebase Realtime Database
                    List<ProgressUser> combinedData = new ArrayList<>();
                    combinedData.addAll(progressUsers); // Data dari Firebase
                    if (progressList != null) {
                        combinedData.addAll(progressList); // Data dari Room
                    }

                    // Set adapter ke RecyclerView
                    fragment.adapter = new ProgressUserAdapter(combinedData, fragment.requireActivity(), fragment.firebaseProgressUserDao);
                    fragment.adapter.setDecimalFormat(decimalFormat);
                    fragment.recyclerView.setAdapter(fragment.adapter);
                }

                @Override
                public void onError(String errorMessage) {
                }
            });
        }
    }
}

