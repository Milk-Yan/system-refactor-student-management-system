package com.softeng306.io;

import java.util.List;

/**
 * Interface for CourseRegistrationMgrIO.
 * CourseRegistrationMgrIO acts as a central module to manipulate course registrations.
 * Provides methods to manipulate course registrations.
 */
public interface ICourseRegistrationMgrIO {
    /**
     * When there is no group of the given type, this method will be called
     */
    void printContainsNoGroupMessage(String type);

    /**
     * When there are no registrations for a course, this method will print an error
     * to the user
     */
    void printNoRegistrationsForCourseMessage();

    /**
     * Displays the error message given when the user tries to register a student for a course that they're already registered in.
     */
    void printAlreadyRegisteredError();

    /**
     * If the user input is invalid, this method will let the user know
     */
    void printInvalidUserInputMessage();

    /**
     * Displays the end of section separator.
     */
    void printEndOfSection();

    /**
     * Displays the message given when the user when they try to register a student to a course that has no vacancies.
     */
    void printNoVacancies();

    /**
     * Displays the message for course information when a course has no exam.
     */
    void printNoAssessmentMessage(String profName);

    /**
     * Before registration details are known, this will print an pending message.
     *
     * @param studentName is the name of the student
     * @param studentId   is the id of the student
     * @param courseId    is a course id that we are registering a student for.
     * @param courseName  is a course name that we are registering a student for.
     */
    void printRegistrationRequestDetails(String studentName, String studentId, String courseId, String courseName);

    /**
     * Gets a student and course from the user to create a new registration for
     */
    void registerStudentForCourse();

    /**
     * Gets a course id from the user to print student registrations for
     */
    void printStudents();

    /**
     * Prints a list of group names to console.
     * @param groupString List of group names
     */
    void printGroupString(List<String> groupString);
}
