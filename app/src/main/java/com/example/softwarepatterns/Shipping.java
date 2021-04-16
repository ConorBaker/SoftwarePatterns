package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Shipping extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(UserID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView addressText = (TextView) findViewById(R.id.textView9);

                User userObj = snapshot.getValue(User.class);
                addressText.setText(userObj.getAddress());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DBError", "Cancel Access DB");};

        });;
    }

    public void returnHome(View V){
        Intent intent = new Intent(getApplicationContext(), ViewItems.class);
        intent.putExtra("basket", "_");
        startActivity(intent);
    }
}