package com.smartprospect.smartprospect.emailsender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public void sendEmail(String fromEmail, String toEmail, String subject, String body) {
        if(!fromEmail.contains("@") && !fromEmail.contains("."))
            throw new IllegalStateException("Votre addresse mail n'est pas valide.");
        if(!toEmail.contains("@") && !toEmail.contains("."))
            throw new IllegalStateException("L'addresse mail de destination n'est pas valide.");
        if(subject.equals("") || subject==null)
            throw new IllegalStateException("Veuillez donner un objet à votre message.");
        if(!fromEmail.contains("@") && !fromEmail.contains("."))
            throw new IllegalStateException("Veuillez renseigner le message à envoyer.");
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
        message.setSubject("Avis client de la plate-forme SmartProspect");

        mailSender.send(message);
    }
}
