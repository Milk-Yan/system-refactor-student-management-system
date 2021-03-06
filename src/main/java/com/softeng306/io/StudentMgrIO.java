package com.softeng306.io;

import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;
import com.softeng306.managers.IStudentMgr;
import com.softeng306.managers.StudentCourseMarkMgr;
import com.softeng306.managers.StudentMgr;
import com.softeng306.validation.RegexValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Concrete implementation of {@code IStudentMgrIO}.
 * Provide methods to add a new student and print a student transcript
 */
public class StudentMgrIO implements IStudentMgrIO {
    private Scanner reader = new Scanner(System.in);
    private IStudentMgr studentMgr = StudentMgr.getInstance();

    @Override
    public void addStudent() {
        String studentID;
        printMenu();

        if (readWhetherToGenerateStudentId()) {
            studentID = studentMgr.generateStudentID();
        } else {
            studentID = readUnusedStudentId();
        }

        String studentName = readStudentName();
        String schoolName = readSchoolName();
        String studentGender = readStudentGender();
        int studentYear = readStudentYear();

        studentMgr.createNewStudent(studentID, studentName, schoolName, studentGender, studentYear);

        printStudentData(studentName, studentID);
    }


    @Override
    public void printStudentTranscript() {
        String studentId = readExistingStudentID();

        int studentsAcademicUnits;
        String studentName;
        // Get the student's academic units and name
        try {
            studentsAcademicUnits = StudentCourseMarkMgr.getInstance().getAcademicUnitsForStudent(studentId);
            studentName = studentMgr.getStudentName(studentId);
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Start printing the student transcript
        if (!studentMgr.studentHasCourses(studentId)) {
            System.out.println("------ No transcript ready for this student yet ------");
            return;
        }
        System.out.println("----------------- Official Transcript ------------------");
        System.out.print("Student Name: " + studentName);
        System.out.println("\tStudent ID: " + studentId);
        System.out.println("AU for this semester: " + studentsAcademicUnits);
        System.out.println();

        List<String> stringList = StudentCourseMarkMgr.getInstance().getMarkMessageForStudent(studentId, studentsAcademicUnits);
        stringList.forEach(System.out::println);

        System.out.println("------------------ End of Transcript -------------------");

    }

    @Override
    public String readExistingStudentID() {
        String studentID;

        // Loop input until the user enters student id of an existing student
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = reader.nextLine();

            while ("-h".equals(studentID)) {
                StudentMgr.getInstance().printAllStudentIds();
                studentID = reader.nextLine();
            }

            if (!studentMgr.studentExists(studentID)) {
                System.out.println("Invalid Student ID. Please re-enter.");
            } else {
                break;
            }

        }
        return studentID;
    }

    /**
     * Allows the user to enter the students name.
     *
     * @return students name
     */
    private String readStudentName() {
        String studentName;
        // Loop until user enters student name which is valid (matches regex)
        while (true) {
            System.out.println("Enter student Name: ");
            studentName = reader.nextLine();
            if (RegexValidator.checkValidStudentNameInput(studentName)) {
                return studentName;
            }
        }
    }

    /**
     * Allows the user to enter the students school.
     *
     * @return students school
     */
    private String readSchoolName() {
        String studentSchool;
        // Loop reading student school until department currently exists
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = reader.nextLine();
            while ("-h".equals(studentSchool)) {
                List<String> allDepartmentNames = Department.getListOfAllDepartmentNames();
                printAllStringsInListByIndex(allDepartmentNames);
                studentSchool = reader.nextLine();
            }

            if (Department.contains(studentSchool)) {
                return studentSchool;
            } else {
                System.out.println("The department is invalid. Please re-enter.");
            }
        }
    }

    /**
     * Allows the user to enter the students gender.
     *
     * @return students gender
     */
    private String readStudentGender() {
        String studentGender;
        // Loop until user enters gender which is valid/exists
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = reader.nextLine();
            while ("-h".equals(studentGender)) {
                printAllStringsInListByIndex(Gender.getListOfAllGenderNames());
                studentGender = reader.nextLine();
            }

            if (Gender.contains(studentGender)) {
                return studentGender;
            } else {
                System.out.println("The gender is invalid. Please re-enter.");
            }
        }
    }

    /**
     * Allows the user to enter the students year level.
     *
     * @return students year level
     */
    private int readStudentYear() {
        int studentYear;
        // Loop until user enters year level between 1 and 4
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

    /**
     * Prints a students data to console, given their student ID.
     */
    private void printStudentData(String name, String ID) {
        System.out.println("Student named: " + name + " is added, with ID: " + ID);
        System.out.println("Student List: ");
        System.out.println("| Student ID | Student Name | Student School | Gender | Year | GPA |");
        List<String> studentInformationStrings = studentMgr.generateStudentInformationStrings();
        studentInformationStrings.forEach(System.out::println);
    }

    /**
     * Prints all the strings in the input list by its index
     * (starting from 1), and a :
     * e.g. 1: inputString
     */
    private void printAllStringsInListByIndex(List<String> list) {
        for (int index = 1; index <= list.size(); index++) {
            String name = list.get(index - 1);
            System.out.println(index + ": " + name);
        }
    }

    /**
     * Displays the menu for adding a student on the console.
     */
    private void printMenu() {
        MainMenuIO.printMethodCall("addStudent");
        System.out.println("Choose the way you want to add a student:");
        System.out.println("1. Manually input the student ID.");
        System.out.println("2. Let the system self-generate the student ID.");
    }

    /**
     * Allows the user to choose whether they want to students ID to be auto-generated.
     *
     * @return true if the system is to auto-generate the students ID, false for the user to manually enter the students ID
     */
    private boolean readWhetherToGenerateStudentId() {
        int choice;
        // Loop until user enters 1 or 2. 1 represents that the user would like to manually
        // enter a student id, and 2 represents the user would like to generate a student id
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

    /**
     * Allows the user to enter the students ID.
     *
     * @return student ID
     */
    private String readUnusedStudentId() {
        // Loops until the user enters a student id which is not used by an existing student and
        // is valid (regex)
        while (true) {
            System.out.println("The student ID should follow:");
            System.out.println("Length is exactly 9");
            System.out.println("Start with U (Undergraduate)");
            System.out.println("End with a uppercase letter between A and L");
            System.out.println("Seven digits in the middle");
            System.out.println();
            System.out.println("Give this student an ID: ");
            String studentID = reader.nextLine();
            // Check the studentId is valid and is also not used by a current student
            if (RegexValidator.checkValidStudentIDInput(studentID)) {
                if (!studentMgr.studentExists(studentID)) {
                    return studentID;
                } else {
                    System.out.println("Sorry. The student ID is used. This student already exists.");
                }
            }
        }
    }
}
