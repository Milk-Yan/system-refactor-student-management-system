package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.student.IStudent;

import java.util.List;

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
     *
     * @return the course of this student mark record.
     */
    ICourse getCourse();

    /**
     * Gets the course work marks of this student mark record.
     *
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
     */
    List<Double> setMainComponentMark(String courseWorkName, double result);

    /**
     * Sets the sub course work marks of this student mark record.
     *
     * @param courseWorkName The name of this sub course work.
     * @param result         The mark obtained in this sub course work.
     */
    List<Double> setSubComponentMark(String courseWorkName, double result);
}
