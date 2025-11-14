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
@Table(name = "cfa_content")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private @Getter @Setter Long contentId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private @Getter @Setter Course course;

    @Column(name = "type", nullable = false, length = 20)
    private @Getter @Setter String type;

    @Column(name = "position", nullable = false)
    private @Getter @Setter Integer position;

    @Override
    public int hashCode() {
        return Objects.hash(contentId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Content content = (Content) o;
        return Objects.equals(contentId, content.contentId);
    }

}
