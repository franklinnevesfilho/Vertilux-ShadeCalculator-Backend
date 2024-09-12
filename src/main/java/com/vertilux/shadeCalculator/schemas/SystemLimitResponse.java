package com.vertilux.shadeCalculator.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SystemLimitResponse {
    private String systemName;
    private RollerTubeResponse tube;
    private RollerFabricResponse fabric;
    private List<SystemLimits> limits;
}
