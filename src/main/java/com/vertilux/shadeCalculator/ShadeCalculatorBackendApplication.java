package com.vertilux.shadeCalculator;

import com.vertilux.shadeCalculator.schemas.ConversionCreation;
import com.vertilux.shadeCalculator.services.MeasurementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@Slf4j
@SpringBootApplication
public class ShadeCalculatorBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShadeCalculatorBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(MeasurementService measurementService){
        return args -> {
            String devMode = System.getenv("DEV_MODE");

            if(devMode != null && devMode.equals("production")){
                log.info("Running in production mode...");
            } else {
                log.info("Running in development mode...");
                createConversions(measurementService);
            }

            log.info("Shade Calculator Backend Application is running...");
        };
    }

    protected void createConversions(MeasurementService measurementService){

        List< ConversionCreation > conversions = List.of(
                ConversionCreation.builder()
                        .from("cm")
                        .to("in")
                        .rate(0.393701)
                        .build(),
                ConversionCreation.builder()
                        .from("in")
                        .to("cm")
                        .rate(2.54)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("ft")
                        .rate(3.28084)
                        .build(),
                ConversionCreation.builder()
                        .from("ft")
                        .to("m")
                        .rate(0.3048)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("yd")
                        .rate(1.09361)
                        .build(),
                ConversionCreation.builder()
                        .from("yd")
                        .to("m")
                        .rate(0.9144)
                        .build()
        );

        conversions.forEach(measurementService::saveConversion);
    }

}
