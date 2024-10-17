package com.vertilux.shadeCalculator.services;
import com.vertilux.shadeCalculator.models.rollerShade.Fabric;
import com.vertilux.shadeCalculator.models.Response;
import com.vertilux.shadeCalculator.models.rollerShade.FabricCollection;
import com.vertilux.shadeCalculator.repositories.FabricCollectionRepo;
import com.vertilux.shadeCalculator.repositories.RollerFabricRepo;
import com.vertilux.shadeCalculator.schemas.FabricCollectionCreation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RollerFabricService
 * This class is responsible for handling the business logic for the RollerFabric entity.
 * @see Fabric
 * @author Franklin Neves Filho
 */

@Slf4j
@AllArgsConstructor
@Service
public class FabricService extends MainService {
    private final RollerFabricRepo rollerFabricRepo;
    private final FabricCollectionRepo collectionRepo;


    /**
     * This method returns all the roller fabrics in the database.
     * @return returns the collections with its associated fabrics
     */
    public Response getAllFabrics(){
        List<FabricCollection> collections = collectionRepo.findAll();
        return Response.builder()
                .data(mapToJson(collections))
                .build();
    }

    /**
     * This method returns a roller fabric by its name.
     * @param name The name of the collection to be found
     * @return Response object with the found RollerFabric
     */
    public Response getCollection(String name){
        FabricCollection collection = collectionRepo.findByName(name).orElse(null);
        if (collection != null) {
            return Response.builder()
                    .data(mapToJson(collection))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Collection not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method will get a collection based on a fabrics name or id
     * @param param can be either id or name of a fabric
     * @return the collection that the fabric belongs to
     */
    public Response getFabricCollection(String param){
        Response result = Response.builder()
                .errors(List.of("Fabric not found"))
                .status("error")
                .build();

        Fabric fabric = rollerFabricRepo.findByName(param).orElse(null);
        if (fabric == null) {
            fabric = rollerFabricRepo.findById(param).orElse(null);
            if (fabric != null) {
                FabricCollection collection = collectionRepo.findByFabricsContains(fabric).orElse(null);
                if (collection != null) {
                    result = Response.builder()
                            .data(mapToJson(collection))
                            .build();
                }
            }
        }

        return result;
    }

    /**
     * Save new fabric to collection
     * @param fabricId the id of the fabric.
     * @param fabricDesc the description of the fabric
     * @param collectionName the name of the collection it belongs to
     * @return The fabric object after being saved/ updated
     */
    public Response saveFabric(String fabricId, String fabricDesc, String collectionName){
        FabricCollection collection = collectionRepo.findByName(collectionName).orElse(null);
        if (collection == null) {
            collection = FabricCollection.builder()
                    .name(collectionName)
                    .build();
            collection = collectionRepo.save(collection);
        }

        Fabric fabric = rollerFabricRepo.findById(fabricId).orElse(null);
        if (fabric == null) {
            fabric = Fabric.builder()
                    .id(fabricId)
                    .description(fabricDesc)
                    .fabricCollection(collection)
                    .build();
        } else {
            fabric.setDescription(fabricDesc);
            fabric.setFabricCollection(collection);
        }

        fabric = rollerFabricRepo.save(fabric);
        return Response.builder()
                .data(mapToJson(fabric))
                .build();
    }

    /**
     * This method deletes a fabric from the database.
     * @param fabricId The id of the fabric to be deleted
     * @return Response object with the deleted RollerFabric
     */
    public Response deleteFabric(String fabricId) {
        Fabric found = rollerFabricRepo.findById(fabricId).orElse(null);
        if (found != null) {
            rollerFabricRepo.delete(found);
            return Response.builder()
                    .data(mapToJson(found))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method updates a fabric in the database.
     * @param fabricId The id of the fabric to be updated
     * @param fabricDesc The description of the fabric to be updated
     * @param collectionName The name of the collection the fabric belongs to
     * @return Response object with the updated RollerFabric
     */
    public Response updateFabric(String fabricId, String fabricDesc, String collectionName) {
        Fabric found = rollerFabricRepo.findById(fabricId).orElse(null);
        if (found != null) {
            FabricCollection collection = collectionRepo.findByName(collectionName).orElse(null);
            if (collection == null) {
                collection = FabricCollection.builder()
                        .name(collectionName)
                        .build();
                collection = collectionRepo.save(collection);
            }

            found.setDescription(fabricDesc);
            found.setFabricCollection(collection);
            Fabric updated = rollerFabricRepo.save(found);
            return Response.builder()
                    .data(mapToJson(updated))
                    .build();
        } else {
            return Response.builder()
                    .errors(List.of("Fabric not found"))
                    .status("error")
                    .build();
        }
    }

    /**
     * This method creates a new fabric collection in the database.
     * @param collection The collection to be created
     * @return Response object with the created RollerFabric
     */
    public Response createCollection(FabricCollectionCreation collection){
        FabricCollection newCollection = FabricCollection.builder()
                .name(collection.getName())
                .thickness(collection.getThickness())
                .weight(collection.getWeight())
                .build();
        newCollection = collectionRepo.save(newCollection);
        return Response.builder()
                .data(mapToJson(newCollection))
                .build();
    }


}