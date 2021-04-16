package com.example.softwarepatterns;

public class CheckPartial implements EncryptionStrategy{

    private static CheckPartial instance;

    public CheckPartial(){

    }

    public static CheckPartial getInstance(){
        if(instance == null){
            instance = new CheckPartial();
        }
        return instance;
    }

    public boolean check(String w1, String w2) {
        String[] s1 = w1.split(" ");
        String[] s2 = w2.split(" ");
        boolean found = false;

        for (int x = 0; x < s1.length; x++) {
            for (int i = 0; i < s2.length; i++) {
                if (s1[x].equals(s2[i])) {
                    found = true;
                }
            }
        }
        return found;
    }
}
