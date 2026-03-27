package com.codewithmosh.store.seeders;

import com.codewithmosh.store.products.categories.Category;
import com.codewithmosh.store.products.Product;
import com.codewithmosh.store.products.categories.CategoryRepository;
import com.codewithmosh.store.products.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductSeeder {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Transactional
    public void seedProducts() {


        Category electronics = categoryRepository.save(new Category("Electronics"));
        Category books = categoryRepository.save(new Category("Books"));
        Category clothing = categoryRepository.save(new Category("Clothing"));
        Category home = categoryRepository.save(new Category("Home"));

        List<Product> products = List.of(

                new Product(null, "Laptop", "High performance laptop", BigDecimal.valueOf(1200), electronics),
                new Product(null, "Smartphone", "Latest smartphone", BigDecimal.valueOf(800), electronics),
                new Product(null, "Tablet", "Portable tablet", BigDecimal.valueOf(500), electronics),
                new Product(null, "Headphones", "Noise cancelling headphones", BigDecimal.valueOf(200), electronics),
                new Product(null, "Camera", "Digital camera", BigDecimal.valueOf(650), electronics),

                new Product(null, "Novel", "Fiction novel", BigDecimal.valueOf(20), books),
                new Product(null, "Cookbook", "Cooking recipes", BigDecimal.valueOf(25), books),
                new Product(null, "History Book", "World history", BigDecimal.valueOf(30), books),
                new Product(null, "Programming Book", "Learn Java", BigDecimal.valueOf(45), books),
                new Product(null, "Notebook", "Writing notebook", BigDecimal.valueOf(10), books),

                new Product(null, "T-Shirt", "Cotton t-shirt", BigDecimal.valueOf(15), clothing),
                new Product(null, "Jeans", "Blue jeans", BigDecimal.valueOf(40), clothing),
                new Product(null, "Jacket", "Winter jacket", BigDecimal.valueOf(120), clothing),
                new Product(null, "Sneakers", "Running sneakers", BigDecimal.valueOf(75), clothing),
                new Product(null, "Cap", "Baseball cap", BigDecimal.valueOf(12), clothing),

                new Product(null, "Chair", "Wooden chair", BigDecimal.valueOf(60), home),
                new Product(null, "Table", "Dining table", BigDecimal.valueOf(250), home),
                new Product(null, "Lamp", "Desk lamp", BigDecimal.valueOf(35), home),
                new Product(null, "Sofa", "Comfortable sofa", BigDecimal.valueOf(700), home),
                new Product(null, "Coffee Maker", "Automatic coffee maker", BigDecimal.valueOf(90), home)
        );

        productRepository.saveAll(products);
    }
}
