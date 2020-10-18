package com.softeng306.main;

import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.io.MainMenuIO;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.professor.Professor;
import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;

import java.util.ArrayList;

public class Main {
    /**
     * An array list of all the students in this school.
     */
    public static ArrayList<Student> students = new ArrayList<Student>(0);
    /**
     * An array list of all the courses in this school.
     */
    public static ArrayList<Course> courses = new ArrayList<Course>(0);
    /**
     * An array list of all the course registration records in this school.
     */
    public static ArrayList<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>(0);
    /**
     * An array list of all the student mark records in this school.
     */
    public static ArrayList<Mark> marks = new ArrayList<Mark>(0);
    /**
     * An array list of all the professors in this school.
     */
    public static ArrayList<Professor> professors = new ArrayList<Professor>(0);

    /**
     * The main function of the system.
     *
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        students = FILEMgr.loadStudents();
        courses = FILEMgr.loadCourses();
        courseRegistrations = FILEMgr.loadCourseRegistration();
        marks = FILEMgr.loadStudentMarks();
        professors = FILEMgr.loadProfessors();

        MainMenuIO.printWelcome();
        MainMenuIO.startMainMenu();
    }
}
