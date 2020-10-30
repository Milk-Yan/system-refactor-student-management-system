package com.softeng306.io;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICourseMgrIO {
    /**
     * Read in the number of groups for a particular stream (lecture, lab, tutorial)
     *
     * @param type       the type of group we are reading in
     * @param compareTo  the upper limit to compare it to
     * @param totalSeats the total number of seats available
     * @return the number of groups the user has inputted
     */
    int readNoOfGroup(String type, int compareTo, int totalSeats);

    /**
     * Reads in a weekly hour for a group from the user
     *
     * @param type          the type of the group the weekly hour is for
     * @param academicUnits the number of academic units for the course
     * @return int the number of weekly hours for that group
     */
    int readWeeklyHour(String type, int academicUnits);

    /**
     * Reads in the user's choice for creating components now
     *
     * @return int the choice the user specified for creating a component
     */
    int readCreateCourseComponentChoice();

    /**
     * Prints a success message that a course has been successfully added
     *
     * @param courseID the course to print the success message for
     */
    void printCourseAdded(String courseID);

    /**
     * Prints an information message that the components have not been initialised
     *
     * @param courseID the course that the components have not been initialised for
     */
    void printComponentsNotInitialisedMessage(String courseID);

    /**
     * Print course information to the console for a user
     */
    void printCourseInfoString(String courseInfoString);

    /**
     * Helper method to print out the vacancies for groups
     *
     * @param groupInformation the groups to print
     */
    void printVacanciesForGroups(String[][] groupInformation, String groupType);

    /**
     * Print out an error message that the course specified does not exist
     */
    void printCourseNotExist();

    /**
     * Reads in the users choice for whether a course should have a final exam
     *
     * @return int whether the user wants to have a final exam for a course
     */
    int readHasFinalExamChoice();

    /**
     * Reads in an exam weighting for a course from the user
     *
     * @return int the exam weight
     */
    int readExamWeight();

    /**
     * Print out a message to the user asking to enter assessments
     */
    void printEnterContinuousAssessments();

    /**
     * Reads in the number of main components for a course from the user
     *
     * @return int number of main components specified
     */
    int readNoOfMainComponents();

    /**
     * Reads in the main component weightage from the user
     *
     * @param i              the number entry for the main component
     * @param totalWeightage the current total weightage
     * @return the main component weightage
     */
    int readMainComponentWeightage(int i, int totalWeightage);

    /**
     * Reads in the number of sub components for a main component from the user
     *
     * @param mainComponentNo the main component number the sub components are for
     * @return int the number of sub components
     */
    int readNoOfSubComponents(int mainComponentNo);

    /**
     * Prints an error that the total weightage does not add to 100
     */
    void printWeightageError();

    /**
     * Prints the components for a course
     */
    void printComponentsForCourse(String courseId, String courseName, Map<Map<String, String>, Map<String, String>> allGroupInformation);

    /**
     * Read in the name for a main component from the user
     *
     * @param totalWeightage  the total weightage for a course
     * @param mainComponentNo the main component number
     * @return the main component name
     */
    String readMainComponentName(int totalWeightage, int mainComponentNo, Set<String> mainComponentNames);

    /**
     * Read in the sub components specified by a user for a course
     *
     * @return List of sub components user has specified
     */
    Map<String, Double> readSubComponents(int numberOfSubComponents);

    /**
     * Print an info message that the course specified is empty
     */
    void printEmptyCourseComponents(String courseID, String courseName);

    /**
     * Print courses
     */
    void printCourses(Map<String, List<String>> courseGeneralInfo);

    /**
     * Print all the course ids
     *
     * @param courses the courses to print ids for
     */
    void printAllCourseIds(List<String> courses);

    /**
     * Print error that course assessment is settled
     */
    void printCourseworkWeightageEnteredError();

    /**
     * Print course statistics header for a particular course
     */
    void printCourseStatisticsHeader(List<String> courseInfo);

    /**
     * Print a main component to the user
     */
    void printMainComponent(String mainComponentName, int mainComponentWeight, double averageCourseMark);

    /**
     * Print statistics for subcomponents
     */
    void printSubcomponents(String[][] subComponentInformation, Map<String, Double> courseMarks);

    /**
     * Print statistics for an exam to the user
     */
    void printExamStatistics(int examWeight, Double examMark);

    /**
     * Print message that course has no exam
     */
    void printNoExamMessage();

    /**
     * Print overall performance based on course marks
     *
     * @param overallMark for the course
     */
    void printOverallPerformance(double overallMark);

    /**
     * Gets a course from a user-inputted ID
     *
     * @return the inputted course.
     */
    String readExistingCourseId();

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    String readExistingDepartment();

    void addCourse();

    void checkAvailableSlots();

    void enterCourseWorkComponentWeightage();

    void printCourseStatistics();

    void printEmptySpace();
}
