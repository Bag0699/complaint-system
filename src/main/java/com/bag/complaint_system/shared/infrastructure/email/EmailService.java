package com.bag.complaint_system.shared.infrastructure.email;

//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {

//    @Value("${spring.sendgrid.api-key}")
//    private String sendGridApiKey;
//
//    @Value("${spring.sendgrid.from.email}")
//    private String fromEmail;
//
//    public void sendEmail(String toEmail, String subject, String body) {
//        try {
//            Email from = new Email(fromEmail);
//            Email to = new Email(toEmail);
//            Content content = new Content("text/plain", body);
//            Mail mail = new Mail(from, subject, to, content);
//
//            SendGrid sg = new SendGrid(sendGridApiKey);
//            Request request = new Request();
//
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//
//            Response response = sg.api(request);
//
//            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
//                log.info("Email sent successfully to: {}", toEmail);
//            } else {
//                log.error("Failed to send email. Status: {}", response.getStatusCode());
//            }
//
//        } catch (IOException ex) {
//            log.error("Error sending email to {}: {}", toEmail, ex.getMessage());
//            throw new RuntimeException("Failed to send email", ex);
//        }
//    }

//    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
//        try {
//            Email from = new Email(fromEmail);
//            Email to = new Email(toEmail);
//            Content content = new Content("text/html", htmlBody);
//            Mail mail = new Mail(from, subject, to, content);
//
//            SendGrid sg = new SendGrid(sendGridApiKey);
//            Request request = new Request();
//
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//
//            Response response = sg.api(request);
//
//            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
//                log.info("HTML email sent successfully to: {}", toEmail);
//            } else {
//                log.error("Failed to send HTML email. Status: {}", response.getStatusCode());
//            }
//
//        } catch (IOException ex) {
//            log.error("Error sending HTML email to {}: {}", toEmail, ex.getMessage());
//            throw new RuntimeException("Failed to send HTML email", ex);
//        }
//    }
}
