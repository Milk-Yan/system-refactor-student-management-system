package com.softeng306.io;

import java.util.List;

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

    void printAlreadyRegisteredError();

    /**
     * If the user input is invalid, this method will let the user know
     */
    void printInvalidUserInputMessage();

    void printEndOfSection();

    void printNoVacancies();

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

    void printGroupString(List<String> groupString);
}
