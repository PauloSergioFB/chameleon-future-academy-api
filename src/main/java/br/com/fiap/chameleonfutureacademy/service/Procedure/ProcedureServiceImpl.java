package br.com.fiap.chameleonfutureacademy.service.Procedure;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.Activity;
import br.com.fiap.chameleonfutureacademy.domainmodel.ActivityOption;
import br.com.fiap.chameleonfutureacademy.domainmodel.Badge;
import br.com.fiap.chameleonfutureacademy.domainmodel.Content;
import br.com.fiap.chameleonfutureacademy.domainmodel.Course;
import br.com.fiap.chameleonfutureacademy.domainmodel.Enrollment;
import br.com.fiap.chameleonfutureacademy.domainmodel.Lesson;
import br.com.fiap.chameleonfutureacademy.domainmodel.Tag;
import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.domainmodel.UserBadge;
import br.com.fiap.chameleonfutureacademy.domainmodel.repositories.Procedures.ProcedureRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureServiceImpl implements ProcedureService {

    private final ProcedureRepository procedureRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void prcSaveUser(User user) {
        try {
            user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));

            procedureRepository.prcInsertUser(
                    user.getFullName(),
                    user.getEmail(),
                    user.getHashedPassword(),
                    user.getBiography(),
                    user.getWhatsapp(),
                    user.getProfileImage());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveCourse(Course course) {
        try {
            procedureRepository.prcInsertCourse(
                    course.getTitle(),
                    course.getDescription(),
                    course.getAuthor(),
                    course.getThumbnailUrl());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveTag(Tag tag) {
        try {
            procedureRepository.prcInsertTag(
                    tag.getDescription());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveContent(Content content) {
        try {
            procedureRepository.prcInsertContent(
                    content.getCourse().getCourseId(),
                    content.getType(),
                    content.getPosition());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveLesson(Lesson lesson) {
        try {
            procedureRepository.prcInsertLesson(
                    lesson.getContent().getContentId(),
                    lesson.getTitle(),
                    lesson.getBody());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveActivity(Activity activity) {
        try {
            procedureRepository.prcInsertActivity(
                    activity.getContent().getContentId(),
                    activity.getTitle(),
                    activity.getBody(),
                    activity.getExplanation());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcSaveBadge(Badge badge) {
        try {
            procedureRepository.prcInsertBadge(
                    badge.getCourse().getCourseId(),
                    badge.getTitle(),
                    badge.getIconUrl());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcRegisterUserInCourse(Enrollment enrollment) {
        try {
            procedureRepository.prcRegisterUserInCourse(
                    enrollment.getUser().getUserId(),
                    enrollment.getCourse().getCourseId(),
                    enrollment.getProgress(),
                    enrollment.getStatus(),
                    enrollment.getStartedAt(),
                    enrollment.getCompletedAt());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcAddTagForCourse(Long courseId, Long tagId) {
        try {
            procedureRepository.prcAddTagForCourse(courseId, tagId);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcAddOptionForActivity(ActivityOption option) {
        try {
            procedureRepository.prcAddOptionForActivity(
                    option.getActivity().getActivityId(),
                    option.getLabel(),
                    option.getDescription(),
                    option.getIsCorrect() ? 1 : 0);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void prcAddBadgeForUser(UserBadge userBadge) {
        try {
            procedureRepository.prcAddBadgeForUser(
                    userBadge.getUser().getUserId(),
                    userBadge.getBadge().getBadgeId(),
                    userBadge.getEarnedAt());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
