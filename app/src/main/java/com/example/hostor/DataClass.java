package com.example.hostor;


import android.location.Location;

public class DataClass {
    private String email, imageURL , location, roomType, internet, park, food, residential, price , key;

    public DataClass(){
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getEmail() {
        return email;
    }
    public String getImageURL() {
        return imageURL;
    }

    public String getLocation() {
        return location;
    }
    public String getRoomType() {
        return roomType;
    }
    public String getInternet() {
        return internet;
    }
    public String getPark() {
        return park;
    }
    public String getFood() {
        return food;
    }
    public String getResidential() {
        return residential;
    }
    public String getPrice() {
        return price;
    }


    public DataClass(String email,String imageURL, String location, String roomType, String internet, String park, String food, String residential, String price) {
        this.email = email;
        this.imageURL = imageURL;
        this.location = location;
        this.roomType = roomType;
        this.internet = internet;
        this.park = park;
        this.food = food;
        this.residential = residential;
        this.price = price;
    }

}