package com.softeng306.domain.course.courseregistration;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.student.IStudent;
import com.softeng306.enums.GroupType;

public class CourseRegistration implements ICourseRegistration {
    private IStudent student;
    private ICourse course;
    private IGroup lectureGroup;
    private IGroup tutorialGroup;
    private IGroup labGroup;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public CourseRegistration() {

    }

    public CourseRegistration(IStudent student, ICourse course, IGroup lectureGroup, IGroup tutorialGroup, IGroup labGroup) {
        this.student = student;
        this.course = course;
        this.lectureGroup = lectureGroup;
        this.tutorialGroup = tutorialGroup;
        this.labGroup = labGroup;
    }

    @Override
    public IStudent getStudent() {
        return student;
    }

    @Override
    public ICourse getCourse() {
        return course;
    }

    @Override
    public IGroup getLectureGroup() {
        return lectureGroup;
    }

    @Override
    public IGroup getTutorialGroup() {
        return tutorialGroup;
    }

    @Override
    public IGroup getLabGroup() {
        return labGroup;
    }

    @Override
    public IGroup getGroupByType(GroupType type) throws GroupTypeNotFoundException {
        if (type == GroupType.LECTURE_GROUP) {
            return lectureGroup;
        } else if (type == GroupType.TUTORIAL_GROUP) {
            return tutorialGroup;
        } else if (type == GroupType.LAB_GROUP) {
            return labGroup;
        }

        throw new GroupTypeNotFoundException(type.toString());
    }
}
