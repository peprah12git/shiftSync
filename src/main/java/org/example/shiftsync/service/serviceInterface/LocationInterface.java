package org.example.shiftsync.service.serviceInterface;

import org.example.shiftsync.dto.LocationDTO;

import java.util.List;

public interface LocationInterface {
    LocationDTO createLocation(LocationDTO dto);
    LocationDTO getLocationById(Long id);
    List<LocationDTO> getAllLocations();
    LocationDTO updateLocation(Long id, LocationDTO dto);
    void deleteLocation(Long id);
}
