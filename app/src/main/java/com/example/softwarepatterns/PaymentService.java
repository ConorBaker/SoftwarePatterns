package com.example.softwarepatterns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_service);
    }

    public void goToShipping(View V){
        Intent intent = new Intent(getApplicationContext(), Shipping.class);
        startActivity(intent);
    }
}