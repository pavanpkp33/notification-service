package com.sdsu.edu.cms.notificationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotificationTransportException extends RuntimeException {

    public NotificationTransportException(String s){
        super(s);
    }


}
