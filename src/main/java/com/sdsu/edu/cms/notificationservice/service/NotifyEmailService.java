package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.notificationservice.exception.NotificationTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class NotifyEmailService implements INotify {

    @Autowired
    public JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean notifyTarget(Notify notification) {
        // todo : create a new notifcation table and update values accordingly.

        UUID uuid = UUID.randomUUID();
        String[] recipients = notification.getReceiver().stream().toArray(String[]::new);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setBcc(recipients);
        message.setSubject(notification.getSubject());
        String msg = (notification.getEmailMessage().isEmpty() || notification.getEmailMessage() == null) ?
                notification.getMessage() : notification.getEmailMessage();
        message.setText(msg);
        try {
            javaMailSender.send(message);
            logger.info("Successful sent email notification");
        }catch (Exception e){
            logger.error("Exception while sending email notification - id %s \n Exception : "+e.getMessage(), uuid.toString());
            String error = "Exception while sending email notification - nid :"+uuid.toString();
            throw new NotificationTransportException(error);

        }
        return true;
    }

}


