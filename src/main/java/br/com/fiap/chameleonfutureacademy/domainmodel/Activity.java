package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_activity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private @Getter @Setter Long activityId;

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private @Getter @Setter Content content;

    @Column(name = "title", nullable = false, length = 100)
    private @Getter @Setter String title;

    @Lob
    @Column(name = "body", nullable = false)
    private @Getter @Setter String body;

    @Column(name = "explanation", nullable = false, length = 500)
    private @Getter @Setter String explanation;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter List<ActivityOption> activityOptions;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Activity a = (Activity) o;
        return Objects.equals(activityId, a.activityId);
    }

}
