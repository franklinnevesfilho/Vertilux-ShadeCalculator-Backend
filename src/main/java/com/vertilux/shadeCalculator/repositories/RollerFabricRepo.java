package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.FabricCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RollerFabricRepo
 * <p>
 *     This interface is responsible for handling the database operations for the RollerFabric entity.
 *     It extends the JpaRepository interface to handle the basic crud operations.
 *     This interface is used by the RollerFabricService class to interact with the database.
 * </p>
 *
 * @see FabricCollection
 * @author Franklin Neves Filho
 */

@Repository
public interface RollerFabricRepo extends JpaRepository<FabricCollection, String> {
    Optional<FabricCollection> findByName(String fabricName);
}
