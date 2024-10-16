package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.ShadeSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RollerShadeRepo
 * <p>
 *     This interface is responsible for handling the database operations for the RollerShadeSystem entity.
 *     It extends the JpaRepository interface to handle the basic crud operations.
 *     It also has a custom method to find a RollerShadeSystem by its name.
 *     This interface is used by the RollerShadeService class to interact with the database.
 * </p>
 *
 * @see ShadeSystem
 * @author Franklin Neves Filho
 */
@Repository
public interface RollerShadeRepo extends JpaRepository<ShadeSystem, String> {
    Optional<ShadeSystem> findByName(String name);
}
