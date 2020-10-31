package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.exceptions.CourseNotFoundException;

import java.util.List;

/**
 * Interface for course manager operations.
 * Defines the responsibilities of what functions must be performed on courses in the academic institute.
 */
public interface ICourseMgr {

    /**
     * Creates a new course and stores it in the file.
     */
    void addCourse(ICourseBuilder completeBuilder);

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    void checkAvailableSlots();

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    void enterCourseWorkComponentWeightage(ICourse currentCourse);

    /**
     * Displays a list of IDs of all the courses.
     */
    void printAllCourseIds();

    /**
     * Get a list of course IDs for courses registered in a given department.
     *
     * @param departmentName The department to get course IDs for.
     * @return A list of course IDs.
     */
    List<String> getCourseIdsInDepartment(String departmentName);

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    void printCourseStatistics();

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     * @throws CourseNotFoundException If the user enters an invalid course.
     */
    ICourse readExistingCourse() throws CourseNotFoundException;

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    String readExistingDepartment();

    /**
     * Checks whether this course ID is used by other courses.
     *
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     * @throws CourseNotFoundException If there is no course with the given ID.
     */
    ICourse getCourseFromId(String courseID) throws CourseNotFoundException;

    /**
     * Gets number of lecture groups
     *
     * @param compareTo The limit of the number of lecture groups
     * @param totalSeats The total number of seats in the course
     * @return the number of lecture groups
     */
    int getNumberOfLectureGroups(int compareTo, int totalSeats);

    /**
     * Reads the number of weekly lecture hours
     *
     * @param academicUnits The number of academic units which limits the number of hours it can be
     * @return the number of weekly lecture hours
     */
    int readWeeklyLectureHour(int academicUnits);

    /**
     * Gets number of lab groups
     *
     * @param compareTo The limit of the number of lab groups
     * @param totalSeats The total number of seats in the course
     * @return the number of lab groups
     */
    int getNumberOfLabGroups(int compareTo, int totalSeats);

    /**
     * Reads the number of weekly lab hours
     *
     * @param academicUnits The number of academic units which limits the number of hours it can be
     * @return the number of weekly lab hours
     */
    int readWeeklyLabHour(int academicUnits);

    /**
     * Gets number of tutorial groups
     *
     * @param compareTo The limit af the number tutorial groups
     * @param totalSeats The total number of seats in the course
     * @return the number of tutorial groups
     */
    int getNumberOfTutorialGroups(int compareTo, int totalSeats);

    /**
     * Reads the number of weekly tutorial hours
     *
     * @param academicUnits The number of academic units which limits the number of hours it can be
     * @return the number of weekly tutorial hours
     */
    int readWeeklyTutorialHour(int academicUnits);

    /**
     * Gets the list of course types
     *
     * @return The list of course types of type String
     */
    List<String> getListCourseTypes();

    /**
     * Gets the String indicating a main component
     *
     * @return a string with the contents "main component"
     */
    String getMainComponentString();

    /**
     * Gets the String indicating a main component
     *
     * @return a string with the contents "main component"
     */
    String getSubComponentString();

    /**
     * Checks if the course with specified courseID exists
     *
     * @param courseID The courseID to check if the course exists
     * @return a boolean indicating if the course exists
     */
    boolean checkCourseExists(String courseID);
}
