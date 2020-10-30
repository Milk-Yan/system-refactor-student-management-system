package com.softeng306.io;

import java.util.List;

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

    void initiateEnteringCourseworkMark(boolean isExam);
}
