package org.example.shiftsync.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @NotBlank(message = "Location name is required")
    @Size(max = 150, message = "Location name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 300, message = "Address must not exceed 300 characters")
    private String address;

    @NotNull(message = "Max headcount per shift is required")
    @Positive(message = "Max headcount per shift must be a positive number")
    private Integer maxHeadcountPerShift;
}