package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.DetailedCourseRow;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Badge.BadgeResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Content.ContentResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
import lombok.Builder;

@Builder
public record DetailedCourseResponseDTO(
        Long courseId,
        String title,
        String description,
        String author,
        String thumbnailUrl,
        LocalDateTime createdAt,
        List<TagResponseDTO> tags,
        List<BadgeResponseDTO> badges,
        Integer totalContents,
        List<ContentResponseDTO> contents) {

    public static DetailedCourseResponseDTO from(Course course) {
        if (course == null)
            return null;

        return DetailedCourseResponseDTO.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .description(course.getDescription())
                .author(course.getAuthor())
                .thumbnailUrl(course.getThumbnailUrl())
                .createdAt(course.getCreatedAt())
                .tags(course.getTags() != null
                        ? course.getTags().stream().map(TagResponseDTO::from).toList()
                        : new ArrayList<>())
                .badges(course.getBadges() != null
                        ? course.getBadges().stream().map(BadgeResponseDTO::from).toList()
                        : new ArrayList<>())
                .totalContents(course.getContents() != null
                        ? course.getContents().size()
                        : 0)
                .contents(course.getContents() != null
                        ? course.getContents().stream().map(ContentResponseDTO::from).toList()
                        : new ArrayList<>())
                .build();
    }

    public static DetailedCourseResponseDTO from(List<DetailedCourseRow> rows) {
        if (rows == null || rows.isEmpty())
            return null;

        DetailedCourseRow course = rows.get(0);

        List<TagResponseDTO> tags = new ArrayList<>();
        List<BadgeResponseDTO> badges = new ArrayList<>();
        List<ContentResponseDTO> contents = new ArrayList<>();

        Set<Long> addedTagIds = new HashSet<>();
        Set<Long> addedBadgeIds = new HashSet<>();
        Set<Long> addedContentIds = new HashSet<>();

        for (var row : rows) {

            if (row.tagId() != null && !addedTagIds.contains(row.tagId())) {
                tags.add(new TagResponseDTO(
                        row.tagId(),
                        row.tagDescription()));

                addedTagIds.add(row.tagId());
            }

            if (row.badgeId() != null && !addedBadgeIds.contains(row.badgeId())) {
                badges.add(new BadgeResponseDTO(
                        row.badgeId(),
                        row.courseId(),
                        row.badgeTitle(),
                        row.iconUrl()));

                addedBadgeIds.add(row.badgeId());
            }

            if (row.contentId() != null && !addedContentIds.contains(row.contentId())) {
                contents.add(new ContentResponseDTO(
                        row.contentId(),
                        row.courseId(),
                        row.type(),
                        row.position()));

                addedContentIds.add(row.contentId());
            }

        }

        return new DetailedCourseResponseDTO(
                course.courseId(),
                course.title(),
                course.description(),
                course.author(),
                course.thumbnailUrl(),
                course.createdAt(),
                tags,
                badges,
                contents.size(),
                contents);
    }

}
