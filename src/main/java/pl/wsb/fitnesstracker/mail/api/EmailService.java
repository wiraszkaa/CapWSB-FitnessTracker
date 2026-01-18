package pl.wsb.fitnesstracker.mail.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.mail.host")
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@fitnesstracker.com}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("Email wysłany do: {}", to);
        } catch (Exception e) {
            log.error("Błąd podczas wysyłania emaila do {}: {}", to, e.getMessage());
        }
    }

    public void sendWeeklyReport(String to, String userName, String reportContent) {
        String subject = "FitnessTracker - Tygodniowy raport treningów";
        sendEmail(to, subject, reportContent);
    }
}
