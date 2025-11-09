package pl.wsb.fitnesstracker.healthmetrics.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.wsb.fitnesstracker.user.api.User;

@Entity
@Table(name = "health_metrics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class HealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @Column(name = "heart_rate")
    private Integer heartRate;

    public HealthMetrics(User user, Double weight, Double height, Integer heartRate) {
        this.user = user;
        this.weight = weight;
        this.height = height;
        this.heartRate = heartRate;
    }
}
