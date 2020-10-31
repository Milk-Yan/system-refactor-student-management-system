package com.softeng306.io;

import java.util.List;

/**
 * Interface for StudentCourseMarkMgrIO.
 * StudentCourseMarkMgrIO acts as a central module to manipulate marks for students and courses.
 * Provides methods to manipulate marks for both students and courses.
 */
public interface IStudentCourseMarkMgrIO {

    /**
     * Prints to console that a student is not registered for a course.
     */
    void printStudentNotRegisteredToCourse(String courseID);

    /**
     * Prints to console a list of available course component choices.
     */
    void printCourseComponentChoices(List<String> availableChoices, List<Integer> weights);

    /**
     * Reads the name of a course component from the user.
     */
    int readCourseComponentChoice(int numChoices);

    /**
     * Reads the mark of a course component from the user
     */
    double readCourseComponentMark();

    /**
     * Reads the mark for an exam from the user.
     */
    double readExamMark();

    /**
     * Initiate the mark entering process for setting a student mark.
     *
     * @param isExam If the component is an Exam or not.
     */
    void initiateEnteringCourseworkMark(boolean isExam);

    /**
     * Print the result after a sub component mark has been successfully set.
     *
     * @param resultList List of calculated results to print.
     */
    void printSubComponentMarkSetMessage(List<Double> resultList);

    /**
     * Print the result after a main component mark has been successfully set (can also be an exam mark).
     *
     * @param resultList List of calculated results to print.
     */
    void printMainComponentMarkSetMessage(List<Double> resultList);

    /**
     * Print an error message if a main component to enter a mark for is not found.
     *
     * @param message the error message to print.
     */
    void printMainComponentDoesNotExistMessage(String message);

}
