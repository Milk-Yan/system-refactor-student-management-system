package com.softeng306.validation;

import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.managers.CourseRegistrationMgr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseRegistrationValidator {
    /**
     * Checks whether this course registration record exists.
     *
     * @param studentID The inputted student ID.
     * @param courseID  The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public static boolean courseRegistrationExists(String studentID, String courseID) {
        Optional<CourseRegistration> courseRegistration = CourseRegistrationMgr
                .getInstance()
                .getCourseRegistrations()
                .stream()
                .filter(cr -> studentID.equals(cr.getStudent().getStudentID()))
                .filter(cr -> courseID.equals(cr.getCourse().getCourseID()))
                .findFirst();

        return courseRegistration.isPresent();
    }
}
