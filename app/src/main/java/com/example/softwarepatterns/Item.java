package com.example.softwarepatterns;

public class Item {

    String title;
    String manufacturer;
    double price;
    String category;
    String image;
    int stock;

    public Item() {
        this.title = null;
        this.manufacturer = null;
        this.price = 0;
        this.category = null;
        this.image = null;
        this.stock = 0;
    }

    public Item(String title, String manufacturer, double price, String category, String image, int stock) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.price = price;
        this.category = category;
        this.image = image;
        this.stock = stock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
