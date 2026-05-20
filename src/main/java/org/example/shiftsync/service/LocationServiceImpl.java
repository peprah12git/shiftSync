package org.example.shiftsync.service;

import lombok.RequiredArgsConstructor;
import org.example.shiftsync.Entity.Location;
import org.example.shiftsync.dto.LocationDTO;
import org.example.shiftsync.dto.LocationResponseDTO;
import org.example.shiftsync.exception.ResourceNotFoundException;
import org.example.shiftsync.repository.LocationRepository;
import org.example.shiftsync.service.serviceInterface.LocationInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationInterface {

    private final LocationRepository locationRepository;

    @Override
    public LocationResponseDTO createLocation(LocationDTO dto) {
        if (locationRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Location with name '" + dto.getName() + "' already exists");
        }
        Location location = Location.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .maxHeadcountPerShift(dto.getMaxHeadcountPerShift())
                .build();
        return mapToDto(locationRepository.save(location));
    }

    @Override
    public LocationResponseDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
        return mapToDto(location);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponseDTO updateLocation(Long id, LocationDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
        location.setName(dto.getName());
        location.setAddress(dto.getAddress());
        location.setMaxHeadcountPerShift(dto.getMaxHeadcountPerShift());
        return mapToDto(locationRepository.save(location));
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
        location.setActive(false);
        locationRepository.save(location);
    }

    private LocationResponseDTO mapToDto(Location location) {
        return new LocationResponseDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getMaxHeadcountPerShift()
        );
    }
}