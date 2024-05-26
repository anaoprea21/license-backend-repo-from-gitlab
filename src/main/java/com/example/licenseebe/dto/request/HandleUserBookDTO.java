package com.example.licenseebe.dto.request;

import com.example.licenseebe.helper.UserBookHandlingActions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleUserBookDTO {
    String email;
    List<UUID> bookIds;
    UserBookHandlingActions action;
}
