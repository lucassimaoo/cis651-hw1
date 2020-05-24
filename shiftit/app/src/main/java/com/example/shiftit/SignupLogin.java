package com.example.shiftit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupLogin extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private Repository repository = Repository.getInstance();

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText phone;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        signup = findViewById(R.id.signup);
        updateUI();
    }

    private void updateUI() {
        if (currentUser != null) {
            findViewById(R.id.name_w).setVisibility(View.GONE);
            findViewById(R.id.phone_w).setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
        }
    }

    public void signup(View view) {
        if (email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()
                || phone.getText().toString().trim().isEmpty() ||
                name.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please provide all information", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser = authResult.getUser();
                        currentUser.sendEmailVerification().addOnSuccessListener(SignupLogin.this, new
                                OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignupLogin.this, "Signup successful. Verification email Sent!", Toast.LENGTH_SHORT).show();

                                    User user = new User(currentUser.getUid(), name.getText().toString(), email.getText().toString(),
                                            phone.getText().toString(), null, null);

                                    repository.save(user);
                                    updateUI();
                                    }
                                }).addOnFailureListener(SignupLogin.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupLogin.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void reset(View view) {
        if (email.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.sendPasswordResetEmail(email.getText().toString()).addOnFailureListener(this, new
                OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignupLogin.this, "Email sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verify(View view) {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login first to resend verification email.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        currentUser.sendEmailVerification()
                .addOnSuccessListener(SignupLogin.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignupLogin.this, "Verification email Setn!",
                                Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                }).addOnFailureListener(SignupLogin.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(View view) {
        if (email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please provide all information", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser = authResult.getUser();
                        if (currentUser.isEmailVerified()) {
                            Toast.makeText(SignupLogin.this, "Login Succesful.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupLogin.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignupLogin.this, "Please verify your email an login again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
