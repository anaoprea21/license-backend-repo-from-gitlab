package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookRatingsResponseDto {
    String text;
    String userName;
    UUID userId;
    UUID ratingId;
    Timestamp addedDate;
    Integer rating;

    public GetBookRatingsResponseDto(Review r)
    {
        this.text = r.getText();
        this.userName = r.getUser().getUsername();
        this.userId = r.getUser().getId();
        this.ratingId = r.getId();
        this.addedDate = r.getCreatedAt();
        this.rating = r.getRating();
    }
}
