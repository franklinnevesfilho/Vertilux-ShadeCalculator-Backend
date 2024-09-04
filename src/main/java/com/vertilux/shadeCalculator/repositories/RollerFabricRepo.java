package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RollerFabricRepo
 * <p>
 *     This interface is responsible for handling the database operations for the RollerFabric entity.
 *     It extends the JpaRepository interface to handle the basic crud operations.
 *     This interface is used by the RollerFabricService class to interact with the database.
 * </p>
 *
 * @see RollerFabric
 * @author Franklin Neves Filho
 */

@Repository
public interface RollerFabricRepo extends JpaRepository<RollerFabric, String> {
    RollerFabric findByName(String fabricName);
}
