package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Tag.TagResponseDTO;
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
public class CourseResponseDTO {

    private Long courseId;
    private String title;
    private String description;
    private String author;
    private String thumbnailUrl;
    private LocalDateTime createdAt;

    @Builder.Default
    private Set<TagResponseDTO> tags = new HashSet<>();

    @Builder.Default
    private List<Badge> badges = new ArrayList<>();

    @Builder.Default
    private List<Content> contents = new ArrayList<>();

    public static CourseResponseDTO fromEntity(Course course) {
        if (course == null)
            return null;

        return CourseResponseDTO.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .description(course.getDescription())
                .author(course.getAuthor())
                .thumbnailUrl(course.getThumbnailUrl())
                .createdAt(course.getCreatedAt())
                .tags(course.getTags() != null ? course.getTags().stream()
                        .map(TagResponseDTO::fromEntity)
                        .collect(Collectors.toSet()) : new HashSet<>())
                .badges(course.getBadges() != null ? course.getBadges() : new ArrayList<>())
                .contents(course.getContents() != null ? course.getContents() : new ArrayList<>())
                .build();
    }

    public static Course toEntity(CourseResponseDTO dto) {
        if (dto == null)
            return null;

        return Course.builder()
                .courseId(dto.getCourseId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .author(dto.getAuthor())
                .thumbnailUrl(dto.getThumbnailUrl())
                .createdAt(dto.getCreatedAt())
                .tags(dto.getTags() != null ? dto.getTags().stream()
                        .map(TagResponseDTO::toEntity)
                        .collect(Collectors.toSet()) : new HashSet<>())
                .badges(dto.getBadges() != null ? dto.getBadges() : new ArrayList<>())
                .contents(dto.getContents() != null ? dto.getContents() : new ArrayList<>())
                .build();
    }

}
