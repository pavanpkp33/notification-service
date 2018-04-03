package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.notificationservice.exception.NotificationTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class NotifyEmailService implements INotify {

    @Autowired
    public JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean notifyTarget(Notify notification) {
        // todo : create a new notifcation table and update values accordingly. Consider creating a thread.
        if(notification.isIs_broadcast()){
            /*
                If it is a broadcast message, check for conference id, get all the email from DB
                Store it in DB as receiver : broadcast.
             */
        }else{
            /*
                Send a normal BCC email and store receiver as paper#
             */
        }

        UUID uuid = UUID.randomUUID();
        String[] recipients = notification.getReceiver().stream().toArray(String[]::new);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setBcc(recipients);
        message.setSubject(notification.getSubject());
        String msg = (notification.getEmail_message().isEmpty() || notification.getEmail_message() == null) ?
                notification.getMessage() : notification.getEmail_message();
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


