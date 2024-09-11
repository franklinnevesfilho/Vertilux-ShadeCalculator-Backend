package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.BottomRail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * BottomRailRepo
 * <p>
 *     This interface is responsible for handling the database operations for the BottomRail entity.
 *     It extends the JpaRepository interface to handle the basic crud operations.
 *     This interface is used by the BottomRailService class to interact with the database.
 * </p>
 *
 * @see BottomRail
 * @author Franklin Neves Filho
 */
public interface BottomRailRepo extends JpaRepository<BottomRail, String> {
    Optional<BottomRail> findByName(String bottomRailName);
}
