package com.softeng306.managers;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.exceptions.CourseNotFoundException;

import java.util.List;

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
    void enterCourseWorkComponentWeightage(Course currentCourse);

    /**
     * Displays a list of IDs of all the courses.
     */
    void printAllCourseIds();

    List<String> getCourseIdsInDepartment(String departmentName);

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    void printCourseStatistics();

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    Course readExistingCourse() throws CourseNotFoundException;

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
     */
    Course getCourseFromId(String courseID) throws CourseNotFoundException;

    int getNumberOfLectureGroups(int compareTo, int totalSeats);

    int getReadWeeklyLectureHour(int academicUnits);

    int getNumberOfLabGroups(int compareTo, int totalSeats);

    int getReadWeeklyLabHour(int academicUnits);

    int getNumberOfTutorialGroups(int compareTo, int totalSeats);

    int getReadWeeklyTutorialHour(int academicUnits);

    List<String> getListCourseTypes();

    String getMainComponentString();

    String getSubComponentString();

    boolean checkCourseExists(String courseID);
}
