package com.sdsu.edu.cms.notificationservice.controllers;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.utils.Constants;
import com.sdsu.edu.cms.notificationservice.exception.NotificationMethodNotSupported;
import com.sdsu.edu.cms.notificationservice.service.NotifyEmailService;
import com.sdsu.edu.cms.notificationservice.service.NotifyService;
import com.sdsu.edu.cms.notificationservice.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MethodNotSupportedException;
import javax.mail.SendFailedException;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
public class NotifyController {

    @Autowired
    NotifyService notifyService;


    @PostMapping("/notify")
    public ServiceResponse notify(@RequestBody Notify payLoad){
        payLoad.getMethod().forEach((type ->{
            if(!Util.getSupportedMethods().contains(type)) throw new NotificationMethodNotSupported(type);
        }));
        notifyService.sendNotifications(payLoad);

        return new ServiceResponse(Arrays.asList(true), "Client notified successfully.");
    }
}
