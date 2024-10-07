package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.rollerShade.FabricCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RollerFabricResponse {
    private String name;
    public static RollerFabricResponse getRollerFabricResponse(FabricCollection fabric) {
        return RollerFabricResponse.builder()
                .name(fabric.getName())
                .build();
    }
}
