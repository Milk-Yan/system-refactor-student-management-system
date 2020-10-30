package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.mark.IMark;
import com.softeng306.domain.student.IStudent;

import java.util.List;

public interface IMarkMgr {
    /**
     * Initializes marks for a student when he/she just registered a course.
     *
     * @param student the student this mark record belongs to.
     * @param course  the course this mark record about.
     * @return the new added mark.
     */
    IMark initialiseMark(IStudent student, ICourse course);

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
    List<IMark> getMarks();

    int getAcademicUnitsForStudent(String studentId);

    List<String> getMarkMessageForStudent(String studentId, int totalAU);
}
