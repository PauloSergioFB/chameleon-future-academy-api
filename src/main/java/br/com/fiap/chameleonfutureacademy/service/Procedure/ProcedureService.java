package br.com.fiap.chameleonfutureacademy.service.Procedure;

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

public interface ProcedureService {

    void prcSaveUser(User user);

    void prcSaveCourse(Course course);

    void prcSaveTag(Tag tag);

    void prcSaveContent(Content content);

    void prcSaveLesson(Lesson lesson);

    void prcSaveActivity(Activity activity);

    void prcSaveBadge(Badge badge);

    void prcRegisterUserInCourse(Enrollment enrollment);

    void prcAddTagForCourse(Long courseId, Long tagId);

    void prcAddOptionForActivity(ActivityOption option);

    void prcAddBadgeForUser(UserBadge userBadge);

}
