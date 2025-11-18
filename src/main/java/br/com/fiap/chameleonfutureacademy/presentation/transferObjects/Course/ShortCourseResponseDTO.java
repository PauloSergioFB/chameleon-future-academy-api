package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import br.com.fiap.chameleonfutureacademy.infrastructure.queries.Course.CourseListRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseResponseDTO {

    private Long courseId;
    private String title;
    private String author;
    private String thumbnailUrl;
    private LocalDateTime createdAt;

    @Builder.Default
    private Set<String> tags = new HashSet<>();

    public static ShortCourseResponseDTO fromEntity(Course course) {
        if (course == null)
            return null;

        return ShortCourseResponseDTO.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .author(course.getAuthor())
                .thumbnailUrl(course.getThumbnailUrl())
                .createdAt(course.getCreatedAt())
                .tags(course.getTags() != null ? course.getTags().stream()
                        .map(Tag::getDescription)
                        .collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

    public static ShortCourseResponseDTO fromRow(CourseListRow row) {
        if (row == null)
            return null;

        return ShortCourseResponseDTO.builder()
                .courseId(row.courseId())
                .title(row.title())
                .author(row.author())
                .thumbnailUrl(row.thumbnailUrl())
                .createdAt(row.createdAt())
                .tags(new HashSet<>())
                .build();
    }

}
