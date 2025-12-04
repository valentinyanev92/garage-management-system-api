package com.softuni.gms.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairCompletionRequest {

    @NotBlank
    String carBrand;

    @NotBlank
    String carModel;

    @NotBlank
    String mechanicFistName;

    @NotBlank
    String mechanicLastName;

    @NotBlank
    String phoneNumber;
}
