package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.mark.IStudentCourseMark;
import com.softeng306.domain.student.IStudent;

import java.util.List;

/**
 * Interface for defining student mark operations.
 * Defines the responsibilities of what functions should be done with student course marks.
 */
public interface IStudentCourseMarkMgr {
    /**
     * Initializes marks for a student when he/she just registered a course.
     *
     * @param student the student this mark record belongs to.
     * @param course  the course this mark record about.
     * @return the new added mark.
     */
    IStudentCourseMark initialiseStudentCourseMark(IStudent student, ICourse course);

    /**
     * Sets the coursework mark for the mark record.
     *
     * @param isExam whether this coursework component refers to "Exam"
     */
    void setCourseworkMark(boolean isExam, String studentID, String courseID);

    /**
     * Return the list of all marks in the system.
     *
     * @return An list of all marks.
     */
    List<IStudentCourseMark> getStudentCourseMarks();

    /**
     * Returns the academic units for a student
     *
     * @param studentId The student ID of specified student
     * @return the academic units for said student
     */
    int getAcademicUnitsForStudent(String studentId);

    /**
     * Returns the mark message for the student, containing each mark each course that the student is registered for
     * This includes marks for each assessment component for each course, as well as the overall GPA for said student
     *
     * @param studentId The student ID for said student
     * @param totalAU The total academic units for said student
     * @return The list containing the mark message for said student
     */
    List<String> getMarkMessageForStudent(String studentId, int totalAU);
}
