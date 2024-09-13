package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.BottomRail;
import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
import com.vertilux.shadeCalculator.models.rollerShade.RollerShadeSystem;
import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import com.vertilux.shadeCalculator.repositories.BottomRailRepo;
import com.vertilux.shadeCalculator.repositories.RollerTubeRepo;
import com.vertilux.shadeCalculator.schemas.RollerTubeResponse;
import com.vertilux.shadeCalculator.schemas.SystemLimit;
import com.vertilux.shadeCalculator.services.BottomRailService;
import com.vertilux.shadeCalculator.services.RollerFabricService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for basic calculations for a roller shade
 * Methods:
 * - getRollUp calculates the roll up diameter of a fabric based on the drop and the tube
 *
 */
@Slf4j
@AllArgsConstructor
@Component
public class ShadeCalculator {
    private MeasurementConverter measurementConverter;
    private BottomRailRepo bottomRailRepo;
    private BottomRailService bottomRailService;
    private RollerFabricService fabricService;
    private RollerTubeRepo tubeRepo;

    /**
     * This method calculates the roll up of a shade.
     *
     * @param drop              the drop of the shade
     * @param tubeOuterDiameter the outer diameter of the tube
     * @param fabricThickness   the thickness of the fabric
     * @return the roll up of the shade in the same unit as the drop
     */
    public Measurement getRollUp(Measurement drop, Measurement tubeOuterDiameter, Measurement fabricThickness) {
        double rollUpValue = -1;
        tubeOuterDiameter = measurementConverter.convert(tubeOuterDiameter, drop.getUnit());
        fabricThickness = measurementConverter.convert(fabricThickness, drop.getUnit());

        if (tubeOuterDiameter.getValue() != -1 || fabricThickness.getValue() != -1) {
            // conversion exists
            rollUpValue = Math.sqrt(
                    Math.pow(tubeOuterDiameter.getValue(), 2) + ((4 * fabricThickness.getValue() * drop.getValue()) / Math.PI));
        }

        return Measurement.builder()
                .value(rollUpValue)
                .unit(drop.getUnit())
                .build();
    }

    /**
     * Calculate the max drop of a shade based on the roll up.
     * @param maxRollUp the max roll up of the shade
     * @param tubeOuterDiameter the outer diameter of the tube
     * @param fabricThickness the fabric of the shade
     * @return the max drop of the shade in the same unit as the roll up
     */
    private Measurement getMaxDrop(Measurement maxRollUp, Measurement tubeOuterDiameter, Measurement fabricThickness) {
        double maxDropValue = -1;

        maxRollUp = measurementConverter.convert(maxRollUp, "mm");
        tubeOuterDiameter = measurementConverter.convert(tubeOuterDiameter, "mm");
        fabricThickness = measurementConverter.convert(fabricThickness, "mm");

        if(maxRollUp.getValue() != -1 && tubeOuterDiameter.getValue() != -1 && fabricThickness.getValue() != -1){
            double maxRollupSquared = Math.pow(maxRollUp.getValue(), 2);
            double tubeOuterDiameterSquared = Math.pow(tubeOuterDiameter.getValue(), 2);

            maxDropValue = (Math.PI * ((maxRollupSquared - tubeOuterDiameterSquared) / 4)) / (fabricThickness.getValue());
        }
        log.info("Max Drop: {} {}", maxDropValue/1000, "m");
        return Measurement.builder()
                .value(maxDropValue)
                .unit("mm")
                .build();
    }

    /**
     * This method returns the moment of inertia of a hollow tube.
     * The formula is:
     * I = (pi * ((d^4) - ((d - 2t)^4)) / 64
     *
     * @param tube the tube to calculate the moment of inertia
     * @return the moment of inertia of the tube
     */
    private Measurement getMomentOfInertia(RollerTube tube) {
        Measurement result = Measurement.builder().value(-1).unit("mm^4").build();
        Measurement thickness = measurementConverter.convert(tube.getThickness(), "mm");
        Measurement innerDiameter = measurementConverter.convert(tube.getInnerDiameter(), "mm");
        innerDiameter = measurementConverter.convert(innerDiameter, "mm");

        if (innerDiameter.getValue() != -1){
            double moment = (Math.PI * ((Math.pow(innerDiameter.getValue(), 4)) -
                                    (Math.pow((innerDiameter.getValue() -
                                            (2 * thickness.getValue())), 4)))) / 64;

            result = Measurement.builder()
                    .value(moment)
                    .unit("mm^4")
                    .build();
        }
        return result;
    }

