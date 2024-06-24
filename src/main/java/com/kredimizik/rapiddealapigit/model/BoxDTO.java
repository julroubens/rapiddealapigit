package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoxDTO {

    private Long packegeId;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String weight;

    @Size(max = 255)
    private String dimentions;

    private Long order;

}
