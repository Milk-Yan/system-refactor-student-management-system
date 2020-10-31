package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Interface for the MarkCalculator class.
 * Defines methods which are used to get the marks for Courses and their components.
 */
@JsonDeserialize(as = MarkCalculator.class)
public interface IMarkCalculator {

    /**
     * Computes the average mark of all students for a specific course component of a course.
     * @param courseID the courseId of the course
     * @param componentName the name of the component within that course
     * @return the average mark across all students
     */
    double computeAverageMarkForCourseComponent(String courseID, String componentName);

    /**
     * Computes the average mark of all students for a course.
     * @param courseID the courseId of the course
     * @return the average mark across all students
     */
    double computeOverallMarkForCourse(String courseID);

    /**
     * Computes the overall marks for a List of Marks.
     *
     * @param thisCourseMark The list of Marks.
     * @return The overall grade.
     */
    double computeOverallMark(List<IStudentCourseMark> thisCourseMark);

    /**
     * Computes the gpa gained for this course from the result of this course.
     *
     * @return the grade in gpa points
     */
    double convertMarkToGradePoints(IStudentCourseMark studentCourseMark);
}
