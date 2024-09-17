package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.rollerShade.RollerFabric;
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
    public static RollerFabricResponse getRollerFabricResponse(RollerFabric fabric) {
        return RollerFabricResponse.builder()
                .name(fabric.getName())
                .build();
    }
}
