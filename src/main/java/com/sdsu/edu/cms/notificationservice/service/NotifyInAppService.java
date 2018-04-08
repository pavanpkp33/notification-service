package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.common.models.notification.NotifyDBModel;
import com.sdsu.edu.cms.common.utils.Constants;
import com.sdsu.edu.cms.notificationservice.proxy.DataServiceProxy;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotifyInAppService implements INotify{

    @Autowired
    public DataServiceProxy dataServiceProxy;
    @Override
    public boolean notifyTarget(Notify notification) {
        //todo : create a new notification table with all fields. Consider creating a thread.

        NotifyDBModel model;
        List<NotifyDBModel> notificationList = new ArrayList<>();
        /*
            If it is a broadcast message, store receiver as broadcast. Else, store individually.

         */
        for(int i=0; i<notification.getReceiver().size(); i++){
            String msg = (notification.getMessage().isEmpty() || notification.getMessage() == null)? notification.getEmail_message() : notification.getMessage();
            model = new NotifyDBModel();
            model.setConference_id(notification.getConference_id());
            model.setCreated_on(notification.getCreated_on());
            model.setHas_seen("N");
            model.setReceiver(notification.getReceiver().get(i));
            model.setIs_broadcast(notification.getIs_broadcast());
            model.setPriority(notification.getPriority());
            model.setSender_name(notification.getSender_name());
            model.setSender_id(notification.getSender_id());
            model.setMessage(msg);
            model.setNotification_id(UUID.randomUUID().toString());
            model.setSubject(notification.getSubject());
            model.setIs_valid("Y");
            model.setMethod(Constants.SCHEME_INAPP);

            notificationList.add(model);

        }
        try {
            dataServiceProxy.saveNotifications(notificationList);
            return true;
        }catch (FeignException e){
            e.printStackTrace();
            return false;
        }

    }


}
