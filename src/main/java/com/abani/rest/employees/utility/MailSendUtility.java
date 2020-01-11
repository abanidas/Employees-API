package com.abani.rest.employees.utility;

import com.abani.rest.employees.model.MailUtil;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.concurrent.Executor;

public class MailSendUtility {

    public static void sendMail(MailUtil mail, String subject, String message, Executor executor, JavaMailSenderImpl mailSender){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    mailSender.setUsername(mail.getSender());
                    mailSender.setPassword(mail.getPassword());

                    SimpleMailMessage msg = new SimpleMailMessage();
                    msg.setTo(mail.getReceiver());
                    msg.setSubject(subject);
                    msg.setText(message);
                    mailSender.send(msg);
                }
                catch (MailException e){
                    System.err.println("Error : " + e.getMessage());
                }
            }
        });
    }
}
