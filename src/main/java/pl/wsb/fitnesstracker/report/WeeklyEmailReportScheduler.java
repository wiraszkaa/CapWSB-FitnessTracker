package pl.wsb.fitnesstracker.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.wsb.fitnesstracker.mail.api.EmailService;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.List;

/**
 * Scheduled task for generating and sending weekly training reports via email.
 * Runs every Monday at 8:00 AM. Only active when EmailService bean is available
 * (requires spring.mail.host property).
 */
@Component
@Slf4j
@ConditionalOnBean(EmailService.class)
public class WeeklyEmailReportScheduler {

    private final WeeklyTrainingReportService reportService;
    private final EmailService emailService;
    private final UserProvider userProvider;

    @Autowired
    public WeeklyEmailReportScheduler(WeeklyTrainingReportService reportService,
            EmailService emailService,
            UserProvider userProvider) {
        this.reportService = reportService;
        this.emailService = emailService;
        this.userProvider = userProvider;
    }

    /**
     * Generates and sends weekly training reports via email every 30 seconds
     * (for testing). Cron expression: "30 * * * * *" means: - every 30 seconds
     * Original: "0 0 8 * * MON" (Mondays at 8 AM)
     */
    @Scheduled(cron = "*/30 * * * * *")
    public void sendWeeklyReports() {
        log.info("Rozpoczęcie wysyłania tygodniowych raportów treningowych...");

        List<User> allUsers = userProvider.findAllUsers();
        log.info("Wysyłanie raportów do {} użytkowników", allUsers.size());

        for (User user : allUsers) {
            try {
                String reportContent = reportService.generateWeeklyReportForUser(user);
                emailService.sendWeeklyReport(user.getEmail(), user.getFirstName() + " " + user.getLastName(), reportContent);
                log.info("Raport wysłany do: {} ({})", user.getFirstName() + " " + user.getLastName(), user.getEmail());
            } catch (Exception e) {
                log.error("Błąd podczas wysyłania raportu do użytkownika {}: {}", user.getEmail(), e.getMessage());
            }
        }

        log.info("Zakończono wysyłanie tygodniowych raportów treningowych.");
    }

    /**
     * For testing purposes - sends reports every 5 minutes. Uncomment to test
     * email sending without waiting for Monday.
     */
    // @Scheduled(cron = "0 */5 * * * *")
    // public void sendWeeklyReportsTest() {
    //     log.info("TEST: Rozpoczęcie wysyłania tygodniowych raportów treningowych...");
    //     sendWeeklyReports();
    // }
}
