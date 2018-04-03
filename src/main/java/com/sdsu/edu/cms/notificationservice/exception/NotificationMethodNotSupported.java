package com.sdsu.edu.cms.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotificationMethodNotSupported extends RuntimeException {

    public NotificationMethodNotSupported(String message) {
        super(message);
    }
}
