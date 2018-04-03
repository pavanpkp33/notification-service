package com.sdsu.edu.cms.notificationservice.utils;


import com.sdsu.edu.cms.common.utils.Constants;
import com.sdsu.edu.cms.notificationservice.service.INotify;
import com.sdsu.edu.cms.notificationservice.service.NotifyEmailService;
import com.sdsu.edu.cms.notificationservice.service.NotifyInAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotifyFactory {
    @Autowired
    NotifyEmailService notifyEmailService;

    @Autowired
    NotifyInAppService notifyInAppService;

    public INotify getClass(String type){
        if(type == null || type.isEmpty()) return null;

        switch (type){

            case Constants.SCHEME_EMAIL : return notifyEmailService;
            case Constants.SCHEME_INAPP : return notifyInAppService;

        }

        return null;
    }
}
