package com.vertilux.shadeCalculator.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Item {
    private String id;
    private String description;
    private String type;
    private String packingUnit;
}
