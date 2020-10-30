package com.softeng306.domain.student;

import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

/**
 * Represents a student enrolled in a school.
 * A student has studentID, studentName, studentSchool, gender, GPA and year.
 * A student can enroll many courses.
 */
public class Student implements IStudent {

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

    @Override
    public String getStudentId() {
        return studentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public double getGpa() {
        return gpa;
    }

    @Override
    public int getYearLevel() {
        return yearLevel;
    }

    @Override
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

}
