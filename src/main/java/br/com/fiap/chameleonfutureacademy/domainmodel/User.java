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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfa_user_account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private @Getter @Setter Long userId;

    @Column(name = "full_name", nullable = false, length = 100)
    private @Getter @Setter String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private @Getter @Setter String email;

    @Column(name = "hashed_password", nullable = false, length = 200)
    private @Getter @Setter String hashedPassword;

    @Column(name = "biography", length = 500)
    private @Getter @Setter String biography;

    @Column(name = "whatsapp", length = 15)
    private @Getter @Setter String whatsapp;

    @Column(name = "profile_image", length = 100)
    private @Getter @Setter String profileImage;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private @Getter @Setter LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter List<Enrollment> enrollments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private @Getter List<UserBadge> badges;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

}
