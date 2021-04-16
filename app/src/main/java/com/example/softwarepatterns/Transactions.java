package com.example.softwarepatterns;

import java.util.ArrayList;

public class Transactions {
    public static class Builder{
        private String name;
        private ArrayList<String> purchases;

        public Builder(String name){
            this.name = name;
        }

        public Builder atPurchaes(ArrayList<String> purchases){
            this.purchases = purchases;

            return this;
        }

        public Transactions build(){
            Transactions transaction = new Transactions();
            transaction.name = this.name;
            transaction.purchases = this.purchases;

            return transaction;
        }
    }

    String name;
    ArrayList<String> purchases;

    private Transactions() {
        this.name = null;
        this.purchases = null;
    }


    public Transactions(String name, ArrayList<String> purchases) {
        this.name = name;
        this.purchases = purchases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<String> purchases) {
        this.purchases = purchases;
    }
}
