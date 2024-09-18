package com.vertilux.shadeCalculator;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.schemas.*;
import com.vertilux.shadeCalculator.services.*;
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
    CommandLineRunner run(
            MeasurementService measurementService,
            BottomRailService bottomRailService,
            RollerFabricService rollerFabricService,
            RollerShadeService rollerShadeService,
            RollerTubeService rollerTubeService
    ) {
        return args -> {
            String devMode = System.getenv("DEV_MODE");

            if(devMode != null && devMode.equals("production")){
                log.info("Running in production mode...");
            } else {
                log.info("Running in development mode...");
                createConversions(measurementService);
                createBottomRails(bottomRailService);
                createRollerFabrics(rollerFabricService);
                createRollerShadeSystems(rollerShadeService);
                createRollerTubes(rollerTubeService);
            }

            log.info("Shade Calculator Backend Application is running...");
        };
    }

    protected void createRollerShadeSystems(RollerShadeService rollerShadeService){
        List<RollerShadeSystemCreation> rollerShadeSystems = List.of(
                RollerShadeSystemCreation.builder()
                        .name("Cassette 100")
                        .maxDiameter(Measurement.builder()
                                .value(65)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerShadeSystemCreation.builder()
                        .name("Cassette 120 flat")
                        .maxDiameter(Measurement.builder()
                                .value(83.5)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerShadeSystemCreation.builder()
                        .name("Cassette 120 round")
                        .maxDiameter(Measurement.builder()
                                .value(78)
                                .unit("mm")
                                .build()
                        )
                        .build()
        );

        rollerShadeSystems.forEach(rollerShadeService::save);
    }
    protected void createRollerTubes(RollerTubeService rollerTubeService){
        List<RollerTubeCreation> rollerTubes = List.of(
                RollerTubeCreation.builder()
                        .name("28mm - 1 1/8\"")
                        .innerDiameter(Measurement.builder()
                                .value(26.79)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(27.1)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("32mm - 1 1/4\" LGH")
                        .innerDiameter(Measurement.builder()
                                .value(30)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(32)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("32mm - 1 1/4\" STD")
                        .innerDiameter(Measurement.builder()
                                .value(30)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(32.6)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("38mm - 1 1/2\" STD")
                        .innerDiameter(Measurement.builder()
                                .value(35.89)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(38.43)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("38mm - 1 1/2\" HD")
                        .outerDiameter(Measurement.builder()
                                .value(40.3)
                                .unit("mm")
                                .build()
                        )
                        .innerDiameter(Measurement.builder()
                                .value(35.9)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("45mm - 1 3/4\"")
                        .innerDiameter(Measurement.builder()
                                .value(41.5)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(45)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerTubeCreation.builder()
                        .name("50mm - 2\"")
                        .innerDiameter(Measurement.builder()
                                .value(47)
                                .unit("mm")
                                .build()
                        )
                        .outerDiameter(Measurement.builder()
                                .value(51.25)
                                .unit("mm")
                                .build()
                        )
                        .build()
        );

        rollerTubes.forEach(rollerTubeService::createRollerTube);

    }
    protected void createRollerFabrics(RollerFabricService rollerFabricService){
        List<RollerFabricCreation> rollerFabrics = List.of(
                RollerFabricCreation.builder()
                        .name("Light-demo1")
                        .weight(Measurement.builder()
                                .value(227)
                                .unit("g/m2")
                                .build()
                        )
                        .thickness(Measurement.builder()
                                .value(0.34)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerFabricCreation.builder()
                        .name("Light-demo2")
                        .weight(Measurement.builder()
                                .value(230)
                                .unit("g/m2")
                                .build()
                        )
                        .thickness(Measurement.builder()
                                .value(0.41)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerFabricCreation.builder()
                        .name("Medium-demo")
                        .weight(Measurement.builder()
                                .value(410)
                                .unit("g/m2")
                                .build()
                        )
                        .thickness(Measurement.builder()
                                .value(0.5)
                                .unit("mm")
                                .build()
                        )
                        .build(),
                RollerFabricCreation.builder()
                        .name("Heavy-demo")
                        .weight(Measurement.builder()
                                .value(610)
                                .unit("g/m2")
                                .build()
                        )
                        .thickness(Measurement.builder()
                                .value(0.8)
                                .unit("mm")
                                .build()
                        )
                        .build()
        );

        rollerFabrics.forEach(rollerFabricService::createRollerFabric);

    }
    protected void createBottomRails(BottomRailService bottomRailService){
        List<BottomRailCreation> bottomRails = List.of(
                BottomRailCreation.builder()
                        .name("Euro Slim")
                        .weight(Measurement.builder()
                                        .value(1.15)
                                        .unit("kg/m")
                                        .build()
                        )
                        .build()
        );

        bottomRails.forEach(bottomRailService::saveBottomRail);

    }
    protected void createConversions(MeasurementService measurementService){

        List< ConversionCreation > conversions = List.of(
                ConversionCreation.builder()
                        .from("mm")
                        .to("m")
                        .factor(0.001)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("mm")
                        .factor(1000)
                        .build(),
                ConversionCreation.builder()
                        .from("cm")
                        .to("m")
                        .factor(0.01)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("cm")
                        .factor(100)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("ft")
                        .factor(3.28084)
                        .build(),
                ConversionCreation.builder()
                        .from("ft")
                        .to("m")
                        .factor(0.3048)
                        .build(),
                ConversionCreation.builder()
                        .from("m")
                        .to("in")
                        .factor(39.3701)
                        .build(),
                ConversionCreation.builder()
                        .from("in")
                        .to("m")
                        .factor(0.0254)
                        .build(),
                ConversionCreation.builder()
                        .from("mm")
                        .to("in")
                        .factor(0.0393701)
                        .build(),
                ConversionCreation.builder()
                        .from("in")
                        .to("mm")
                        .factor(25.4)
                        .build(),
                ConversionCreation.builder()
                        .from("mm")
                        .to("ft")
                        .factor(0.00328084)
                        .build(),
                ConversionCreation.builder()
                        .from("ft")
                        .to("mm")
                        .factor(304.8)
                        .build(),
                // weight conversions
                ConversionCreation.builder()
                        .from("kg/m")
                        .to("g/m")
                        .factor(1000)
                        .build(),
                ConversionCreation.builder()
                        .from("kg/m")
                        .to("g/mm")
                        .factor(1)

                        .build(),
                ConversionCreation.builder()
                        .from("kg")
                        .to("N")
                        .factor(9.81)
                        .build(),
                ConversionCreation.builder()
                        .from("g")
                        .to("N")
                        .factor(0.00981)
                        .build(),
                ConversionCreation.builder()
                        .from("g/m")
                        .to("kg/m")
                        .factor(0.001)
                        .build(),
                ConversionCreation.builder()
                        .from("g/m")
                        .to("g/mm")
                        .factor(0.001)
                        .build(),
                ConversionCreation.builder()
                        .from("g/m2")
                        .to("g/mm2")
                        .factor(0.000001)
                        .build(),
                ConversionCreation.builder()
                        .from("g/m2")
                        .to("kg/m2")
                        .factor(0.001)
                        .build(),
                // modulus of elasticity conversions
                ConversionCreation.builder()
                        .from("GPa")
                        .to("N/mm2")
                        .factor(1000)
                        .build()
        );

        conversions.forEach(measurementService::saveConversion);
    }

}
