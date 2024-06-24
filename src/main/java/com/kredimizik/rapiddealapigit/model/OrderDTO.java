package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long oderId;

    private LocalDateTime orderDate;

    @Size(max = 50)
    private String status;

    private Long client;

}
