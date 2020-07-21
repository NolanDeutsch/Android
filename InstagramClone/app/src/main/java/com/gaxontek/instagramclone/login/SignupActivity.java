package com.gaxontek.instagramclone.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaxontek.instagramclone.MainActivity;
import com.gaxontek.instagramclone.R;
import com.gaxontek.instagramclone.login.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignInEmail";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    private EditText etUser;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("users");

        UtilsInitilization();

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String username = etUser.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(username.isEmpty() || username.length() == 0 || email.isEmpty() || email.length() == 0 || password.isEmpty() || password.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Please Fill in All the Fields!", Toast.LENGTH_LONG).show();
                }
                else{
                    CreateUser(username, email, password);
                }
            }
        });
    }

    private void UtilsInitilization() {
        etUser = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnCreateUser = findViewById(R.id.btnCreateUser);
    }

    private void CreateUser(String username, String email, String password) {
        Log.d(TAG, "signIn:" + email);
        final User mUser = new User(username, email);
        mAuth.createUserWithEmailAndPassword(mUser.email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        myRef.child(user.getUid()).setValue(mUser);
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
