package com.example.licenseebe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDTO {
    String username;
    String email;
    int reviewsNumber;
    boolean twoFactorStatus;
}
