package com.mapbox.mymap.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mapbox.mymap.MapViewActivity;
import com.mapbox.mymap.R;

public class RegisterEmailPasswordActivity extends AppCompatActivity {

    private static final String TAG = RegisterEmailPasswordActivity.class.getName();
    private FirebaseAuth mAuth;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText displayNameEditText = findViewById(R.id.display_name);
        final Button loginButton = findViewById(R.id.register);
        loadingProgressBar = findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingProgressBar.setVisibility(View.VISIBLE);
                createUserWithEmailAndPassword(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);


    }

    private void updateUI(FirebaseUser currentUser) {
        loadingProgressBar.setVisibility(View.GONE);
        if (currentUser!=null){
            startActivity(new Intent(getApplicationContext(), MapViewActivity.class));
            finish();
        }
    }

    private void createUserWithEmailAndPassword(String email, String password) {

        if (isUserNameValid(email) && isPasswordValid(password)) {


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                updateUI(user);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterEmailPasswordActivity.this, "Authentication failed : " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }
                        }
                    });
        }else {
            showError(email,password);
        }
    }



    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), SignInEmailPasswordActivity.class));

    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private void showError(String email, String password) {
        if (!isUserNameValid(email)){
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
        }
        else if (!isPasswordValid(email)){
            Toast.makeText(this, "password must have at last 6 characters", Toast.LENGTH_SHORT).show();
        }
    }

}