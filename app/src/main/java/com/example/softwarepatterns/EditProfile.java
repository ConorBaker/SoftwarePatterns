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

public class EditProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(UserID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText nameText = (EditText) findViewById(R.id.nameEditText);
                EditText addressText = (EditText) findViewById(R.id.addressEditText);

                User userObj = snapshot.getValue(User.class);
                nameText.setText(userObj.getName());
                addressText.setText(userObj.getAddress());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DBError", "Cancel Access DB");};

        });;
    }

    public void update(View v) {
        EditText nameText = (EditText) findViewById(R.id.nameEditText);
        String name = nameText.getText().toString();
        EditText addressText = (EditText) findViewById(R.id.addressEditText);
        String address = addressText.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UserID = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("Users").child(UserID);


        if(!name.equals("")){
            db.child("name").setValue(name);
        }
        if(!address.equals("")){
            db.child("address").setValue(address);
        }

        Intent intent= new Intent(getApplicationContext(), ViewItems.class);
        startActivity(intent);

    }

}