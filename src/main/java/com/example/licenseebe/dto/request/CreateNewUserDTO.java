package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewUserDTO {

    String email;
    String password;
    String username;
    UserRole role;
}
