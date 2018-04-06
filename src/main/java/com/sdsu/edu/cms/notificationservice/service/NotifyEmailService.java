package com.sdsu.edu.cms.notificationservice.service;

import com.sdsu.edu.cms.common.models.notification.Notify;
import com.sdsu.edu.cms.common.models.notification.NotifyDBModel;
import com.sdsu.edu.cms.common.utils.Constants;
import com.sdsu.edu.cms.notificationservice.exception.NotificationTransportException;
import com.sdsu.edu.cms.notificationservice.proxy.DataServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.UUID;


@Service
public class NotifyEmailService implements INotify {

    @Autowired
    public JavaMailSender javaMailSender;
    @Autowired
    public DataServiceProxy dataServiceProxy;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean notifyTarget(Notify notification) {
        // todo : create a new notifcation table and update values accordingly. Consider creating a thread.
        NotifyDBModel notifyDBModel = new NotifyDBModel();
        UUID uuid = UUID.randomUUID();
        String msg = (notification.getEmail_message().isEmpty() || notification.getEmail_message() == null) ?
                notification.getMessage() : notification.getEmail_message();
        if(notification.getIs_broadcast().equals("Y")){
            /*
                If it is a broadcast message, check for conference id, get all the email from DB
                Store it in DB as receiver : broadcast.
             */

            notifyDBModel.setPriority(Constants.NotifyMethod.ANNOUNCEMENT.toString());
            notifyDBModel.setReceiver("BROADCAST");


        }else{
            /*
                Send a normal BCC email and store receiver as paper#
             */
            notifyDBModel.setReceiver(notification.getConference_id());
            notifyDBModel.setPriority(Constants.NotifyMethod.MESSAGE.toString());

        }
        notifyDBModel.setIs_broadcast(notification.getIs_broadcast());
        notifyDBModel.setMethod(Constants.SCHEME_EMAIL);
        notifyDBModel.setNotification_id(uuid.toString());
        notifyDBModel.setCreated_on(notification.getCreated_on());
        notifyDBModel.setHas_seen("Y");
        notifyDBModel.setIs_valid("Y");
        notifyDBModel.setSubject(notification.getSubject());
        notifyDBModel.setSender_id(notification.getSender_id());
        notifyDBModel.setSender_name(notification.getSender_name());
        notifyDBModel.setConference_id(notification.getConference_id());
        notifyDBModel.setMessage(msg);
        sendEmail(notification, uuid, msg);
        dataServiceProxy.saveNotifications(Arrays.asList(notifyDBModel));

        return true;
    }


    @Async
    public void sendEmail(Notify notification, UUID uuid, String msg){
        String[] recipients = notification.getReceiver().stream().toArray(String[]::new);


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(msg, "text/html");
            helper.setBcc(recipients);
            helper.setSubject(notification.getSubject());
            javaMailSender.send(mimeMessage);
            logger.info("Successful sent email notification");
        } catch (Exception e) {
            logger.error("Exception while sending email notification - id %s \n Exception : "+e.getMessage(), uuid.toString());
            String error = "Exception while sending email notification - nid :"+uuid.toString();
            throw new NotificationTransportException(error);
        }
    }

}


