package com.sdsu.edu.cms.notificationservice.service;


import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.notificationservice.utils.NotifyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {


    INotify notify;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NotifyFactory notifyFactory;

    public boolean[] sendNotifications(Notify request){
        int count = 0;
        int requestTypes = request.getMethod().size();
        boolean[] status = new boolean[requestTypes];
        int idx = 0;
        while(idx < requestTypes){
            notify = notifyFactory.getClass(request.getMethod().get(idx));
            boolean flag = notify.notifyTarget(request);
            if(flag == false) count ++;

            status[idx] = flag;
            idx++;
        }
        logger.error("Failed notifications count is "+ count);
        return status;
    }

}


