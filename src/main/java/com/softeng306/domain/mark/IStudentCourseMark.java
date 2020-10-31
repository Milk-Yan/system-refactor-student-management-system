package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.student.IStudent;

import java.util.List;

/**
 * Interface for a StudentCourseMark.
 * Used to represent a Mark a student receives in a course.
 * Provides methods to get the student and course this mark is for, and to give access to the overall and MainComponentMarks for this course.
 */
@JsonDeserialize(as = StudentCourseMark.class)
public interface IStudentCourseMark {
    /**
     * Gets the student of this student mark record.
     *
     * @return the student of this student mark record.
     */
    IStudent getStudent();

    /**
     * Gets the course of this student mark record.
     * @return the course of this student mark record.
     */
    ICourse getCourse();

    /**
     * Gets the course work marks of this student mark record.
     * @return a list contains the course work marks of this student mark record.
     */
    List<IMainComponentMark> getCourseWorkMarks();

    /**
     * Gets the total mark of this student mark record.
     *
     * @return the total mark of this student mark record.
     */
    double getTotalMark();

    /**
     * Sets the main course work marks of this student mark record.
     *
     * @param courseWorkName The name of this main course work.
     * @param result         The mark obtained in this main course work.
     * @return resultList List of calculated results to print.
     */
    List<Double> setMainComponentMark(String courseWorkName, double result);

    /**
     * Sets the sub course work marks of this student mark record.
     *
     * @param courseWorkName The name of this sub course work.
     * @param result         The mark obtained in this sub course work.
     *                       @return resultList List of calculated results to print.
     */
    List<Double> setSubComponentMark(String courseWorkName, double result);
}
