package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_enrollment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private @Getter @Setter Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private @Getter @Setter User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private @Getter @Setter Course course;

    @Column(name = "progress", nullable = false)
    @Builder.Default
    private @Getter @Setter Integer progress = 0;

    @Column(name = "status", nullable = false, length = 15)
    private @Getter @Setter String status;

    @Column(name = "started_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private @Getter @Setter LocalDateTime completedAt;

    @PrePersist
    public void prePersist() {
        if (startedAt == null)
            startedAt = LocalDateTime.now();

        if (progress == null)
            progress = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Enrollment enrollment = (Enrollment) o;
        return Objects.equals(enrollmentId, enrollment.enrollmentId);
    }

}
