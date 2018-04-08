package com.sdsu.edu.cms.notificationservice.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsu.edu.cms.common.models.notification.NotifyDBModel;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.notificationservice.exception.NotificationNotFoundException;
import com.sdsu.edu.cms.notificationservice.proxy.DataServiceProxy;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class NotificationService {

    @Autowired
    DataServiceProxy dataServiceProxy;


    public ServiceResponse getNotifications(String conferenceId, String id){
        Map<String, String> map = new HashMap<>();
        List<Object> res;
        List<NotifyDBModel> result = null;
        if(id == null && conferenceId == null ){
              res = dataServiceProxy.getNotifications(map).getData();
        }else if(id == null){
            map.put("cid", conferenceId);
             res =  dataServiceProxy.getNotifications(map).getData();
        }else{
            map.put("id", id);
            map.put("cid", conferenceId);
            res =   dataServiceProxy.getNotifications(map).getData();
        }
        if(res.size() > 0){
            Gson gson = new Gson();
            Type type = new TypeToken<List<NotifyDBModel>>() {}.getType();
            result = gson.fromJson(res.get(0).toString(), type);
        }
        List<Object> defResult = new ArrayList<>();
        for(NotifyDBModel n : result){
            defResult.add(n);
        }
        return  new ServiceResponse(defResult, "Notifications queried successfully");
    }

    public ServiceResponse updateNotification(String notificationId, String hasSeen){
        Map<String, String> map = new HashMap<>();
        map.put("id", notificationId);
        map.put("field", "has_seen");
        map.put("value", hasSeen);
        try {
            dataServiceProxy.updateNotifications(map);
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                throw new NotificationNotFoundException("given");
            }
            throw e;
        }


        return new ServiceResponse(Arrays.asList(true), "Notification updated successfully");
    }
}
