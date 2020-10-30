package com.softeng306.domain.course.courseregistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.student.IStudent;
import com.softeng306.enums.GroupType;

/**
 * Interface for course registrations, used for registering a student in a course
 * Provides methods for returning registration data
 */
@JsonDeserialize(as = CourseRegistration.class)
public interface ICourseRegistration {
    /**
     * @return The student association with this registration
     */
    IStudent getStudent();

    /**
     * @return The course that the student is registering for
     */
    ICourse getCourse();

    /**
     * @return The lecture group that this student has chosen for the course
     */
    IGroup getLectureGroup();

    /**
     * @return The tutorial group that this student has chosen for the course
     */
    IGroup getTutorialGroup();

    /**
     * @return The lab group that this student has chosen for the course
     */
    IGroup getLabGroup();

    /**
     * Get a specified group from this registration
     *
     * @param type The type of group to return
     * @return The requested group for this registration
     * @throws GroupTypeNotFoundException
     */
    IGroup getGroupByType(GroupType type) throws GroupTypeNotFoundException;

}
