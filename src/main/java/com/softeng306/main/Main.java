package com.softeng306.main;

import com.softeng306.io.MainMenuIO;
import com.softeng306.managers.*;
import com.softeng306.io.FILEMgr;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * The main function of the system.
     * Command line interface.
     *
     * @param args The command line parameters.
     */
    public static void main(String[] args) {

        StudentMgr.students = FILEMgr.loadStudents();
        CourseMgr.courses = FILEMgr.loadCourses();
        CourseRegistrationMgr.courseRegistrations = FILEMgr.loadCourseRegistration();
        MarkMgr.marks = FILEMgr.loadStudentMarks();
        ProfessorMgr.professors = FILEMgr.loadProfessors();

        MainMenuIO.printWelcome();
        MainMenuIO.startMainMenu();
    }
}