    /**
     * This method calculates the total load on the tube.
     *
     * @param fabric     The fabric of the shade
     * @param bottomRail The bottom rail of the shade
     * @param width      The width of the shade
     * @param drop       The drop of the shade
     * @return the total load on the tube in the same unit as the drop
     */
    public Measurement getTotalLoad(RollerFabric fabric, BottomRail bottomRail, Measurement width, Measurement drop) {
        Measurement result = Measurement.builder().value(-1).build();
        String convertTo = "m";
        drop = measurementConverter.convert(drop, convertTo);
        width = measurementConverter.convert(width, convertTo);

        if (drop.getValue() != -1 && width.getValue() != -1) {

            Measurement fabricWeight = fabricService.getWeightKg(fabric, width, drop);
            fabricWeight = measurementConverter.convert(fabricWeight, "N");
            Measurement bottomRailWeight = bottomRailService.getWeightKg(bottomRail, width);
            bottomRailWeight = measurementConverter.convert(bottomRailWeight, "N");

            if (fabricWeight.getValue() != -1 && bottomRailWeight.getValue() != -1) {
                double totalLoad = fabricWeight.getValue() + bottomRailWeight.getValue();

                result = Measurement.builder()
                        .value(totalLoad)
                        .unit("N")
                        .build();
            }
        }

        return result;
    }
    /**
     * This method calculates the deflection of a tube.
     * The formula is:
     * deflection = (5 * w * l^3) / (384 * E * I)
     * @param fabric The fabric of the shade
     * @param tube The tube of the shade
     * @param width The width of the shade
     * @param drop The drop of the shade
     * @return the deflection of the tube
     */
    public Measurement getTubeDeflection(RollerFabric fabric, RollerTube tube, Measurement width, Measurement drop){
        Measurement result = Measurement.builder().value(-1).build();
        BottomRail basic = bottomRailRepo.findByName("Euro Slim").orElse(null);
        if (basic != null) {
            Measurement l = measurementConverter.convert(width, "mm");
            Measurement E = measurementConverter.convert(tube.getModulus(), "N/mm2");

            if (l.getValue() != -1 && E.getValue() != -1) {
                Measurement w = getTotalLoad(fabric, basic, width, drop); // N
                Measurement I = getMomentOfInertia(tube); // mm^4

                double deflection = (5 * w.getValue() * Math.pow(l.getValue(), 3)) / (384 * E.getValue() * I.getValue());

                result = Measurement.builder()
                        .value(deflection)
                        .unit("mm")
                        .build();
            }
        }
        return result;
    }

    /**
     * Get all the system limits for all tubes
     * @param system the system chosen
     * @param fabric the fabric chosen
     * @return a list of all system limits
     */
    public List<SystemLimit> getAllSystemLimits(String unit, RollerShadeSystem system, RollerFabric fabric) {
        List<SystemLimit> systemLimits = new ArrayList<>();
        List<RollerTube> tubes = tubeRepo.findAll();
        for (RollerTube tube : tubes) {
            SystemLimit systemLimit = getSystemLimit(unit, system, fabric, tube);
            systemLimits.add(systemLimit);
        }
        return systemLimits;
    }

    /**
     * This method calculates the system limit of a shade.
     * The system limit is the maximum width, and drop that a shade can have,
     * based on the tube, fabric, and system type.
     * @param system the system chosen
     * @param fabric the fabric chosen
     * @param tube the tube chosen
     * @return All information about the system limit
     */
    public SystemLimit getSystemLimit(String unit, RollerShadeSystem system, RollerFabric fabric, RollerTube tube) {
        byte maxDeflection = 3;

        Measurement currWidth = Measurement.builder().value(0).unit("mm").build();
        Measurement deflection = Measurement.builder().value(0).unit("mm").build();
        Measurement drop = getMaxDrop(system.getMaxDiameter(), tube.getOuterDiameter(), fabric.getThickness());
        if( drop.getValue() != -1){

            Measurement prevWidth = currWidth;
            Measurement prevDeflection = deflection;
            while(deflection.getValue() < maxDeflection){

                double diff = prevDeflection.getValue() - maxDeflection;
                int step;

                if(diff > 1){
                    step = 20;
                }else if (diff > 0.5){
                    step = 10;
                } else {
                    step = 5;
                }

                currWidth = Measurement.builder()
                        .value(currWidth.getValue() + step) // increase by 10mm
                        .unit(currWidth.getUnit())
                        .build();

                deflection = getTubeDeflection(fabric, tube, currWidth, drop);

                if (deflection.getValue() > maxDeflection){
                    currWidth = prevWidth;
                    deflection = prevDeflection;
                    break;
                }else{
                    prevWidth = currWidth;
                    prevDeflection = deflection;
                }

            }
        }

        currWidth = roundMeasurement(
                measurementConverter.convert(currWidth, unit)
        );
        drop = roundMeasurement(
                measurementConverter.convert(drop, unit)
        );

        if(unit.equals("m")){
            deflection = roundMeasurement(
                    measurementConverter.convert(deflection, "mm")
            );
        }else if (unit.equals("ft")){
            deflection = roundMeasurement(
                    measurementConverter.convert(deflection, "in")
            );
        }else{
            deflection = roundMeasurement(deflection);
        }


        return SystemLimit.builder()
                .maxWidth(currWidth)
                .maxDrop(drop)
                .tube(RollerTubeResponse.builder()
                        .name(tube.getName())
                        .build())
                .deflection(deflection)
                .build();
    }

    /**
     * Round a measurement to 2 decimal places
     * @param measurement the measurement to round
     * @return the rounded measurement
     */
    private Measurement roundMeasurement(Measurement measurement) {
        return Measurement.builder()
                .value(Math.round(measurement.getValue() * 100.0) / 100.0)
                .unit(measurement.getUnit())
                .build();
    }

}