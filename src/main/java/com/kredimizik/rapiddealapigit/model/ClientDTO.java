package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientDTO {

    private Long clientId;

    @Size(max = 255)
    private String clientName;

    @Size(max = 255)
    private String clientEmail;

    @Size(max = 50)
    private String phoneNumber;

    @Size(max = 255)
    private String address;

}
