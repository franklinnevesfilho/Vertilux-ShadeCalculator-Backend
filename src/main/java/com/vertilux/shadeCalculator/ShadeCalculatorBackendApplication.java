package com.vertilux.shadeCalculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ShadeCalculatorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadeCalculatorBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(){
        return args -> {
            String devMode = System.getenv("DEV_MODE");

            if(devMode != null && devMode.equals("production")){
                log.info("Running in production mode...");
            } else {
                log.info("Running in development mode...");
            }

            log.info("Shade Calculator Backend Application is running...");
        };
    }

}
