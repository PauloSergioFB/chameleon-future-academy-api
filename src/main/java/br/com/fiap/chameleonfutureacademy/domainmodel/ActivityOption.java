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
@Table(name = "cfa_activity_option")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_option_id")
    private @Getter @Setter Long activityOptionId;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private @Getter @Setter Activity activity;

    @Column(name = "label", nullable = false, length = 1)
    private @Getter @Setter String label;

    @Column(name = "description", nullable = false, length = 150)
    private @Getter @Setter String description;

    @Column(name = "is_correct", nullable = false)
    private @Getter @Setter Boolean isCorrect;

    @Override
    public int hashCode() {
        return Objects.hash(activityOptionId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        ActivityOption activityOption = (ActivityOption) o;
        return Objects.equals(activityOptionId, activityOption.activityOptionId);
    }

}