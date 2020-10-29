package com.softeng306.validation;

import com.softeng306.domain.student.Student;
import com.softeng306.managers.StudentMgr;

import java.util.List;
import java.util.stream.Collectors;

public class StudentValidator {

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
        List<Student> anyStudent = StudentMgr.getInstance().getStudents().stream().filter(s -> studentID.equals(s.getStudentID())).collect(Collectors.toList());
        if (anyStudent.isEmpty()) {
            return null;
        }
        return anyStudent.get(0);
    }
}
