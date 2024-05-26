package com.example.licenseebe.repository.calendarEvent;

import com.example.licenseebe.dto.response.CalendarEventDTO;
import com.example.licenseebe.dto.response.CalendarEventDrawerDataDTO;
import com.example.licenseebe.dto.response.EditableBookDTO;
import com.example.licenseebe.model.CalendarBookDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalendarEventRepository extends JpaRepository<CalendarBookDate, Integer>, CalendarEventRepositoryCustom {

    @Query("SELECT NEW com.example.licenseebe.dto.response.CalendarEventDTO(c) FROM CalendarBookDate c WHERE c.user.email = :email")
    List<CalendarEventDTO> getUserEvents(String email);

    @Query("SELECT NEW com.example.licenseebe.dto.response.CalendarEventDrawerDataDTO(c) FROM CalendarBookDate c WHERE c.id = :uuid")
    Optional<CalendarEventDrawerDataDTO> getEvent(UUID uuid);
    @Query("SELECT c FROM CalendarBookDate c WHERE c.id = :uuid")
    Optional<CalendarBookDate> getEventAsEvent(UUID uuid);

    @Query("SELECT c FROM CalendarBookDate c")
    List<CalendarBookDate> getCalendarDates();
}
