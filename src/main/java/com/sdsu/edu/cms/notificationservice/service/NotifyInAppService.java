package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;
import org.springframework.stereotype.Service;

@Service
public class NotifyInAppService implements INotify{

    @Override
    public boolean notifyTarget(Notify notification) {
        //todo : create a new notification table with all fields. Consider creating a thread.
        /*
            If it is a broadcast message, store receiver as broadcast. Else, store individually.

         */
        return false;
    }


}
