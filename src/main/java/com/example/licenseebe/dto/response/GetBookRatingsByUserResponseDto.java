package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookRatingsByUserResponseDto {
    String text;
    String bookTitle;
    UUID bookId;
    UUID ratingId;
    Timestamp addedDate;
    Integer rating;

    private byte[] picture;
    public GetBookRatingsByUserResponseDto(Review r) throws SQLException {
        this.text = r.getText();
        this.bookTitle = r.getBook().getTitle();
        this.bookId = r.getBook().getId();
        this.ratingId = r.getId();
        this.addedDate = r.getCreatedAt();
        this.rating = r.getRating();
        if(r.getBook().getPicture() != null)
            this.picture = r.getBook().getPicture().getBytes(1,(int)r.getBook().getPicture().length());
    }
}
