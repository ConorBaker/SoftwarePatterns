package com.example.softwarepatterns;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;

    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signInCheck(View v){

        EditText usernameText = (EditText) findViewById( R.id.uEditText );
        EditText passwordText = (EditText) findViewById( R.id.pEditText );

        email = usernameText.getText().toString();
        password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User signed in",
                                    Toast.LENGTH_SHORT).show();
                            /*
                            Intent intent= new Intent();
                            startActivity(intent);

                             */
                        } else {
                            Log.w("MySignin", "SignInUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }}});


    }

    public void signUpCheck(View v){

        EditText usernameText = (EditText) findViewById( R.id.uEditText );
        EditText passwordText = (EditText) findViewById( R.id.pEditText );

        email = usernameText.getText().toString();
        password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {
                            Log.d("myAcctivity", "createUserWithEmail:success");
                            FirebaseUser u = mAuth.getCurrentUser();
                            String UserID = u.getUid();
                            db = FirebaseDatabase.getInstance().getReference();
                            User user = new User(email);
                            db.child("Users").child(UserID).setValue(user).addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(MainActivity.this, "Write is successful",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, "Write is failed", Toast.LENGTH_LONG).show();}
                                    });
                            /*
                            Intent intent= new Intent();
                            startActivity(intent);

                             */
                        } else {
                            Log.w("myAcctivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }}});
    }


}