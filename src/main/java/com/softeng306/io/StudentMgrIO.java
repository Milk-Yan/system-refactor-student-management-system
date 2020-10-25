package com.softeng306.io;

import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;
import com.softeng306.managers.StudentMgr;
import com.softeng306.validation.DepartmentValidator;
import com.softeng306.validation.GenderValidator;
import com.softeng306.validation.StudentValidator;
import com.softeng306.domain.student.Student;

import java.util.Scanner;

public class StudentMgrIO {

    private static Scanner reader = new Scanner(System.in);


    public static void printMenu() {
        MainMenuIO.printMethodCall("addStudent");
        System.out.println("Choose the way you want to add a student:");
        System.out.println("1. Manually input the student ID.");
        System.out.println("2. Let the system self-generate the student ID.");
    }

    public static boolean systemGenerateID() {
        int choice;
        do {
            System.out.println("Please input your choice:");
            if (reader.hasNextInt()) {
                choice = reader.nextInt();
                reader.nextLine();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid input. Please re-enter.");
                } else {
                    if (choice == 1) {
                        return false;
                    }
                    return true;
                }
            } else {
                System.out.println("Your input " + reader.nextLine() + " is not an integer.");
            }
        } while (true);

    }

    public static String getStudentID() {
        while (true) {
            System.out.println("The student ID should follow:");
            System.out.println("Length is exactly 9");
            System.out.println("Start with U (Undergraduate)");
            System.out.println("End with a uppercase letter between A and L");
            System.out.println("Seven digits in the middle");
            System.out.println();
            System.out.println("Give this student an ID: ");
            String studentID = reader.nextLine();
            if (StudentValidator.checkValidStudentIDInput(studentID)) {
                if (StudentValidator.checkStudentExists(studentID) == null) {
                    return studentID;
                }
            }
        }
    }

    public static String getStudentName() {
        String studentName;
        while (true) {
            System.out.println("Enter student Name: ");
            studentName = reader.nextLine();
            if (StudentValidator.checkValidStudentNameInput(studentName)) {
                return studentName;
            }
        }
    }

    public static String getSchoolName() {
        String studentSchool;
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = reader.nextLine();
            while ("-h".equals(studentSchool)) {
                Department.printAllDepartment();
                studentSchool = reader.nextLine();
            }

            if (DepartmentValidator.checkDepartmentValidation(studentSchool)) {
                return studentSchool;
            }
        }
    }

    public static String getStudentGender() {
        String studentGender;
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = reader.nextLine();
            while ("-h".equals(studentGender)) {
                Gender.printAllGender();
                studentGender = reader.nextLine();
            }

            if (GenderValidator.checkGenderValidation(studentGender)) {
                return studentGender;
            }
        }
    }

    public static int getStudentYear() {
        int studentYear;
        do {
            System.out.println("Enter student's school year (1-4) : ");
            if (reader.hasNextInt()) {
                studentYear = reader.nextInt();
                reader.nextLine();
                if (studentYear < 1 || studentYear > 4) {
                    System.out.println("Your input is out of bound.");
                    System.out.println("Please re-enter an integer between 1 and 4");
                } else {
                    return studentYear;
                }
            } else {
                System.out.println("Your input " + reader.nextLine() + " is not an integer");
                System.out.println("Please re-enter.");
            }
        } while (true);
    }

    public static void printStudentID(String name, String ID) {
        System.out.println("Student named: " + name + " is added, with ID: " + ID);
        System.out.println("Student List: ");
        System.out.println("| Student ID | Student Name | Student School | Gender | Year | GPA |");
        for (Student student : StudentMgr.getInstance().getStudents()) {
            String GPA = "not available";
            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            System.out.println(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
    }
}
