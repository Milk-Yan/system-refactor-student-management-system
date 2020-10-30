package com.softeng306.domain.student;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

@JsonDeserialize(as = Student.class)
public interface IStudent {
    /**
     * Gets the student's ID.
     *
     * @return this student's ID.
     */
    String getStudentId();

    /**
     * Gets the student's name.
     *
     * @return this student's name.
     */
    String getName();

    /**
     * Gets the student's school.
     *
     * @return this student's school.
     */
    Department getDepartment();

    /**
     * Gets the student's gender.
     *
     * @return this student's gender.
     */
    Gender getGender();

    /**
     * Gets the student's GPA.
     *
     * @return this student's GPA.
     */
    double getGpa();

    /**
     * Gets the student's year.
     *
     * @return this student's year.
     */
    int getYearLevel();

    /**
     * Sets the ID of this student.
     *
     * @param studentID this student's ID.
     */
    void setStudentId(String studentID);

    /**
     * Sets the school of this student.
     *
     * @param studentSchool this student's school.
     */
    void setDepartment(Department studentSchool);

    /**
     * Sets the gender of this student.
     *
     * @param gender this student's gender.
     */
    void setGender(Gender gender);

    /**
     * Sets the GPA of this student.
     *
     * @param GPA this student's GPA.
     */
    void setGpa(double GPA);

    /**
     * Sets the year of this student.
     *
     * @param studentYear this student's year.
     */
    void setYearLevel(int studentYear);
}
