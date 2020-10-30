package com.softeng306.domain.student;

import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

/**
 * Represents a student enrolled in a school.
 * A student has studentID, studentName, studentSchool, gender, GPA and year.
 * A student can enroll many courses.
 */
public class Student {

    private String studentId;
    private String name;
    private Department department;
    private Gender gender;
    private double gpa = 0;
    private int yearLevel;


    /**
     * Default constructor, required for Jackson serialization.
     */
    public Student() {

    }

    /**
     * Creates student with the student name and student's ID.
     *
     * @param studentId   This student's name.
     * @param name This student's ID.
     */
    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    /**
     * Gets the student's ID.
     *
     * @return this student's ID.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Gets the student's name.
     *
     * @return this student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the student's school.
     *
     * @return this student's school.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Gets the student's gender.
     *
     * @return this student's gender.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Gets the student's GPA.
     *
     * @return this student's GPA.
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Gets the student's year.
     *
     * @return this student's year.
     */
    public int getYearLevel() {
        return yearLevel;
    }

    /**
     * Sets the ID of this student.
     *
     * @param studentId this student's ID.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Sets the school of this student.
     *
     * @param department this student's school.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Sets the gender of this student.
     *
     * @param gender this student's gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the GPA of this student.
     *
     * @param gpa this student's GPA.
     */
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Sets the year of this student.
     *
     * @param yearLevel this student's year.
     */
    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

}
