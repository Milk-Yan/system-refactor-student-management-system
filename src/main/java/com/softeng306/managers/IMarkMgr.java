package com.softeng306.managers;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.student.Student;

import java.util.List;

public interface IMarkMgr {
    /**
     * Initializes marks for a student when he/she just registered a course.
     *
     * @param student the student this mark record belongs to.
     * @param course  the course this mark record about.
     * @return the new added mark.
     */
    Mark initialiseMark(Student student, Course course);

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
    List<Mark> getMarks();

    int getAcademicUnitsForStudent(String studentId);

    List<String> getMarkMessageForStudent(String studentId, int totalAU);
}
