package com.example.licenseebe.controller;

import com.example.licenseebe.dto.request.CreateNewBookDTO;
import com.example.licenseebe.dto.request.CreateNewCalendarEventDTO;
import com.example.licenseebe.helper.BookType;
import com.example.licenseebe.helper.HttpStatusHelper;
import com.example.licenseebe.service.BookCategoryService;
import com.example.licenseebe.service.BookService;
import com.example.licenseebe.service.CalendarEventService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static com.example.licenseebe.helper.HttpStatusHelper.success;

@RestController
@Log4j2
@CrossOrigin
@RequestMapping("/calendar-event")
public class CalendarEvent {

    private final HttpStatusHelper httpStatusHelper;

    private final CalendarEventService calendarEventService;


    @Autowired
    public CalendarEvent(HttpStatusHelper httpStatusHelper,CalendarEventService calendarEventService) {
        this.httpStatusHelper = httpStatusHelper;
        this.calendarEventService = calendarEventService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/get-user-events/{email}")
    public ResponseEntity<Object> getUserEvents(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String email) {
        try {
            return success("response", calendarEventService.getUserEvents(httpServletRequest, httpServletResponse, email));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 404, message = "BOOK_NOT_FOUND"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/get-event/{uuid}")
    public ResponseEntity<Object> getBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID uuid) {
        try {
            return success("response", calendarEventService.getEvent(httpServletRequest, httpServletResponse, uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/add-event")
    public ResponseEntity<Object> createNewEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody CreateNewCalendarEventDTO newEvent) {
        try {
            return success("response", calendarEventService.createNewEvent(httpServletRequest, httpServletResponse, newEvent));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "SOMETHING_WENT_WRONG"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 409, message = "CONFLICT"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @DeleteMapping("/delete-event/{uuid}")
    public ResponseEntity<Object> deleteEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable UUID uuid) {
        try {
            return success("response", calendarEventService.deleteEvent(httpServletRequest, httpServletResponse, uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return httpStatusHelper.commonErrorMethod(httpServletRequest, e, null);
        }

    }
}
