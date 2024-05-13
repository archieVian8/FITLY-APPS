package com.example.projectfitlyp4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectfitlyp4.R;
import com.example.projectfitlyp4.database.ProfileHistory;
import com.example.projectfitlyp4.databinding.ActivityProfileEditBinding;
import com.example.projectfitlyp4.helper.DateHelper;
import com.example.projectfitlyp4.helper.ViewModelFactory;


public class ProfileEditActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "extra_note";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private boolean isEdit = false;

    private ProfileHistory profileHistory;
    private ProfileHistoryProfileEditViewModel profileHistoryProfileEditViewModel;
    private ActivityProfileEditBinding binding;
    EditText etName, etEmail;
    Spinner spinGender;
    Button btConfirm, btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btConfirm = findViewById(R.id.btConfirm);
        btCancel = findViewById(R.id.btCancel);
        spinGender = findViewById(R.id.spinGender);
        Spinner spinner = (Spinner) findViewById(R.id.spinGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");

        // Mengisi EditText dengan data yang ada
        etName.setText(name);
        etEmail.setText(email);

        // Memilih gender dalam spinner
        if (gender != null) {
            int spinnerPosition = adapter.getPosition(gender);
            spinGender.setSelection(spinnerPosition);
        }
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding =
                ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileHistoryProfileEditViewModel =
                obtainViewModel(ProfileEditActivity.this);
        profileHistory = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (profileHistory != null) {
            isEdit = true;
        } else {
            profileHistory = new ProfileHistory();
        }
        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);
            if (profileHistory != null) {
                binding.etName.setText(profileHistory.getFromName());
                binding.etEmail.setText(profileHistory.getToName());
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.btConfirm.setText(btnTitle);
        binding.btConfirm.setOnClickListener(view -> {
            String title =
                    binding.etName.getText().toString().trim();
            String description =
                    binding.etEmail.getText().toString().trim();
            if (title.isEmpty()) {
                binding.etName.setError(getString(R.string.empty));

            } else if (description.isEmpty()) {
                binding.etEmail.setError(getString(R.string.empty));
            } else {
                profileHistory.setFromName(title);
                profileHistory.setToName(description);
                Intent intent2 = new Intent();
                intent.putExtra(EXTRA_NOTE, profileHistory);
                if (isEdit) {
                    profileHistoryProfileEditViewModel.update(profileHistory);
                    showToast(getString(R.string.changed));
                } else {
                    profileHistory.setDate(DateHelper.getCurrentDate());
                    profileHistoryProfileEditViewModel.insert(profileHistory);
                    showToast(getString(R.string.added));

                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }
    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);

        } else {
            dialogMessage = getString(R.string.message_delete);
            dialogTitle = getString(R.string.delete);
        }
        AlertDialog.Builder alertDialogBuilder = new
                AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)

                .setPositiveButton(getString(R.string.yes),

                        (dialog, id) -> {
                            if (!isDialogClose) {
                                profileHistoryProfileEditViewModel.delete(profileHistory);
                                showToast(getString(R.string.deleted));
                            }

                            finish();

                        })
                .setNegativeButton(getString(R.string.no),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void showToast(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @NonNull
    private static ProfileHistoryProfileEditViewModel
    obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory =
                ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity,
                factory).get(ProfileHistoryProfileEditViewModel.class);
    }
}