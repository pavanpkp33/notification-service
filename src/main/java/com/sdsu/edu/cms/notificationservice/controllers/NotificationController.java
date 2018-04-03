package com.sdsu.edu.cms.notificationservice.controllers;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.notificationservice.service.NotifyEmailService;
import com.sdsu.edu.cms.notificationservice.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.SendFailedException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
public class NotificationController {

    @Autowired
    NotifyService notifyService;


    @PostMapping("/notify")
    public ServiceResponse notify(@RequestBody Notify payLoad){
        notifyService.sendNotifications(payLoad);

        return null;
    }
}
