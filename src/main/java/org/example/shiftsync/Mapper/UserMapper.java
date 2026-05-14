package org.example.shiftsync.Mapper;


import org.example.shiftsync.Entity.User;
import org.example.shiftsync.dto.RegistrationDTO;
import org.example.shiftsync.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegistrationDTO registrationDTO) {

            User user = new User();
            user.setEmail(registrationDTO.getEmail());
            user.setFullName(registrationDTO.getFullName());
            user.setPasswordHash(registrationDTO.getPassword());
            //user.setRole(registrationDTO.getRole());
            return user;

    }

    public UserResponse toDTO(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
       // response.setRole(user.getRole());
        return response;
    }
}
