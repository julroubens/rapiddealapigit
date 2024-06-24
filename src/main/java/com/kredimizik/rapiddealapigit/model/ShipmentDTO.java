package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShipmentDTO {

    private Long shipmentId;

    @Size(max = 255)
    private String trackingNumber;

    private LocalDate shimentDate;

    private LocalDate deliveryDate;

    @Size(max = 255)
    private String status;

    @Size(max = 50)
    private String carrier;

    private Long box;

}
