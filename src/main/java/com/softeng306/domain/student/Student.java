package com.softeng306.domain.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.enums.Gender;
import com.softeng306.main.Main;
import com.softeng306.managers.StudentMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student enrolled in a school.
 * A student has studentID, studentName, studentSchool, gender, GPA and year.
 * A student can enroll many courses.
 */
public class Student {

    private String studentID;
    private String studentName;
    private String studentSchool;
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

    /**
     * Gets the student's ID.
     *
     * @return this student's ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Gets the student's name.
     *
     * @return this student's name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Gets the student's school.
     *
     * @return this student's school.
     */
    public String getStudentSchool() {
        return studentSchool;
    }

    /**
     * Gets the student's gender.
     *
     * @return this student's gender.
     */
    public Gender getGender(){
        return gender;
    }

    /**
     * Gets the student's GPA.
     *
     * @return this student's GPA.
     */
    public double getGPA() {
        return GPA;
    }

    /**
     * Gets the student's year.
     *
     * @return this student's year.
     */
    public int getStudentYear() {
        return studentYear;
    }

    /**
     * Sets the ID of this student.
     *
     * @param studentID this student's ID.
     */
    public void setStudentID(String studentID) { this.studentID = studentID;
    }

    /**
     * Sets the school of this student.
     *
     * @param studentSchool this student's school.
     */
    public void setStudentSchool(String studentSchool) {
        this.studentSchool = studentSchool;
    }

    /**
     * Sets the gender of this student.
     *
     * @param gender this student's gender.
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }

    /**
     * Sets the GPA of this student.
     *
     * @param GPA this student's GPA.
     */
    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    /**
     * Sets the year of this student.
     *
     * @param studentYear this student's year.
     */
    public void setStudentYear(int studentYear) {
        this.studentYear = studentYear;
    }

}
