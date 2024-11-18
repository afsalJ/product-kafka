package com.afsal.project2.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Range {
    @NotNull(message = "clientIdStart must not be null")
    private Integer clientIdStart;
    @NotNull(message = "empIdStart must not be null")
    private Integer empIdStart;
    @NotNull(message = "count must not be null")
    private Integer count;
}
