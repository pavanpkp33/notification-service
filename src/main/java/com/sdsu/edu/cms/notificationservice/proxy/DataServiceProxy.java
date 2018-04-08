package com.sdsu.edu.cms.notificationservice.proxy;


import com.sdsu.edu.cms.common.models.notification.NotifyDBModel;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "data-service")
@RibbonClient(name = "data-service")
public interface DataServiceProxy {

    @PostMapping("/api/v1/notifications")
    ServiceResponse saveNotifications(@RequestBody List<NotifyDBModel> payLoad);

    @PostMapping("/api/v1/notifications/query")
    ServiceResponse getNotifications(@RequestParam Map<String, String> map);

    @PostMapping("/api/v1/notifications/update")
    ServiceResponse updateNotifications(@RequestParam Map<String, String> map);
}
