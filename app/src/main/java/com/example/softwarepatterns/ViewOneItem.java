package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewOneItem extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_one_item);

        String title = getIntent().getStringExtra("title");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Items").child(title);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView titleText = (TextView) findViewById(R.id.uTextView2);
                TextView priceText = (TextView) findViewById(R.id.uTextView3);
                TextView stockText = (TextView) findViewById(R.id.uTextView4);

                Item itemObj = snapshot.getValue(Item.class);

                String price = String.valueOf(itemObj.getPrice());
                String stock = String.valueOf(itemObj.getStock());

                titleText.setText(itemObj.getTitle());
                priceText.setText(price);
                stockText.setText(stock);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DBError", "Cancel Access DB");};

        });;
    }

    public void addToBasket(View V){
        String basket = getIntent().getStringExtra("basket");;
        TextView titleText = (TextView) findViewById(R.id.uTextView2);
        String item = String.valueOf(titleText.getText());
        basket = basket + "_"+item;
        Intent intent= new Intent(getApplicationContext(), ViewItems.class);
        intent.putExtra("basket", basket);
        startActivity(intent);

    }

    public void updateStock(View V){
        mAuth = FirebaseAuth.getInstance();
        String title = getIntent().getStringExtra("title");
        final FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(UserID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getAdmin()){
                    Intent intent= new Intent(getApplicationContext(), UpdateStock.class);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }else{
                    Toast.makeText(ViewOneItem.this, "You don't have permission to do that", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}