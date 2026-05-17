package org.example.shiftsync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shiftsync.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long Id;
    private String FullName;
    private String Email;
    private String token;
    private String refreshToken;
    private Role role;

}
