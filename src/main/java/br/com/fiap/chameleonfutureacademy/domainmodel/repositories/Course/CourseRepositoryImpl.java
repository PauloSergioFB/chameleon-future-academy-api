package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.QCourse;
import br.com.fiap.chameleonfutureacademy.domainmodel.QTag;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseListRow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CourseRepositoryImpl implements CourseRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    public CourseRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<CourseListRow> findFiltered(
            String title,
            String author,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) {

        QCourse qCourse = QCourse.course;
        QTag qTag = QTag.tag;

        BooleanBuilder filters = new BooleanBuilder();

        if (title != null)
            filters.and(qCourse.title.containsIgnoreCase(title));

        if (author != null)
            filters.and(qCourse.author.containsIgnoreCase(author));

        if (tag != null)
            filters.and(qTag.description.equalsIgnoreCase(tag));

        OrderSpecifier<?> order = buildOrder(orderBy, direction);

        return executePagedQuery(filters, page, size, order);
    }

    @Override
    public Page<CourseListRow> findSearch(
            String search,
            String tag,
            int page,
            int size,
            String orderBy,
            String direction) {

        QCourse qCourse = QCourse.course;
        QTag qTag = QTag.tag;

        BooleanBuilder filters = new BooleanBuilder();

        if (search != null) {
            BooleanBuilder searchGroup = new BooleanBuilder();

            searchGroup.or(qCourse.title.containsIgnoreCase(search));
            searchGroup.or(qCourse.author.containsIgnoreCase(search));
            searchGroup.or(qTag.description.containsIgnoreCase(search));

            filters.and(searchGroup);
        }

        if (tag != null)
            filters.and(qTag.description.equalsIgnoreCase(tag));

        OrderSpecifier<?> order = buildOrder(orderBy, direction);

        return executePagedQuery(filters, page, size, order);
    }

    private Page<CourseListRow> executePagedQuery(
            BooleanBuilder filters,
            int page,
            int size,
            OrderSpecifier<?> order) {

        QCourse qCourse = QCourse.course;
        QTag qTag = QTag.tag;

        Pageable pageable = PageRequest.of(page, size);

        List<Long> ids = queryFactory
                .select(qCourse.courseId)
                .distinct()
                .from(qCourse)
                .leftJoin(qCourse.tags, qTag)
                .where(filters)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CourseListRow> rows = queryFactory
                .select(Projections.constructor(
                        CourseListRow.class,
                        qCourse.courseId,
                        qCourse.title,
                        qCourse.author,
                        qCourse.thumbnailUrl,
                        qCourse.createdAt,
                        qTag.description))
                .from(qCourse)
                .leftJoin(qCourse.tags, qTag)
                .where(qCourse.courseId.in(ids))
                .orderBy(order)
                .fetch();

        Long total = countFiltered(filters);

        return new PageImpl<>(rows, pageable, total);
    }

    private OrderSpecifier<?> buildOrder(String orderBy, String direction) {
        Order order = direction.equalsIgnoreCase("desc") ? Order.DESC : Order.ASC;

        PathBuilder<Course> path = new PathBuilder<>(Course.class, "course");

        return new OrderSpecifier<>(
                order,
                path.getComparable(orderBy, Comparable.class));
    }

    private long countFiltered(BooleanBuilder filters) {
        JPAQuery<Course> query = new JPAQuery<>(entityManager);

        QCourse qCourse = QCourse.course;
        QTag qTag = QTag.tag;

        JPAQuery<Long> countQuery = query
                .select(qCourse.courseId.countDistinct())
                .from(qCourse)
                .leftJoin(qCourse.tags, qTag)
                .where(filters);

        Long total = countQuery.fetchOne();
        return total != null ? total : 0;
    }

}
