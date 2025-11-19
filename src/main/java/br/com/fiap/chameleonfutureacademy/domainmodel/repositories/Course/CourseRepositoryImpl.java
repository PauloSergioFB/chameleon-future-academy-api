package br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseTagRow;
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
    public Page<CourseTagRow> findFiltered(
            String title,
            String author,
            String tag,
            Pageable pageable) {

        QCourse qCourse = QCourse.course;
        QTag qTag = QTag.tag;

        BooleanBuilder filters = new BooleanBuilder();

        if (title != null)
            filters.and(qCourse.title.containsIgnoreCase(title));

        if (author != null)
            filters.and(qCourse.author.containsIgnoreCase(author));

        if (tag != null)
            filters.and(qTag.description.equalsIgnoreCase(tag));

        OrderSpecifier<?> order = buildOrder(pageable);

        return executePagedQuery(filters, pageable.getPageNumber(), pageable.getPageSize(), order);
    }

    @Override
    public Page<CourseTagRow> findSearch(
            String search,
            String tag,
            Pageable pageable) {

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

        OrderSpecifier<?> order = buildOrder(pageable);

        return executePagedQuery(filters, pageable.getPageNumber(), pageable.getPageSize(), order);
    }

    private OrderSpecifier<?> buildOrder(Pageable pageable) {

        Sort.Order sortOrder = pageable.getSort().stream()
                .findFirst()
                .orElse(Sort.Order.asc("courseId"));

        String orderBy = sortOrder.getProperty();

        Order order = sortOrder.isAscending() ? Order.ASC : Order.DESC;

        PathBuilder<Course> path = new PathBuilder<>(Course.class, "course");

        return new OrderSpecifier<>(
                order,
                path.getComparable(orderBy, Comparable.class));
    }

    private Page<CourseTagRow> executePagedQuery(
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

        List<CourseTagRow> rows = queryFactory
                .select(Projections.constructor(
                        CourseTagRow.class,
                        qCourse.courseId,
                        qCourse.title,
                        qCourse.author,
                        qCourse.thumbnailUrl,
                        qCourse.createdAt,
                        qTag.tagId,
                        qTag.description))
                .from(qCourse)
                .leftJoin(qCourse.tags, qTag)
                .where(qCourse.courseId.in(ids))
                .orderBy(order)
                .fetch();

        Long total = countFiltered(filters);

        return new PageImpl<>(rows, pageable, total);
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
