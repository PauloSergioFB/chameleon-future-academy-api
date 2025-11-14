package br.com.fiap.chameleonfutureacademy.domainmodel;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_tag")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private @Getter @Setter Long tagId;

    @Column(name = "description", nullable = false, unique = true, length = 100)
    private @Getter @Setter String description;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private @Setter @Getter Set<Course> courses;

    @Override
    public int hashCode() {
        return Objects.hash(tagId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagId, tag.tagId);
    }

}
