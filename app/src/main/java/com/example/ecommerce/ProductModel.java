package com.example.ecommerce;

public class ProductModel {
    private String name;
    private String price;
    private String brand;
    private String description;
    private String category;
    private int imageResource;


    public ProductModel(String name, String price, String brand, String description, String category, int imageResource) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResource() {
        return imageResource;
    }
}
