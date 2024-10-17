package com.vertilux.shadeCalculator.schemas;

import com.vertilux.shadeCalculator.models.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FabricCollectionCreation implements Schema{
    private String name;
    private Measurement thickness;
    private Measurement weight;
}
