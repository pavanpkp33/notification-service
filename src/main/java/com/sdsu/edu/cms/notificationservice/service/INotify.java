package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;

public interface INotify {

    boolean notifyTarget(Notify notification);
}
