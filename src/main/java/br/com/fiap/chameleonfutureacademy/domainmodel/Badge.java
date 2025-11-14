package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_badge")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private @Getter @Setter Long badgeId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private @Getter @Setter Course course;

    @Column(name = "title", nullable = false, unique = true, length = 100)
    private @Getter @Setter String title;

    @Column(name = "icon_url", length = 100)
    private @Getter @Setter String iconUrl;

    @Override
    public int hashCode() {
        return Objects.hash(badgeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Badge badge = (Badge) o;
        return Objects.equals(badgeId, badge.badgeId);
    }

}
