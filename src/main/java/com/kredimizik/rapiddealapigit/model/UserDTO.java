package com.kredimizik.rapiddealapigit.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long userId;

    @Size(max = 255)
    private String userName;

    @Size(max = 255)
    private String userEmail;

    @Size(max = 50)
    private String phoneNumber;

    @Size(max = 50)
    private String role;

}
