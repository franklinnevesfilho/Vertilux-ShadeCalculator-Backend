package com.vertilux.shadeCalculator.repositories;

import com.vertilux.shadeCalculator.models.rollerShade.Fabric;
import com.vertilux.shadeCalculator.models.rollerShade.FabricCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FabricCollectionRepo extends JpaRepository<FabricCollection, String> {
    Optional<FabricCollection> findByName(String name);
    Optional<FabricCollection> findByFabricsContains(Fabric fabric);
}
