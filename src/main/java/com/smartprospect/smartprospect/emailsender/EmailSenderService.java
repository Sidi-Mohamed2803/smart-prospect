package com.smartprospect.smartprospect.emailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service @RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public void sendEmail(String fromEmail, String toEmail, String subject, String body, MultipartFile file) {

        if(!fromEmail.contains("@") && !fromEmail.contains("."))
            throw new IllegalStateException("Votre addresse mail n'est pas valide.");
        if(!toEmail.contains("@") && !toEmail.contains("."))
            throw new IllegalStateException("L'addresse mail de destination n'est pas valide.");
        if(subject.equals("") || subject==null)
            throw new IllegalStateException("Veuillez donner un objet à votre message.");
        if(!fromEmail.contains("@") && !fromEmail.contains("."))
            throw new IllegalStateException("Veuillez renseigner le message à envoyer.");
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            String header = "Envoyé depuis la plateforme SmartProspect :\n\n";
//            helper.setFrom(fromEmail);
//            helper.setTo(toEmail);
//            helper.setSubject(subject);
//            helper.setText(header + body);
//            if (file!=null && !file.isEmpty()) {
//                helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
//            }
//            mailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        SimpleMailMessage message = new SimpleMailMessage();
        String header = "Envoyé depuis la plateforme SmartProspect :\n\n";
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(header + body);
        message.setSubject(subject);


        mailSender.send(message);

    }

    public void sendEmailToAdmin(String fromEmail, String body) {
        if(!fromEmail.contains("@") && !fromEmail.contains("."))
            throw new IllegalStateException("Votre addresse mail n'est pas valide.");

        SimpleMailMessage message = new SimpleMailMessage();
        String header = "Envoyé depuis la plateforme SmartProspect :\n\n";
        message.setFrom(fromEmail);
        message.setTo("contact@smartways-innovation.com");
        message.setText(header + body);
        message.setSubject("Avis utilisateur de la plate-forme SmartProspect");

        mailSender.send(message);
    }
}
