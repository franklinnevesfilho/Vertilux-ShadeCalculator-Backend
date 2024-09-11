package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RollerTubeRepo
 * <p>
 *     This interface is responsible for handling the database operations for the RollerTube entity.
 *     It extends the JpaRepository interface to handle the basic crud operations.
 *     This interface is used by the RollerTubeService class to interact with the database.
 * </p>
 *
 * @see RollerTube
 * @author Franklin Neves Filho
 */

@Repository
public interface RollerTubeRepo extends JpaRepository<RollerTube, String> {
    Optional<RollerTube> findByName(String tubeName);
}
