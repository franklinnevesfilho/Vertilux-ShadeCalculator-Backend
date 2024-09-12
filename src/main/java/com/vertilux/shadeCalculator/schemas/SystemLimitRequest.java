package com.vertilux.shadeCalculator.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SystemLimitRequest implements Schema{
    private String systemName;
    private String fabricName;
    private String tubeName;
}
