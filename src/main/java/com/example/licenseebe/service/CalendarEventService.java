package com.example.licenseebe.service;

import com.example.licenseebe.dto.request.CreateNewCalendarEventDTO;
import com.example.licenseebe.dto.response.CalendarEventDTO;
import com.example.licenseebe.dto.response.CalendarEventDrawerDataDTO;
import com.example.licenseebe.helper.Conflict;
import com.example.licenseebe.helper.CustomConflictException;
import com.example.licenseebe.helper.HttpStatusHelper;
import com.example.licenseebe.helper.Response;
import com.example.licenseebe.model.Book;
import com.example.licenseebe.model.CalendarBookDate;
import com.example.licenseebe.model.User;
import com.example.licenseebe.repository.books.BookRepository;
import com.example.licenseebe.repository.calendarEvent.CalendarEventRepository;
import com.example.licenseebe.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.search.StringTerm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    @Autowired
    public CalendarEventService(HttpStatusHelper httpStatusHelper, CalendarEventRepository calendarEventRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.calendarEventRepository = calendarEventRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Object getUserEvents(HttpServletRequest request, HttpServletResponse response, String email) throws CustomConflictException {

        List<CalendarEventDTO> calendarEvents = calendarEventRepository.getUserEvents(email);
        if (calendarEvents.isEmpty()) {
            throw new CustomConflictException(Conflict.NO_EVENTS_FOUND);
        } else {
            return calendarEvents;
        }
    }
    public Object getEvent(HttpServletRequest request, HttpServletResponse response, UUID uuid) throws CustomConflictException {

        Optional<CalendarEventDrawerDataDTO> calendarEvent = calendarEventRepository.getEvent(uuid);
        if (calendarEvent.isPresent()) {
            return calendarEvent.get();
        } else {
            throw new CustomConflictException(Conflict.NO_EVENT_FOUND);
        }
    }

    public Object createNewEvent(HttpServletRequest request, HttpServletResponse response, CreateNewCalendarEventDTO newEvent) throws IOException, CustomConflictException {

        Optional<Book> eventBook=bookRepository.getBookAsBookById(newEvent.getBookId());
        if(eventBook.isPresent() ){
            Book book=eventBook.get();
            Optional<User> eventUser=userRepository.getUserByEmail(newEvent.getEmail());
            if(eventUser.isPresent()) {
                User user = eventUser.get();
                CalendarBookDate newEventToBeInserted = CalendarBookDate.builder()
                .book(book)
                .date(newEvent.getTime())
                .user(user)
                .build();

            calendarEventRepository.save(newEventToBeInserted);

            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
            }else{
                return new ResponseEntity<>(Response.NO_USER_FOUND, HttpStatus.BAD_REQUEST).getBody();
            }
        }
        return new ResponseEntity<>(Response.NO_BOOK_FOUND, HttpStatus.BAD_REQUEST).getBody();

    }

    public Object deleteEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UUID uuid) throws CustomConflictException {
        Optional<CalendarBookDate> deletedEvent = calendarEventRepository.getEventAsEvent(uuid);
        if (deletedEvent.isPresent()) {
            calendarEventRepository.delete(deletedEvent.get());
            return new ResponseEntity<>(Response.SUCCESS, HttpStatus.OK).getBody();
        } else {
            throw new CustomConflictException(Conflict.NO_EVENT_FOUND);
        }
    }
}
