package com.codewithmosh.store;

import com.codewithmosh.store.seeders.ProductSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {

       ApplicationContext context =  SpringApplication.run(StoreApplication.class, args);

//       var productSeeder = context.getBean(ProductSeeder.class);
//       productSeeder.seedProducts();
    }
}
