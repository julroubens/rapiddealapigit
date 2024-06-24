package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private Long transactionId;

    private Double amount;

    private Double dept;

    private LocalDate transactionDate;

    @Size(max = 255)
    private String paymentMethod;

    @Size(max = 255)
    private String status;

    @NotNull
    private Long client;

    @NotNull
    private Long shipment;

}
