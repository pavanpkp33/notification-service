package com.sdsu.edu.cms.notificationservice.controllers;


import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", produces = {APPLICATION_JSON_VALUE})
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    //Checks for IN-APP notifications only. Notifications can be status update of papers or any communications.
    @GetMapping("/notifications/conferences/{confId}")
    public ServiceResponse getNotificationsByConference(@PathVariable String confId){
        return notificationService.getNotifications(confId, null);

    }


    @PostMapping("/notifications/conferences/{confId}/users")
    public ServiceResponse getUserNotificationsByConference(@RequestBody Map<String, String> email, @PathVariable String confId){
        return notificationService.getNotifications(confId, email.get("email"));

    }

    @GetMapping("/notifications")
    public ServiceResponse getAllNotifications(){
        return  notificationService.getNotifications(null, null);

    }

    @PatchMapping(value = "/notifications/{notificationId}", consumes = {APPLICATION_JSON_VALUE})
    public ServiceResponse updateNotification(@PathVariable String notificationId, @RequestBody Map<String, String> payLoad){

        String hasSeen = payLoad.get("has_seen");

        return notificationService.updateNotification(notificationId, hasSeen);

    }
}
