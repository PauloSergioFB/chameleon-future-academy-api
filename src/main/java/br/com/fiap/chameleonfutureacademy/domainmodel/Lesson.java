package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_lesson")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private @Getter @Setter Long lessonId;

    @OneToOne
    @JoinColumn(name = "content_id", nullable = false, unique = true)
    private @Getter @Setter Content content;

    @Column(name = "title", nullable = false, length = 100)
    private @Getter @Setter String title;

    @Lob
    @Column(name = "body", nullable = false)
    private @Getter @Setter String body;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId);
    }

}
