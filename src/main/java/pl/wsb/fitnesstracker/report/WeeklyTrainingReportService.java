package pl.wsb.fitnesstracker.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeeklyTrainingReportService {

    private final UserProvider userProvider;
    private final TrainingRepository trainingRepository;

    public WeeklyTrainingReportService(UserProvider userProvider, TrainingRepository trainingRepository) {
        this.userProvider = userProvider;
        this.trainingRepository = trainingRepository;
    }

    public void generateWeeklyReport() {
        log.info("=== Generowanie tygodniowego raportu treningów ===");

        LocalDate now = LocalDate.now();
        LocalDate weekStart = now.minus(7, ChronoUnit.DAYS);
        Date weekStartDate = Date.from(weekStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nowDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<User> allUsers = userProvider.findAllUsers();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("Raport dla okresu: {} - {}", weekStart, now);
        log.info("Liczba użytkowników: {}", allUsers.size());
        log.info("");

        for (User user : allUsers) {
            List<Training> weeklyTrainings = trainingRepository.findAll().stream()
                    .filter(training -> training.getUser().getId().equals(user.getId()))
                    .filter(training -> training.getStartTime().after(weekStartDate)
                    && training.getStartTime().before(nowDate))
                    .collect(Collectors.toList());

            log.info("--- Użytkownik: {} {} ({}) ---", user.getFirstName(), user.getLastName(), user.getEmail());
            log.info("Liczba treningów w tym tygodniu: {}", weeklyTrainings.size());

            if (!weeklyTrainings.isEmpty()) {
                double totalDistance = 0;
                double totalSpeed = 0;

                for (Training training : weeklyTrainings) {
                    log.info("  * {} - {} | {} | Dystans: {} km | Prędkość: {} km/h",
                            sdf.format(training.getStartTime()),
                            sdf.format(training.getEndTime()),
                            training.getActivityType(),
                            String.format("%.2f", training.getDistance()),
                            String.format("%.2f", training.getAverageSpeed()));

                    totalDistance += training.getDistance();
                    totalSpeed += training.getAverageSpeed();
                }

                log.info("  Łączny dystans: {} km", String.format("%.2f", totalDistance));
                log.info("  Średnia prędkość: {} km/h", String.format("%.2f", totalSpeed / weeklyTrainings.size()));
            } else {
                log.info("  Brak treningów w tym tygodniu.");
            }

            log.info("");
        }

        log.info("=== Koniec raportu tygodniowego ===");
    }

    public String generateWeeklyReportForUser(User user) {
        LocalDate now = LocalDate.now();
        LocalDate weekStart = now.minus(7, ChronoUnit.DAYS);
        Date weekStartDate = Date.from(weekStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nowDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Training> allTrainings = trainingRepository.findAll();
        long totalTrainings = allTrainings.stream()
                .filter(training -> training.getUser().getId().equals(user.getId()))
                .count();

        List<Training> weeklyTrainings = allTrainings.stream()
                .filter(training -> training.getUser().getId().equals(user.getId()))
                .filter(training -> training.getStartTime().after(weekStartDate)
                && training.getStartTime().before(nowDate))
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        report.append("Witaj ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("!\n\n");
        report.append("Oto podsumowanie Twoich treningów:\n");
        report.append("Łączna liczba treningów: ").append(totalTrainings).append("\n");
        report.append("Treningi w tym tygodniu (").append(weekStart).append(" - ").append(now).append("): ")
                .append(weeklyTrainings.size()).append("\n\n");

        if (!weeklyTrainings.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            double totalDistance = 0;

            report.append("Szczegóły treningów tego tygodnia:\n");
            for (Training training : weeklyTrainings) {
                report.append("- ").append(sdf.format(training.getStartTime()))
                        .append(" | ").append(training.getActivityType())
                        .append(" | Dystans: ").append(String.format("%.2f", training.getDistance())).append(" km")
                        .append(" | Prędkość: ").append(String.format("%.2f", training.getAverageSpeed())).append(" km/h\n");
                totalDistance += training.getDistance();
            }

            report.append("\nŁączny dystans w tym tygodniu: ").append(String.format("%.2f", totalDistance)).append(" km\n");
        }

        report.append("\nTrzymaj formę!\n");
        report.append("Zespół FitnessTracker");

        return report.toString();
    }
}
