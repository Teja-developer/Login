package com.example.kvrte.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    static final String CHAT_PREFS = "ChatPrefs";
    static final String DISPLAY_NAME_KEY = "username";

    EditText name, email, password, cpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        cpassword = findViewById(R.id.cpass);

        Button b = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();


    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String emai = email.getText().toString();
        String passwor = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwor) && !isPasswordValid(passwor)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emai)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emai)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password
        String confirmPassword = cpassword.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser() {

        String emai = email.getText().toString();
        String passwor = password.getText().toString();



        mAuth.createUserWithEmailAndPassword(emai, passwor).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("L", "createUser onComplete: " + task.isSuccessful());

                        if(!task.isSuccessful()){
                            Log.d("L", "user creation failed"+task.isSuccessful());
                            showErrorDialog("Registration attempt failed");
                        } else {
                            saveDisplayName();
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
    }


    // TODO: Save the display name to Shared Preferences
    private void saveDisplayName() {
        String displayName = name.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }


    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


}
