package com.softeng306.domain.course.courseregistration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.student.IStudent;
import com.softeng306.enums.GroupType;

@JsonDeserialize(as = CourseRegistration.class)
public interface ICourseRegistration {
    IStudent getStudent();

    ICourse getCourse();

    IGroup getLectureGroup();

    IGroup getTutorialGroup();

    IGroup getLabGroup();

    IGroup getGroupByType(GroupType type) throws GroupTypeNotFoundException;
}
