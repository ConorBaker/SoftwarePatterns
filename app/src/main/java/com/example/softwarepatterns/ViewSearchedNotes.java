package com.example.softwarepatterns;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ViewSearchedNotes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    public DatabaseReference db;
    public ArrayList<String> myDataSet = new ArrayList<String>();
    private static EncryptionStrategy es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_notes);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);


        Spinner spinner2 = findViewById(R.id.search_Spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.SpinnerArray3, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        final String basket = getIntent().getStringExtra("basket");

        final String cat = getIntent().getStringExtra("cat");

        final String word = getIntent().getStringExtra("word");


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference fireBD = FirebaseDatabase.getInstance().getReference("Items");
        fireBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    Item itemObj = Snapshot.getValue(Item.class);
                    if (word.equals(" ")) {
                        myDataSet.add(itemObj.title);
                    } else {
                        if (cat.equals("Title")) {
                            es = CheckPartial.getInstance();
                            if (es.check(itemObj.title, word)) {
                                myDataSet.add(itemObj.title);
                            }
                        } else if (cat.equals("Category")) {
                            es = CheckFull.getInstance();
                            if (es.check(itemObj.getCategory(),word)) {
                                myDataSet.add(itemObj.title);
                            }
                        } else if (cat.equals("Manufacturer")) {
                            es = CheckPartial.getInstance();
                            if (es.check(itemObj.manufacturer, word)) {
                                myDataSet.add(itemObj.title);
                            }
                        }
                    }

                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                MyAdapter mAdapter = new MyAdapter(myDataSet);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(ViewSearchedNotes.this, LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

                final String basket = getIntent().getStringExtra("basket");

                mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String title = myDataSet.get(position);
                        Intent intent = new Intent(getBaseContext(), ViewOneItem.class);
                        intent.putExtra("title", title);
                        intent.putExtra("basket", basket);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort(View v) {
        Spinner spinner2 = findViewById(R.id.search_Spinner2);
        String order = String.valueOf(spinner2.getSelectedItem());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

            if (order.equals("Ascending")) {
                myDataSet.sort(Comparator.comparing(String::toString));
            } else {
                myDataSet.sort(Comparator.comparing(String::toString).reversed());
            }

            mRecyclerView.setLayoutManager(mLayoutManager);
            MyAdapter mAdapter = new MyAdapter(myDataSet);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(ViewSearchedNotes.this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);
    }
}