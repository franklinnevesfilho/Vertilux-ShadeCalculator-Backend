package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.rollerShade.RollerTube;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RollerTubeResponse {
    private String name;
    public static RollerTubeResponse getRollerTubeResponse(RollerTube tube) {
        return RollerTubeResponse.builder()
                .name(tube.getName())
                .build();
    }
}
