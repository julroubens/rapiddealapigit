package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TrackingEventDTO {

    private Long eventId;

    @Size(max = 255)
    private String eventType;

    private LocalDate eventDate;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String description;

    @NotNull
    private Long shipment;

}
