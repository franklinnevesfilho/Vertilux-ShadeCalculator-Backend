package com.vertilux.shadeCalculator.services;

import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.measurements.Measurement;
import com.vertilux.shadeCalculator.models.measurements.MeasurementUnit;
import com.vertilux.shadeCalculator.models.measurements.UnitConversion;
import com.vertilux.shadeCalculator.repositories.ConversionRepo;
import com.vertilux.shadeCalculator.repositories.MeasurementRepo;
import com.vertilux.shadeCalculator.schemas.ConversionCreation;
import com.vertilux.shadeCalculator.utils.MeasurementConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Measurement Service
 * This class handles all the CRUD operations for the measurement units, and their conversion rates.
 *
 * @see MeasurementConverter for precise usage
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class MeasurementService extends MainService{
    private final MeasurementRepo measurementRepo;
    private final ConversionRepo conversionRepo;
    private final MeasurementConverter measurementConverter;

    /**
     * This method returns all the measurement units in the database.
     * @return Response object with all the measurement units
     */
    public Response getAllUnits(){
        List<MeasurementUnit> units = measurementRepo.findAll();

        return Response.builder()
                .data(mapToJson(units))
                .build();
    }

    /**
     * This method returns a measurement unit by its name.
     * @param unitName The name of the unit to be found
     * @return Response object with the found MeasurementUnit
     */
    public Response getUnitByName(String unitName){
        Optional<MeasurementUnit> found = measurementRepo.findByUnit(unitName);
        if (found.isPresent()){
            return Response.builder()
                    .data(mapToJson(found.get()))
                    .build();
        }else{
            return Response.builder()
                    .errors(List.of("Unit not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method returns a measurement unit by its id.
     * @param unitId The id of the unit to be found
     * @return Response object with the found MeasurementUnit
     */
    public Response getUnitById(String unitId) {
        Optional<MeasurementUnit> found = measurementRepo.findById(unitId);
        if (found.isPresent()) {
            return Response.builder()
                    .data(mapToJson(found.get()))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Unit not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * @param unitId The id of the unit to be deleted
     * @return Response object with the status of the operation
     */
    public Response deleteUnitById(String unitId) {
        Response response;
        try {
            measurementRepo.deleteById(unitId);
        } catch (Exception e) {
            response = Response.builder()
                    .errors(List.of("Unit not found"))
                    .status("error")
                    .build();
            return response;
        }
        return Response.builder().build();
    }

    /**
     * @param unitName The name of the unit to be deleted
     * @return Response object with the status of the operation
     */
    public Response deleteUnitByName(String unitName) {
        Optional<MeasurementUnit> found = measurementRepo.findByUnit(unitName);
        if (found.isPresent()) {
            measurementRepo.delete(found.get());
            return Response.builder().build();
        } else {
            return Response.builder()
                    .errors(List.of("Unit not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method returns all the conversion rates in the database.
     * @return Response object with all the conversion rates
     */

    public Response getAllConversions() {
        List<UnitConversion> conversions = conversionRepo.findAll();
        return Response.builder()
                .data(mapToJson(conversions))
                .build();
    }

    /**
     * This method saves a new conversion rate to the database.
     * @param conversion Contains from, to and rate attribute for conversion
     * @return Response object with the saved UnitConversion
     */
    public Response saveConversion(ConversionCreation conversion) {
        Response response;

        Optional<MeasurementUnit> optFrom = measurementRepo.findByUnit(conversion.getFrom());
        Optional<MeasurementUnit> optTo = measurementRepo.findByUnit(conversion.getTo());
        MeasurementUnit from;
        MeasurementUnit to;

        if(optFrom.isPresent() && optTo.isPresent()){
            from = optFrom.get();
            to = optTo.get();
        }else{
            from = saveUnit(conversion.getFrom());
            to = saveUnit(conversion.getTo());
        }

        if (conversionRepo.findByFromUnitAndToUnit(from, to).isPresent()){

            response = Response.builder()
                        .errors(List.of("Conversion already exists"))
                        .build();
        } else{

            UnitConversion newConversion = UnitConversion.builder()
                    .fromUnit(from)
                    .toUnit(to)
                    .conversionFactor(conversion.getRate())
                    .build();
            UnitConversion savedConversion = conversionRepo.save(newConversion);
            response = Response.builder()
                    .data(mapToJson(savedConversion))
                    .build();
        }

        return response;
    }

    /**
     * @param id The id of the conversion to be deleted
     * @return Response object with the status of the operation
     */
    public Response deleteConversion(String id) {
        Response response;
        try {
            conversionRepo.deleteById(id);
        } catch (Exception e) {
            response = Response.builder()
                    .errors(List.of("Conversion not found"))
                    .status("error")
                    .build();
            return response;
        }
        return Response.builder().build();
    }

    /**
     * @param measurement measurement to be converted
     * @param toUnit unit to convert to
     * @return Response object with the converted measurement
     */
    public Response convert(Measurement measurement, String toUnit){
        Response response;

        Optional<MeasurementUnit> to = measurementRepo.findByUnit(toUnit);
        Optional<MeasurementUnit> from = measurementRepo.findByUnit(measurement.getUnit());

        if (to.isEmpty() || from.isEmpty()){
            response = Response.builder()
                    .errors(List.of("One or more units do not exist"))
                    .status("error")
                    .build();
        }else{
            response = Response.builder()
                    .data(mapToJson(measurementConverter.convert(measurement, to.get())))
                    .build();
        }


        return response;
    }

    /**
     * This method saves a new measurement unit to the database.
     * @param unit The MeasurementUnit object to be saved
     * @return MeasurementUnit object
     */
    private MeasurementUnit saveUnit(String unit) {
        Optional<MeasurementUnit> found = measurementRepo.findByUnit(unit);
        if (found.isPresent()) {
            return found.get();
        } else {
            MeasurementUnit newUnit = MeasurementUnit.builder()
                    .unit(unit)
                    .build();
            return measurementRepo.save(newUnit);

        }
    }
}


