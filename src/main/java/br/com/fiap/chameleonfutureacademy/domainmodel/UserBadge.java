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
@Table(name = "cfa_user_badge")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private @Getter @Setter Long userBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private @Getter @Setter User user;

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private @Getter @Setter Badge badge;

    @Column(name = "earned_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime earnedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (earnedAt == null) {
            earnedAt = LocalDateTime.now();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(userBadgeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        UserBadge userBadge = (UserBadge) o;
        return Objects.equals(userBadgeId, userBadge.userBadgeId);
    }

}
