package com.softeng306.validation;

import com.softeng306.domain.student.Student;
import com.softeng306.managers.StudentMgr;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentValidator {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    private static final String STUDENT_ID_REGEX = "^U[0-9]{7}[A-Z]$";
    private static final String STUDENT_NAME_REGEX = "^[ a-zA-Z]+$";


    /**
     * Checks whether the inputted student ID is in the correct format.
     *
     * @param studentID The inputted student ID.
     * @return boolean indicates whether the inputted student ID is valid.
     */
    public static boolean checkValidStudentIDInput(String studentID) {
        boolean valid = RegexValidator.checkStringRegexFormat(studentID, STUDENT_ID_REGEX);
        if (!valid) {
            System.out.println("Wrong format of student ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted student name is in the correct format.
     *
     * @param studentName The inputted student name.
     * @return boolean indicates whether the student person name is valid.
     */
    public static boolean checkValidStudentNameInput(String studentName) {
        boolean valid = RegexValidator.checkStringRegexFormat(studentName, STUDENT_NAME_REGEX);
        if (!valid) {
            System.out.println("Wrong format of name.");
        }
        return valid;
    }

    /**
     * Checks whether this student ID is used by other students.
     *
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public static Student checkStudentExists(String studentID) {
        List<Student> anyStudent = StudentMgr.students.stream().filter(s -> studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if (anyStudent.size() == 0) {
            return null;
        }
        System.out.println("Sorry. The student ID is used. This student already exists.");
        return anyStudent.get(0);
    }

    /**
     * Prompts the user to input an existing student.
     *
     * @return the inputted student.
     */
    public static Student checkStudentExists() {
        String studentID;
        Student currentStudent = null;
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = scanner.nextLine();
            while ("-h".equals(studentID)) {
                StudentMgr.getInstance().printAllStudentIds();
                studentID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            currentStudent = checkStudentExists(studentID);
            System.setOut(originalStream);
            if (currentStudent == null) {
                System.out.println("Invalid Student ID. Please re-enter.");
            } else {
                break;
            }

        }
        return currentStudent;
    }
}
