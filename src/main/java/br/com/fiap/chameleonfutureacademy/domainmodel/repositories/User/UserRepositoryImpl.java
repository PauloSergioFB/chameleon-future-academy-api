package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.User;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.fiap.chameleonfutureacademy.domainmodel.QBadge;
import br.com.fiap.chameleonfutureacademy.domainmodel.QCourse;
import br.com.fiap.chameleonfutureacademy.domainmodel.QEnrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.QUser;
import br.com.fiap.chameleonfutureacademy.domainmodel.QUserBadge;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.User.UserProfileRow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<UserProfileRow> findProfile(Long userId) {

        QUser qUser = QUser.user;
        QEnrollment qEnrollment = QEnrollment.enrollment;
        QCourse qCourse = QCourse.course;
        QUserBadge qUserBadge = QUserBadge.userBadge;
        QBadge qBadge = QBadge.badge;

        List<UserProfileRow> rows = queryFactory
                .select(Projections.constructor(
                        UserProfileRow.class,
                        qUser.userId,
                        qUser.fullName,
                        qUser.email,
                        qUser.biography,
                        qUser.whatsapp,
                        qUser.profileImage,
                        qUser.createdAt,

                        qEnrollment.enrollmentId,
                        qEnrollment.progress,
                        qEnrollment.status,
                        qEnrollment.startedAt,
                        qEnrollment.completedAt,

                        qCourse.courseId,
                        qCourse.title,
                        qCourse.description,
                        qCourse.author,
                        qCourse.thumbnailUrl,
                        qCourse.createdAt,

                        qBadge.badgeId,
                        qBadge.title,
                        qBadge.iconUrl))
                .from(qUser)
                .leftJoin(qUser.enrollments, qEnrollment)
                .leftJoin(qEnrollment.course, qCourse)
                .leftJoin(qUser.badges, qUserBadge)
                .leftJoin(qUserBadge.badge, qBadge)
                .where(qUser.userId.eq(userId))
                .fetch();

        return rows;
    }

}
