package com.softeng306.domain.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

/**
 * Represents a student enrolled in a school.
 * A student has studentID, studentName, studentSchool, gender, GPA and year.
 * A student can enroll many courses.
 */
public class Student implements IStudent {

    private String studentID;
    private String studentName;
    private Department studentSchool;
    private Gender gender;

    @JsonProperty("GPA")
    private double GPA = 0;
    private int studentYear;


    /**
     * Default constructor, required for Jackson serialization.
     */
    public Student() {

    }

    /**
     * Creates student with the student name and student's ID.
     *
     * @param studentID   This student's name.
     * @param studentName This student's ID.
     */
    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    @Override
    public String getStudentID() {
        return studentID;
    }

    @Override
    public String getStudentName() {
        return studentName;
    }

    @Override
    public Department getStudentSchool() {
        return studentSchool;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public double getGPA() {
        return GPA;
    }

    @Override
    public int getStudentYear() {
        return studentYear;
    }

    @Override
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public void setStudentSchool(Department studentSchool) {
        this.studentSchool = studentSchool;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    @Override
    public void setStudentYear(int studentYear) {
        this.studentYear = studentYear;
    }

}
