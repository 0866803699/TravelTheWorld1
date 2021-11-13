package com.example.traveltheworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book{
    private String placeName;
    private String countryName;
    private String price;
    private String imageUrl;
    private String ratings;
    private String time;
    private String description;

    public Book() {
    }

    public Book(String name, String countryName, String price, String imageUrl, String description, String start, String time) {
        this.placeName = placeName;
        this.countryName = this.countryName;
        this.price = this.price;
        this.imageUrl = this.imageUrl;
        this.ratings = ratings;
        this.time = this.time;
        this.description = this.description;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
