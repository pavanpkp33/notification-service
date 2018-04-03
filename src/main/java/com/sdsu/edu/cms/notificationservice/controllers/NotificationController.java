package com.sdsu.edu.cms.notificationservice.controllers;


import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    //Checks for IN-APP notifications only. Notifications can be status update of papers or any communications.
    @GetMapping("/notifications/conferences/{confId}")
    public ServiceResponse getNotificationsByConference(@PathVariable String confId){
        notificationService.getNotifications(confId);
        return null;
    }


    @GetMapping("/notifications/conferences/{confId}/users/{userId}")
    public ServiceResponse getUserNotificationsByConference(@PathVariable String userId, @PathVariable String confId){
        notificationService.getNotifications(userId);
        return null;
    }

    @GetMapping("/notifications")
    public ServiceResponse getAllNotifications(){
        notificationService.getNotifications(null);
        return null;
    }

}
