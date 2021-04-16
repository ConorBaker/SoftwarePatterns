package com.example.softwarepatterns;

public class CheckFull implements EncryptionStrategy {
    private static CheckFull instance;

    public CheckFull(){

    }

    public static CheckFull getInstance(){
        if(instance == null){
            instance = new CheckFull();
        }
        return instance;
    }


    public boolean check(String w1, String w2) {
        if (w1.equalsIgnoreCase(w2)) {
            return true;
        }else{
            return false;
        }
    }
}
