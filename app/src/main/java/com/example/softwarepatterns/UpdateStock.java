package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateStock extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);

        String title = getIntent().getStringExtra("title");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("Items").child(title);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText nameText = (EditText) findViewById(R.id.uTextView2);
                EditText priceText = (EditText) findViewById(R.id.uTextView3);
                EditText stockText = (EditText) findViewById(R.id.uTextView4);

                Item item = snapshot.getValue(Item.class);
                nameText.setText(item.getTitle());
                priceText.setText(String.valueOf(item.getPrice()));
                stockText.setText(String.valueOf(item.getStock()));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DBError", "Cancel Access DB");};

        });;
    }


    public void update(View V){
        EditText nameText = (EditText) findViewById(R.id.uTextView2);
        String name = nameText.getText().toString();
        EditText priceText = (EditText) findViewById(R.id.uTextView3);
        String price = priceText.getText().toString();
        EditText stockText = (EditText) findViewById(R.id.uTextView4);
        String stock = stockText.getText().toString();


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(UserID);



        if(!nameText.equals("")){
            db.child("title").setValue(name);
        }
        if(!priceText.equals("")){
            db.child("price").setValue(price);
        }
        if(!stockText.equals("")){
            db.child("stock").setValue(stock);
        }

        Intent intent= new Intent(getApplicationContext(), ViewItems.class);
        startActivity(intent);
    }

}