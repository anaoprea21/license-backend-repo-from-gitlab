package com.example.licenseebe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBooksByUserLibrary {
    String accessToken;
    String searchInput;
}
