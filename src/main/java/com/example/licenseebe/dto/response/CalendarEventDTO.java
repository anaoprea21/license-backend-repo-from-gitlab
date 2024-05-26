package com.example.licenseebe.dto.response;

import com.example.licenseebe.model.CalendarBookDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventDTO {

    private UUID id;

    private UUID bookId;

    String bookTitle;

    byte[] bookCover;

    Timestamp time;
    Long timeLeft;

    public CalendarEventDTO(CalendarBookDate calendarEvent) throws SQLException {
        this.id = calendarEvent.getId();
        this.bookId = calendarEvent.getBook().getId();
        if (calendarEvent.getBook().getPicture() != null)
            this.bookCover = calendarEvent.getBook().getPicture().getBytes(1, (int) calendarEvent.getBook().getPicture().length());
        this.bookTitle = calendarEvent.getBook().getTitle();
        this.time = calendarEvent.getDate();
        this.timeLeft = calendarEvent.getDate().getTime() - Instant.EPOCH.toEpochMilli();
    }

}
