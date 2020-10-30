package com.softeng306.managers;

import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.exceptions.InvalidCourseRegistrationException;
import com.softeng306.domain.exceptions.StudentNotFoundException;

import java.util.List;

public interface ICourseRegistrationMgr {
    /**
     * Registers a course for a student
     */
    List<String> registerCourse(String studentID, String courseID) throws InvalidCourseRegistrationException, StudentNotFoundException, CourseNotFoundException;

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    void printStudents(String courseID, int opt) throws CourseNotFoundException, GroupTypeNotFoundException;

    List<String> getCourseIdsForStudentId(String studentId);
}
