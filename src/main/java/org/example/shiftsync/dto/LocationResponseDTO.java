package org.example.shiftsync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDTO {
    private Long id;
    private String name;
    private String address;
    private Integer maxHeadcountPerShift;
}