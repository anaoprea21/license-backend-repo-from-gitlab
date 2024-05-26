package com.example.licenseebe.helper;

import com.example.licenseebe.controller.EmailController;
import com.example.licenseebe.model.CalendarBookDate;
import com.example.licenseebe.repository.calendarEvent.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@EnableScheduling
@Component
public class CalendarChron {

    final EmailController emailController;

    final CalendarEventRepository calendarEventRepository;

    public CalendarChron(EmailController emailController, CalendarEventRepository calendarEventRepository) {
        this.emailController = emailController;
        this.calendarEventRepository = calendarEventRepository;
    }


    @Scheduled(cron = "0 0 23 * * *")
    public void sendCalendarEmails() {
        List<CalendarBookDate> calendarBookDates = calendarEventRepository.getCalendarDates();
        for (CalendarBookDate c : calendarBookDates) {
            if (c.getDate().toInstant().isBefore(Instant.now())) {
                String[] email = new String[1];
                email[0] = c.getUser().getEmail();
                emailController.sendSimpleEmail(EmailTemplates.sendReminderEmail(), "Book reminder for date: " + c.getDate().toString(), email, null);
            }
        }
    }

        @Scheduled(cron = "0 0 23 * * *")
    public void deletePastCalendarEmails() {
        List<CalendarBookDate> calendarBookDates = calendarEventRepository.getCalendarDates();
        for (CalendarBookDate c : calendarBookDates) {
            if (c.getDate().toInstant().isBefore(Instant.now())) {
                calendarEventRepository.delete(c);
            }
        }
    }
}
