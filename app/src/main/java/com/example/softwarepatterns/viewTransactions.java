package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewTransactions extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public DatabaseReference db;
    public ArrayList<String> myDataSet = new ArrayList<String>();
    String word = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);


        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);


        mAuth = FirebaseAuth.getInstance();
        DatabaseReference fireBD = FirebaseDatabase.getInstance().getReference("Transactions");
        fireBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    Transactions tranObj = Snapshot.getValue(Transactions.class);
                    word = word + tranObj.getName();
                    word = word + "\n";
                    for(int x =0; x < tranObj.getPurchases().size(); x++){
                        word = word + tranObj.getPurchases().get(x) + ", ";
                    }
                    myDataSet.add(word);
                    word = "";
                }

                mRecyclerView.setLayoutManager(mLayoutManager);
                MyAdapter mAdapter = new MyAdapter(myDataSet);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(viewTransactions.this, LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}