package com.example.licenseebe.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpStatusHelper {

    private final Logger logger = LoggerFactory.getLogger(HttpStatusHelper.class);

    public static ResponseEntity<Object> success(String objectName, Object object) {
        Map<String, Object> map = new HashMap<>();
        map.put(objectName, object);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Object> success() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.OK);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public ResponseEntity<Object> conflict(String idEntity, String requestType, String requestPath, String objectAsString, CustomConflictException e) {

        e.printStackTrace();
        logger.error("idEntity: " + idEntity + " requestType: " + requestType + " requestPath: " + requestPath + " objectAsString: " + objectAsString +
                " errorMessage: " + e.getErrorMessage());

        Map<String, String> map = new HashMap<>();
        map.put("message", e.getErrorMessage());
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }

    public ResponseEntity<Object> internalServerError(String idEntity, String requestType, String requestPath, String objectAsString, Exception e) {
        e.printStackTrace();
        logger.error("idEntity: " + idEntity + " requestType: " + requestType + " requestPath: " + requestPath + " objectAsString: " + objectAsString +
                " errorMessage: " + e.getMessage());
        String MESSAGE_ERROR = "INTERNAL_SERVER_ERROR";
        String SERVER_ERROR_LOG = "The following error was encountered";

        Map<String, String> map = new HashMap<>();
        map.put("message", MESSAGE_ERROR);
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> objectNotFoundError(String idEntity, String requestType, String requestPath, String objectAsString,
                                                      CustomEntityNotFoundException e) {
        e.printStackTrace();
        logger.error("idEntity: " + idEntity + " requestType: " + requestType + " requestPath: " + requestPath + " objectAsString: " + objectAsString
                + " errorMessage: " + e.getErrorMessage());
        Map<String, String> map = new HashMap<>();
        map.put("message", e.getErrorMessage());

        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> commonErrorMethod(HttpServletRequest request, Exception e, Object object) {
        String userUuid = null;
        String requestType = request.getMethod();
        String requestPath = request.getRequestURI();

        Object objectToMap = null;

        ObjectMapper objectMapper = new ObjectMapper();
        String objectAsString = null;
        try {
            objectAsString = objectMapper.writeValueAsString(objectToMap);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        if (e instanceof CustomConflictException) {
            return conflict(userUuid, requestType, requestPath, objectAsString, (CustomConflictException) e);
        } else if (e instanceof CustomEntityNotFoundException) {
            return objectNotFoundError(userUuid, requestType, requestPath, objectAsString, (CustomEntityNotFoundException) e);
        } else if (e instanceof HttpClientErrorException) {
            return badRequest();
        } else {
            return internalServerError(userUuid, requestType, requestPath, objectAsString, e);
        }
    }

    public static ResponseEntity<Object> badRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
