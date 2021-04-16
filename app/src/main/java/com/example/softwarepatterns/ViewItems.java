package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ViewItems extends AppCompatActivity implements AdapterView.OnItemSelectedListener,OrderServiceFacade{

    int stock =0;
    private FirebaseAuth mAuth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        Spinner spinner = findViewById(R.id.search_Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SpinnerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        String basket = getIntent().getStringExtra("basket");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void edit(MenuItem menuitem) {
        Intent intent = new Intent(getApplicationContext(), EditProfile.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void viewAll(View V) {
        String basket = getIntent().getStringExtra("basket");;
        Intent intent = new Intent(getApplicationContext(), ViewSearchedNotes.class);
        intent.putExtra("basket", basket);
        intent.putExtra("cat", " ");
        intent.putExtra("word", " ");
        startActivity(intent);
    }

    public void search(View V){
        Spinner spinner = findViewById(R.id.search_Spinner);
        String cat = String.valueOf(spinner.getSelectedItem());
        EditText searchText = (EditText) findViewById(R.id.tagEditText);
        String searchWord = String.valueOf(searchText.getText());

        String basket = getIntent().getStringExtra("basket");;
        Intent intent = new Intent(getApplicationContext(), ViewSearchedNotes.class);
        intent.putExtra("basket", basket);
        intent.putExtra("cat", cat);
        intent.putExtra("word", searchWord);
        startActivity(intent);
    }

    public void checkout(View V){
        String basket = getIntent().getStringExtra("basket");;
        String[] items = basket.split("_");
        List<String> itemsAsList = Arrays.asList(items);
        ArrayList<String> trans = new ArrayList<>();
        Iterator<String> namesIterator = itemsAsList.iterator();


        while (namesIterator.hasNext()) {
            String title = namesIterator.next();
            if (!title.equals("")) {
                DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Items").child(title);
                fireDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Item item = snapshot.getValue(Item.class);
                        if(placeOrder(item)){
                            stock = item.getStock() -1;
                            fireDB.child("stock").setValue(stock);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                trans.add(title);

            }
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        fireDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userObj = snapshot.getValue(User.class);
                Transactions tran = new Transactions.Builder(userObj.getName()).atPurchaes(trans).build();
                db = FirebaseDatabase.getInstance().getReference();
                db.child("Transactions").push().setValue(tran).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Intent intent = new Intent(getApplicationContext(), PaymentService.class);
        startActivity(intent);
    }

    public void viewTrans(View V){
        Intent intent = new Intent(getApplicationContext(), viewTransactions.class);
        startActivity(intent);
    }

    @Override
    public boolean placeOrder(Item item) {
        boolean orderFulfilled = false;
        if (CheckInventory.isAvailable(item)) {
            boolean paymentConfirmed = RecievePayment.makePayment(true);
            if (paymentConfirmed) {
                orderFulfilled = true;
            }
        } else {
            orderFulfilled = false;
        }
        return orderFulfilled;
    }
}