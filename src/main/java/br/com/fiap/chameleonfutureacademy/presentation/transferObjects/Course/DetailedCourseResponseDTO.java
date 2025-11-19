package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Course;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
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
                .contents(course.getContents() != null
                        ? course.getContents().stream().map(ContentResponseDTO::from).toList()
                        : new ArrayList<>())
                .build();
    }

}
