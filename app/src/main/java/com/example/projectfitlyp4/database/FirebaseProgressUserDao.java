package com.example.projectfitlyp4.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FirebaseProgressUserDao {
    private DatabaseReference databaseReference;

    public FirebaseProgressUserDao(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference("progress_user");
    }

    public void insertProgressUserToFirebase(final ProgressUser progressUser) {
        final String progressUserId = databaseReference.push().getKey();
        progressUser.setFirebaseId(progressUserId);
        databaseReference.child(progressUserId).setValue(progressUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Data insert from Firebase successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Error insert data from Firebase: " + e.getMessage());
                    }
                });
    }

    public void deleteProgressUserFromFirebase(ProgressUser progressUser) {
        String progressUserId = progressUser.getFirebaseId();
        Log.d("Firebase", "Deleting entry with ID: " + progressUserId);
        databaseReference.child(progressUserId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Data deleted from Firebase successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Error deleting data from Firebase: " + e.getMessage());
                    }
                });
    }

    public void getAllProgressUsersFromFirebase(final FirebaseProgressUserCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ProgressUser> progressUsers = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProgressUser progressUser = snapshot.getValue(ProgressUser.class);
                    progressUsers.add(progressUser);
                }
                callback.onDataLoaded(progressUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface FirebaseProgressUserCallback {
        void onDataLoaded(List<ProgressUser> progressUsers);
        void onError(String errorMessage);
    }
}
