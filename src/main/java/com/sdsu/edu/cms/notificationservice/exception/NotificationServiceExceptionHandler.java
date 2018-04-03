package com.sdsu.edu.cms.notificationservice.exception;



import com.sdsu.edu.cms.common.models.response.ApiError;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Arrays;

@ControllerAdvice
@RestController
public class NotificationServiceExceptionHandler extends ResponseEntityExceptionHandler{

    ApiError apiError;
    @ExceptionHandler(NotificationTransportException.class)
    public final ResponseEntity<Object> handleNotificationTransportError(Exception ex, WebRequest request) {

        apiError = new ApiError(Constants.INTERNAL_SERVER_ERROR,ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotificationMethodNotSupported.class)
    public final ResponseEntity<Object> handleNotificationNotSupportedError(Exception ex, WebRequest request) {

        apiError = new ApiError(Constants.BAD_REQUEST,ex.getMessage()+" method is currently not supported.", request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest request) {
        apiError = new ApiError(Constants.INTERNAL_SERVER_ERROR,ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        apiError = new ApiError(Constants.INTERNAL_SERVER_ERROR,ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
