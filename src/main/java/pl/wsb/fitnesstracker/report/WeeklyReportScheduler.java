package pl.wsb.fitnesstracker.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task for generating weekly training reports.
 * Runs every Monday at 8:00 AM.
 */
@Component
@Slf4j
public class WeeklyReportScheduler {

    private final WeeklyTrainingReportService reportService;

    public WeeklyReportScheduler(WeeklyTrainingReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Generates weekly training report every 30 seconds (for testing).
     * Cron expression: "30 * * * * *" means:
     * - every 30 seconds
     * Original: "0 0 8 * * MON" (Mondays at 8 AM)
     */
    @Scheduled(cron = "*/30 * * * * *")
    public void generateWeeklyReport() {
        log.info("Rozpoczęcie generowania tygodniowego raportu treningów...");
        reportService.generateWeeklyReport();
        log.info("Zakończono generowanie tygodniowego raportu treningów.");
    }

    /**
     * For testing purposes - generates report every minute.
     * Uncomment to test the scheduled task without waiting for Monday.
     */
    // @Scheduled(cron = "0 * * * * *")
    // public void generateWeeklyReportTest() {
    //     log.info("TEST: Rozpoczęcie generowania tygodniowego raportu treningów...");
    //     reportService.generateWeeklyReport();
    //     log.info("TEST: Zakończono generowanie tygodniowego raportu treningów.");
    // }
}
