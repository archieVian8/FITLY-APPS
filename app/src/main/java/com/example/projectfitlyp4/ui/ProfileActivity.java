package com.example.projectfitlyp4.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
//import com.example.fitlyapp.ui.editprofile.InsertUpdateActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvBmi, tvGender, tvHeight, tvWeight;
    Button btEditProfile, btGoToChangeHistories, btnLogout;

    private NavController navController;
    private ImageView profileImageView;
    private TextView bmiTextView;
    private TextView genderTextView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView emailTextView;
    private TextView nameTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvBmi = findViewById(R.id.tvBmi);
        tvGender = findViewById(R.id.tvGender);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        btEditProfile = findViewById(R.id.btEditProfile);
        btGoToChangeHistories = findViewById(R.id.btGoToChangeHistories);
            btnLogout = findViewById(R.id.btnLogout);
            mAuth = FirebaseAuth.getInstance();

        tvName.setText("Ditoo");
        tvEmail.setText("dito@gmail.com");
        tvBmi.setText("18.6");
        tvGender.setText("female");
        tvHeight.setText("178cm");
        tvWeight.setText("65kg");
        btEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);

                intent.putExtra("name", tvName.getText().toString());
                intent.putExtra("email", tvEmail.getText().toString());
                intent.putExtra("bmi", tvBmi.getText().toString());
                intent.putExtra("gender", tvGender.getText().toString());
                intent.putExtra("height", tvHeight.getText().toString());
                intent.putExtra("weight", tvWeight.getText().toString());

                startActivity(intent);
            }
        });
        btGoToChangeHistories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileChangeHistoriesActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });


//        loadUserProfileImage();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assume User class has the same fields as the database
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        tvName.setText(user.getName());
                        tvEmail.setText(user.getEmail());
                        tvBmi.setText(user.getBmi());
                        tvGender.setText(user.getGender());
                        tvHeight.setText(user.getHeight());
                        tvWeight.setText(user.getWeight());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loadUserProfileImage() {
        String userProfileUrl = "http://192.168.1.7/server-fitly/profile/?id=1";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,userProfileUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String imageUrl = response.getString("imageUrl");
                            String name = response.getString("name");
                            String email = response.getString("email");
                            double bmi = response.getDouble("bmi");
                            String gender = response.getString("gender");
                            int height = response.getInt("height");
                            int weight = response.getInt("weight");

                            ImageRequest imageRequest = new ImageRequest(imageUrl,
                                    new Response.Listener<Bitmap>() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            // Gambar berhasil diambil, tampilkan di ImageView
                                            profileImageView.setImageBitmap(bitmap);
                                        }
                                    }, 0, 0, null,
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Kesalahan dalam pengambilan gambar, lakukan penanganan kesalahan di sini
                                            error.printStackTrace();
                                        }
                                    });

                            Volley.newRequestQueue(getApplicationContext()).add(imageRequest);

                            bmiTextView.setText(String.valueOf(bmi));
                            genderTextView.setText(gender);
                            heightTextView.setText(String.valueOf(height));
                            weightTextView.setText(String.valueOf(weight));
                            emailTextView.setText(email);
                            nameTextView.setText(name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }


}