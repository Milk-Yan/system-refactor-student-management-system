package com.softeng306.managers;

import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.exceptions.InvalidCourseRegistrationException;
import com.softeng306.domain.exceptions.StudentNotFoundException;

import java.util.List;

/**
 * Interface for course registration manager operations.
 * Defines the responsibilities of what functions must be performed on courses registrations in the academic institute.
 */
public interface ICourseRegistrationMgr {

    /**
     * Registers a course for a student
     *
     * @param studentID The student registering for the course
     * @param courseID The course being registered by the student
     * @return information about the registration
     * @throws InvalidCourseRegistrationException
     * @throws StudentNotFoundException
     * @throws CourseNotFoundException
     */
    List<String> registerCourse(String studentID, String courseID) throws InvalidCourseRegistrationException, StudentNotFoundException, CourseNotFoundException;

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     *
     * @param courseID The course ID representing the course to print students for
     * @param opt The option chosem: 1 for lecture groups, 2 for tutorial groups, 3 for lab groups
     * @throws CourseNotFoundException
     * @throws GroupTypeNotFoundException
     */
    void printStudents(String courseID, int opt) throws CourseNotFoundException, GroupTypeNotFoundException;

    /**
     * Gets all course IDs that student has registered for
     *
     * @param studentId The student ID representing the student to get information for
     * @return the list of course IDs that the student has registered for
     */
    List<String> getCourseIdsForStudentId(String studentId);
}
