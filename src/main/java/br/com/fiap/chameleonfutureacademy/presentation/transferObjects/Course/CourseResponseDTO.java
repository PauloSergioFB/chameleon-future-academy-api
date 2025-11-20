package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseTagRow;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
import lombok.Builder;

@Builder
public record CourseResponseDTO(
        Long courseId,
        String title,
        String description,
        String author,
        String thumbnailUrl,
        LocalDateTime createdAt,
        List<TagResponseDTO> tags,
        Integer totalContents) {

    public static CourseResponseDTO from(Course course) {
        if (course == null)
            return null;

        return CourseResponseDTO.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .description(course.getDescription())
                .author(course.getAuthor())
                .thumbnailUrl(course.getThumbnailUrl())
                .createdAt(course.getCreatedAt())
                .tags(course.getTags() != null
                        ? course.getTags().stream().map(TagResponseDTO::from).toList()
                        : new ArrayList<>())
                .totalContents(course.getContents() != null
                        ? course.getContents().size()
                        : 0)
                .build();
    }

    public static CourseResponseDTO from(CourseTagRow row) {
        if (row == null)
            return null;

        return CourseResponseDTO.builder()
                .courseId(row.courseId())
                .title(row.title())
                .description(row.description())
                .author(row.author())
                .thumbnailUrl(row.thumbnailUrl())
                .createdAt(row.createdAt())
                .tags(new ArrayList<>())
                .totalContents(row.totalContents().intValue())
                .build();
    }

    public static List<CourseResponseDTO> from(List<CourseTagRow> rows) {
        Map<Long, CourseResponseDTO> coursesMap = new LinkedHashMap<>();

        for (var row : rows) {
            CourseResponseDTO dto = coursesMap.computeIfAbsent(
                    row.courseId(),
                    id -> CourseResponseDTO.from(row));

            if (row.tagId() != null) {
                TagResponseDTO tagDto = TagResponseDTO.builder()
                        .tagId(row.tagId())
                        .description(row.tagDescription())
                        .build();

                dto.tags().add(tagDto);
            }
        }

        return new ArrayList<>(coursesMap.values());
    }

}
