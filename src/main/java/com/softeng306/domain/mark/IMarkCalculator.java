package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = MarkCalculator.class)
public interface IMarkCalculator {
    double computeAverageMarkForCourseComponent(String courseID, String componentName);

    double computeOverallMarkForCourse(String courseID);

    /**
     * Computes the overall marks for a particular course.
     *
     * @param thisCourseMark The marks for the course.
     * @return The exam marks for the course.
     */
    double computeOverallMark(List<IStudentCourseMark> thisCourseMark);

    /**
     * Computes the gpa gained for this course from the result of this course.
     *
     * @return the grade (in A, B ... )
     */
    double convertMarkToGradePoints(IStudentCourseMark studentCourseMark);
}
