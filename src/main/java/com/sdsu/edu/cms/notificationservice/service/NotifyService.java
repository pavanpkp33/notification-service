package com.sdsu.edu.cms.notificationservice.service;


import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.notificationservice.utils.NotifyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {


    INotify notify;

    @Autowired
    NotifyFactory notifyFactory;

    public boolean[] sendNotifications(Notify request){

        int requestTypes = request.getMethod().size();
        boolean[] status = new boolean[requestTypes];
        int idx = 0;
        while(idx < requestTypes){
            notify = notifyFactory.getClass(request.getMethod().get(idx));
            boolean flag = notify.notifyTarget(request);
            status[idx] = flag;
            idx++;
        }

        return status;
    }

}


