package com.softeng306.io;

import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.group.LabGroup;
import com.softeng306.domain.course.group.LectureGroup;
import com.softeng306.domain.course.group.TutorialGroup;
import com.softeng306.domain.professor.Professor;
import com.softeng306.managers.ProfessorMgr;
import com.softeng306.validation.CourseValidator;
import com.softeng306.validation.DepartmentValidator;
import com.softeng306.validation.GroupValidator;
import com.softeng306.validation.ProfessorValidator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseMgrIO {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    public static String readCourseId() {
        String courseID;
        // Can make the sameCourseID as boolean, set to false.
        while (true) {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
            if (CourseValidator.checkValidCourseIDInput(courseID)) {
                if (CourseValidator.checkCourseExists(courseID) == null) {
                    break;
                }
            }
        }

        return courseID;
    }

    public static String readCourseName() {
        System.out.println("Enter course Name: ");
        String courseName = scanner.nextLine();
        return courseName;
    }

    public static int readTotalSeats() {
        int totalSeats;
        while (true) {
            System.out.println("Enter the total vacancy of this course: ");
            if (scanner.hasNextInt()) {
                totalSeats = scanner.nextInt();
                if (totalSeats <= 0) {
                    System.out.println("Please enter a valid vacancy (greater than 0)");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                System.out.println("Please re-enter");
            }
        }

        return totalSeats;
    }

    public static int readAU() {
        int AU;
        while (true) {
            System.out.println("Enter number of academic unit(s): ");
            if (scanner.hasNextInt()) {
                AU = scanner.nextInt();
                scanner.nextLine();
                if (AU < 0 || AU > 10) {
                    System.out.println("AU out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return AU;
    }

    public static String readCourseDepartment() {
        String courseDepartment;
        while (true) {
            System.out.println("Enter course's department (uppercase): ");
            System.out.println("Enter -h to print all the departments.");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                Department.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
            if (DepartmentValidator.checkDepartmentValidation(courseDepartment)) {
                break;
            }
        }

        return courseDepartment;
    }

    public static String readCourseType() {
        String courseType;
        while (true) {
            System.out.println("Enter course type (uppercase): ");
            System.out.println("Enter -h to print all the course types.");
            courseType = scanner.nextLine();
            while (courseType.equals("-h")) {
                CourseType.printAllCourseType();
                courseType = scanner.nextLine();
            }
            if (CourseValidator.checkCourseTypeValidation(courseType)) {
                break;
            }
        }
        return courseType;
    }

    public static int readNoOfLectureGroups(int totalSeats) {
        int noOfLectureGroups;
        do {
            System.out.println("Enter the number of lecture groups: ");
            // lecture group number cannot be 0 and also cannot be larger than totalSeats
            if (scanner.hasNextInt()) {
                noOfLectureGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLectureGroups > 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lecture group must be positive but less than total seats in this course.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);

        return noOfLectureGroups;
    }

    public static int readLecWeeklyHour(int AU) {
        int lecWeeklyHour;
        while (true) {
            System.out.println("Enter the weekly lecture hour for this course: ");
            if (scanner.hasNextInt()) {
                lecWeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (lecWeeklyHour < 0 || lecWeeklyHour > AU) {
                    System.out.println("Weekly lecture hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return lecWeeklyHour;
    }

    public static List<LectureGroup> readLectureGroups(int totalSeats, int noOfLectureGroups) {
        String lectureGroupName;
        int lectureGroupCapacity;
        int seatsLeft = totalSeats;
        boolean groupNameExists;

        List<LectureGroup> lectureGroups = new ArrayList<>();

        for (int i = 0; i < noOfLectureGroups; i++) {
            System.out.println("Give a name to the lecture group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                lectureGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(lectureGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (lectureGroups.size() == 0) {
                    break;
                }
                for (LectureGroup lectureGroup : lectureGroups) {
                    if (lectureGroup.getGroupName().equals(lectureGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lecture group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);


            do {
                System.out.println("Enter this lecture group's capacity: ");
                do {
                    if (scanner.hasNextInt()) {
                        lectureGroupCapacity = scanner.nextInt();
                        scanner.nextLine();
                        if (lectureGroupCapacity > 0) {
                            break;
                        }
                        System.out.println("Capacity must be positive. Please re-enter.");
                    } else {
                        System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                    }
                } while (true);
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    LectureGroup lectureGroup = new LectureGroup(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity);

                    lectureGroups.add(lectureGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            } while (true);
        }

        return lectureGroups;
    }

    public static int readNoOfTutorialGroups(int noOfLectureGroups, int totalSeats) {
        int noOfTutorialGroups;
        do {
            System.out.println("Enter the number of tutorial groups:");
            if (scanner.hasNextInt()) {
                noOfTutorialGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfTutorialGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of tutorial group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);

        return noOfTutorialGroups;
    }

    public static int readTutWeeklyHour(int AU) {
        int tutWeeklyHour;
        while (true) {
            System.out.println("Enter the weekly tutorial hour for this course: ");
            if (scanner.hasNextInt()) {
                tutWeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (tutWeeklyHour < 0 || tutWeeklyHour > AU) {
                    System.out.println("Weekly tutorial hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return tutWeeklyHour;
    }

    public static List<TutorialGroup> readTutorialGroups(int noOfTutorialGroups, int totalSeats) {
        List<TutorialGroup> tutorialGroups = new ArrayList<>();
        String tutorialGroupName;
        int tutorialGroupCapacity;
        boolean groupNameExists;
        int totalTutorialSeats = 0;

        for (int i = 0; i < noOfTutorialGroups; i++) {
            System.out.println("Give a name to the tutorial group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                tutorialGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(tutorialGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (tutorialGroups.size() == 0) {
                    break;
                }
                for (TutorialGroup tutorialGroup : tutorialGroups) {
                    if (tutorialGroup.getGroupName().equals(tutorialGroupName)) {
                        groupNameExists = true;
                        System.out.println("This tutorial group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            do {
                System.out.println("Enter this tutorial group's capacity: ");
                if (scanner.hasNextInt()) {
                    tutorialGroupCapacity = scanner.nextInt();
                    scanner.nextLine();
                    totalTutorialSeats += tutorialGroupCapacity;
                    if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                        TutorialGroup tutorialGroup = new TutorialGroup(tutorialGroupName, tutorialGroupCapacity, tutorialGroupCapacity);
                        tutorialGroups.add(tutorialGroup);
                        break;
                    } else {
                        System.out.println("Sorry, the total capacity you allocated for all the tutorial groups is not enough for this course.");
                        System.out.println("Please re-enter the capacity for the last tutorial group " + tutorialGroupName + " you have entered.");
                        totalTutorialSeats -= tutorialGroupCapacity;
                    }
                } else {
                    System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
                }
            } while (true);
        }

        return tutorialGroups;
    }

    public static int readNoOfLabGroups(int noOfLectureGroups, int totalSeats) {
        int noOfLabGroups;
        do {
            System.out.println("Enter the number of lab groups: ");
            if (scanner.hasNextInt()) {
                noOfLabGroups = scanner.nextInt();
                scanner.nextLine();
                if (noOfLabGroups >= 0 && noOfLectureGroups <= totalSeats) {
                    break;
                }
                System.out.println("Invalid input.");
                System.out.println("Number of lab group must be non-negative.");
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        } while (true);

        return noOfLabGroups;
    }

    public static int readLabWeeklyHour(int AU) {
        int labWeeklyHour;
        while (true) {
            System.out.println("Enter the weekly lab hour for this course: ");
            if (scanner.hasNextInt()) {
                labWeeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (labWeeklyHour < 0 || labWeeklyHour > AU) {
                    System.out.println("Weekly lab hour out of bound. Please re-enter.");
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return labWeeklyHour;
    }

    public static List<LabGroup> readLabGroups(int noOfLabGroups, int totalSeats) {
        List<LabGroup> labGroups = new ArrayList<>();
        int totalLabSeats = 0;
        String labGroupName;
        int labGroupCapacity;
        boolean groupNameExists;
        for (int i = 0; i < noOfLabGroups; i++) {
            System.out.println("Give a name to this lab group");
            do {
                groupNameExists = false;
                System.out.println("Enter a group Name: ");
                labGroupName = scanner.nextLine();
                if (!GroupValidator.checkValidGroupNameInput(labGroupName)) {
                    groupNameExists = true;
                    continue;
                }
                if (labGroups.size() == 0) {
                    break;
                }
                for (LabGroup labGroup : labGroups) {
                    if (labGroup.getGroupName().equals(labGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lab group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            do {
                System.out.println("Enter this lab group's capacity: ");
                labGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalLabSeats += labGroupCapacity;
                if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                    LabGroup labGroup = new LabGroup(labGroupName, labGroupCapacity, labGroupCapacity);
                    labGroups.add(labGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lab groups is not enough for this course.");
                    System.out.println("Please re-enter the capacity for the last lab group " + labGroupName + " you have entered.");
                    totalLabSeats -= labGroupCapacity;
                }
            } while (true);
        }

        return labGroups;
    }

    public static Professor readProfessor(String courseDepartment) {
        // TODO: Fix name of method
        List<String> professorsInDepartment = ProfessorMgr.getInstance().printProfInDepartment(courseDepartment, false);
        String profID;
        Professor profInCharge;


        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                professorsInDepartment = ProfessorMgr.getInstance().printProfInDepartment(courseDepartment, true);
                profID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            profInCharge = ProfessorValidator.checkProfExists(profID);
            System.setOut(originalStream);
            if (profInCharge != null) {
                if (professorsInDepartment.contains(profID)) {
                    break;
                } else {
                    System.out.println("This prof is not in " + courseDepartment + ".");
                    System.out.println("Thus he/she cannot teach this course.");
                }
            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
        }

        return profInCharge;
    }

    public static int readCreateCourseComponentChoice() {
        // TODO: Use enum instead of int to store choice
        int addCourseComponentChoice;
        System.out.println("Create course components and set component weightage now?");
        System.out.println("1. Yes");
        System.out.println("2. Not yet");
        addCourseComponentChoice = scanner.nextInt();
        scanner.nextLine();

        while (addCourseComponentChoice > 2 || addCourseComponentChoice < 0) {
            System.out.println("Invalid choice, please choose again.");
            System.out.println("1. Yes");
            System.out.println("2. Not yet");
            addCourseComponentChoice = scanner.nextInt();
            scanner.nextLine();
        }

        return addCourseComponentChoice;
    }

    public static void printCourseAdded(String courseID) {
        System.out.println("Course " + courseID + " is added");
    }

    public static void printComponentsNotInitialized(String courseID) {
        System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
    }

    public static void printCourseInfo(Course course) {
        System.out.println(course.getCourseID() + " " + course.getCourseName() + " (Available/Total): " + course.getVacancies() + "/" + course.getTotalSeats());
        System.out.println("--------------------------------------------");
        for (LectureGroup lectureGroup : course.getLectureGroups()) {
            System.out.println("Lecture group " + lectureGroup.getGroupName() + " (Available/Total): " + lectureGroup.getAvailableVacancies() + "/" + lectureGroup.getTotalSeats());
        }
        if (course.getTutorialGroups() != null) {
            System.out.println();
            for (TutorialGroup tutorialGroup : course.getTutorialGroups()) {
                System.out.println("Tutorial group " + tutorialGroup.getGroupName() + " (Available/Total):  " + tutorialGroup.getAvailableVacancies() + "/" + tutorialGroup.getTotalSeats());
            }
        }
        if (course.getLabGroups() != null) {
            System.out.println();
            for (LabGroup labGroup : course.getLabGroups()) {
                System.out.println("Lab group " + labGroup.getGroupName() + " (Available/Total): " + labGroup.getAvailableVacancies() + "/" + labGroup.getTotalSeats());
            }
        }
        System.out.println();
    }

    public static void printCourseNotExist() {
        System.out.println("This course does not exist. Please check again.");
    }
}
