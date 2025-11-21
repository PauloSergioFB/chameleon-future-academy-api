package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Procedures;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;

public interface ProcedureRepository extends JpaRepository<User, Long> {

    @Procedure(procedureName = "prc_insert_user")
    void prcInsertUser(
            String name,
            String email,
            String hashedPassword,
            String biography,
            String whatsapp,
            String profileImage);

    @Procedure(procedureName = "prc_insert_course")
    void prcInsertCourse(
            String title,
            String description,
            String author,
            String thumbnailUrl);

    @Procedure(procedureName = "prc_insert_tag")
    void prcInsertTag(String description);

    @Procedure(procedureName = "prc_insert_content")
    void prcInsertContent(
            Long courseId,
            String type,
            Integer position);

    @Procedure(procedureName = "prc_insert_lesson")
    void prcInsertLesson(
            Long contentId,
            String title,
            String body);

    @Procedure(procedureName = "prc_insert_activity")
    void prcInsertActivity(
            Long contentId,
            String title,
            String body,
            String explanation);

    @Procedure(procedureName = "prc_insert_badge")
    void prcInsertBadge(
            Long courseId,
            String title,
            String iconUrl);

    @Procedure(procedureName = "prc_register_user_in_course")
    void prcRegisterUserInCourse(
            Long userId,
            Long courseId,
            Integer progress,
            String status,
            LocalDateTime startedAt,
            LocalDateTime completedAt);

    @Procedure(procedureName = "prc_add_tag_for_course")
    void prcAddTagForCourse(
            Long courseId,
            Long tagId);

    @Procedure(procedureName = "prc_add_option_for_activity")
    void prcAddOptionForActivity(
            Long activityId,
            String label,
            String description,
            Integer isCorrect);

    @Procedure(procedureName = "prc_add_badge_for_user")
    void prcAddBadgeForUser(
            Long userId,
            Long badgeId,
            LocalDateTime earnedAt);

}
