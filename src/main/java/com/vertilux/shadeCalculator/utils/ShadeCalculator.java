package com.vertilux.shadeCalculator.utils;

import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.rollerShade.BottomRail;
import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import com.vertilux.shadeCalculator.repositories.BottomRailRepo;
import com.vertilux.shadeCalculator.repositories.MeasurementRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for basic calculations for a roller shade
 * Methods:
 * - getRollUp calculates the roll up diameter of a fabric based on the drop and the tube
 *
 */

@Builder
@AllArgsConstructor
@Component
public class ShadeCalculator {
    private MeasurementConverter measurementConverter;
    private MeasurementRepo measurementRepo;
    private BottomRailRepo bottomRailRepo;

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
                    Math.pow(tubeOuterDiameter.getValue(), 2) +
                            (4 * fabricThickness.getValue() * drop.getValue()) / Math.PI);
        }

        return Measurement.builder()
                .value(rollUpValue)
                .unit(drop.getUnit())
                .build();
    }

    /**
     * This method calculates the roll up of a shade.
     *
     * @param drop   the drop of the shade
     * @param fabric the fabric of the shade
     * @param tube   the tube of the shade
     * @return the roll up of the shade in the same unit as the drop
     */
    public Measurement getRollUp(Measurement drop, RollerFabric fabric, RollerTube tube) {
        return getRollUp(drop, tube.getOuterDiameter(), fabric.getThickness());
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
            Measurement fabricWeight = measurementConverter.convert(fabric.getWeightKg(width, drop), "N");
            Measurement bottomRailWeight = measurementConverter.convert(bottomRail.getWeightKg(width), "N");

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
        // unit to convert to
        BottomRail basic = bottomRailRepo.findByName("Euro Slim").orElse(null);

        if (basic != null) {


            Measurement l = measurementConverter.convert(drop, "mm");
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

}