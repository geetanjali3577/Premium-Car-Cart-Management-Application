package com.premiumcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PremiumCarCartManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PremiumCarCartManagementApplication.class, args);
        System.err.println(" Premium Car Cart Management Application Run ");
        System.err.println(" http://localhost:8080/swagger-ui/index.html ");
    }

}
