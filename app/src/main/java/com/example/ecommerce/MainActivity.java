package com.example.ecommerce;

import android.database.Cursor;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ProductModel> productList = new ArrayList<>();
    private DBManager dbManager;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize DBManager
        dbManager = new DBManager(this);
        dbManager.open();

        // Insert sample products
        insertSampleProducts();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.carousel); // Ensure this ID matches your layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load products from database
        loadProductsFromDatabase();


}

    void setProductList() {

    }

    private void insertSampleProducts() {
        // Sample product data
        String[][] products = {
                //item_name, item_desc, item_price, brand, category
                {"Men's T-Shirts", "Cotton, crew neck", "19.99", "Brand A", "Man"},
                {"Women's Dress", "Floral, flowy", "49.99", "Brand B", "Woman"},
                {"Addidas Jeans", "Classic denim", "59.99", "Brand C", "Man"},
                {"Sneaky Sneakers", "Athletic shoes", "79.99", "Brand D", "Man"},
                {"Fem Jacket", "Waterproof, winter", "99.99", "Brand E", "Woman"},
                {"Swimsuit", "One-piece, bikini", "39.99", "Brand F", "Woman"},
                {"Hat", "Baseball cap", "14.99", "Brand G", "Accessories"},
                {"Sunglasses", "UV protection", "29.99", "Brand H", "Man"},
                {"Belt", "Leather, adjustable", "24.99", "Brand I", "Kids"},
                {"Socks", "Crew socks, cotton", "9.99", "Brand J", "Kids"}
        };

        // Insert each product into the database
        for (String[] product : products) {
            dbManager.insert(product[0], product[2], product[3], product[1], product[4]);
        }
    }

    private void loadProductsFromDatabase() {
        productList = new ArrayList<>();
        Cursor cursor = dbManager.fetch();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.item_name));
                String desc = cursor.getString(cursor.getColumnIndex(DatabaseHelper.item_description));
                String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.item_price));
                String brand = cursor.getString(cursor.getColumnIndex(DatabaseHelper.brand));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.category));
                int imageResource = R.drawable.model;


                ProductModel product = new ProductModel(name, desc, price, brand, category, imageResource);
                productList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Set adapter
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}
