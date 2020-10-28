package com.softeng306.io;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.MainComponentMark;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.mark.SubComponentMark;
import com.softeng306.domain.student.Student;
import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.CourseRegistrationMgr;
import com.softeng306.managers.MarkMgr;
import com.softeng306.managers.StudentMgr;
import com.softeng306.validation.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentMgrIO {

    private Scanner reader = new Scanner(System.in);

    private StudentMgr studentMgr = StudentMgr.getInstance();

    /**
     * Adds a student and put the student into file
     */
    public void addStudent() {
        String studentID = null;
        printMenu();

        if (doesSystemGenerateId()) {
            studentID = studentMgr.generateStudentID();
        } else {
            studentID = getStudentID();
        }

        String studentName = getStudentName();
        String schoolName = getSchoolName();
        String studentGender = getStudentGender();
        int studentYear = getStudentYear();

        studentMgr.addStudent(studentID, studentName, schoolName, studentGender, studentYear);

        printStudentData(studentName, studentID);
    }


    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    public void printStudentTranscript() {
        String studentId = readStudentIdFromUser();

        int thisStudentAU = MarkMgr.getInstance().getAUForStudent(studentId);

        if (!studentMgr.studentHasCourses(studentId)) {
            System.out.println("------ No transcript ready for this student yet ------");
            return;
        }
        System.out.println("----------------- Official Transcript ------------------");
        System.out.print("Student Name: " + studentMgr.getStudentName(studentId));
        System.out.println("\tStudent ID: " + studentId);
        System.out.println("AU for this semester: " + thisStudentAU);
        System.out.println();

        List<String> stringList = MarkMgr.getInstance().getMarkStringForStudent(studentId, thisStudentAU);
        stringList.forEach(System.out::println);

        System.out.println("------------------ End of Transcript -------------------");

    }

    /**
     * Displays the menu for adding a student on the console.
     */
    public void printMenu() {
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
    public boolean doesSystemGenerateId() {
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

    /**
     * Allows the user to enter the students ID.
     *
     * @return student ID
     */
    public String getStudentID() {
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
            if (StudentValidator.checkValidStudentIDInput(studentID)) {
                if (StudentValidator.checkStudentExists(studentID) == null) {
                    return studentID;
                } else {
                    System.out.println("Sorry. The student ID is used. This student already exists.");
                }
            }
        }
    }

    /**
     * Allows the user to enter the students name.
     *
     * @return students name
     */
    public String getStudentName() {
        String studentName;
        while (true) {
            System.out.println("Enter student Name: ");
            studentName = reader.nextLine();
            if (StudentValidator.checkValidStudentNameInput(studentName)) {
                return studentName;
            }
        }
    }

    /**
     * Allows the user to enter the students school.
     *
     * @return students school
     */
    public String getSchoolName() {
        String studentSchool;
        while (true) {
            System.out.println("Enter student's school (uppercase): ");
            System.out.println("Enter -h to print all the schools.");
            studentSchool = reader.nextLine();
            while ("-h".equals(studentSchool)) {
                Department.printAllDepartment();
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
    public String getStudentGender() {
        String studentGender;
        while (true) {
            System.out.println("Enter student gender (uppercase): ");
            System.out.println("Enter -h to print all the genders.");
            studentGender = reader.nextLine();
            while ("-h".equals(studentGender)) {
                Gender.printAllGender();
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
    public int getStudentYear() {
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

    /**
     * Prints a students data to console, given their student ID.
     */
    public void printStudentData(String name, String ID) {
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

    /**
     * Prompts the user to input an ID for an existing student
     */
    public static String readExistingStudentIDFromUser() {
        String studentID;

        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = reader.nextLine();

            while ("-h".equals(studentID)) {
                StudentMgr.getInstance().printAllStudentIds();
                studentID = reader.nextLine();
            }

            if (StudentValidator.checkStudentExists(studentID) == null) {
                System.out.println("Invalid Student ID. Please re-enter.");
            } else {
                break;
            }

        }
        return studentID;
    }


    /**
     * Prompts the user to input an existing student.
     *
     * @return the inputted student.
     */
    public String readStudentIdFromUser() {
        String studentID;
        Student currentStudent = null;
        while (true) {
            System.out.println("Enter Student ID (-h to print all the student ID):");
            studentID = reader.nextLine();
            while ("-h".equals(studentID)) {
                StudentMgr.getInstance().printAllStudentIds();
                studentID = reader.nextLine();
            }
            List<String> existingStudentIds = studentMgr.getExistingStudentIds();

            if (!existingStudentIds.contains(studentID)) {
                System.out.println("Invalid Student ID. Please re-enter.");
            } else {
                break;
            }

        }
        return studentID;
    }
}
