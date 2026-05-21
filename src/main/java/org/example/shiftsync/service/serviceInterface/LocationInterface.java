package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.LocationDTO;
import org.example.shiftsync.dto.LocationResponseDTO;

import java.util.List;

public interface LocationInterface {
    LocationResponseDTO createLocation(LocationDTO dto);
    LocationResponseDTO getLocationById(Long id);
    List<LocationResponseDTO> getAllLocations();
    LocationResponseDTO updateLocation(Long id, LocationDTO dto);
    void deleteLocation(Long id);
}
