package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_course")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private @Getter @Setter Long courseId;

    @Column(name = "title", nullable = false, unique = true, length = 100)
    private @Getter @Setter String title;

    @Column(name = "description", length = 255)
    private @Getter @Setter String description;

    @Column(name = "author", length = 100)
    private @Getter @Setter String author;

    @Column(name = "thumbnail_url", length = 100)
    private @Getter @Setter String thumbnailUrl;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter List<Enrollment> enrollments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cfa_course_tag", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private @Getter @Setter Set<Tag> tags;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("position ASC")
    private @Getter List<Content> contents;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter List<Badge> badges;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

}
