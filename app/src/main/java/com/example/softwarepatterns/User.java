package com.example.softwarepatterns;

public class User{

    public static class Builder {
        private String email;
        private String name;
        private String address;
        private Boolean admin;

        public Builder(String email){
            this.email = email;
        }

        public Builder atName (String name){
            this.name = name;

            return this;
        }

        public Builder atAddress (String address){
            this.address = address;

            return this;
        }

        public Builder atEmail (String email){
            this.name = email;

            return this;
        }

        public Builder atAdmin (Boolean admin){
            this.admin = admin;

            return this;
        }

        public User build(){
            User user = new User();
            user.name = this.name;
            user.address = this.address;
            user.email = this.email;
            user.admin = this.admin;

            return user;
        }

    }
    String email;
    private String name;
    private String address;
    private Boolean admin;

    private User(){
        this.email = null;
        this.name = null;
        this.address = null;
        this.admin = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
