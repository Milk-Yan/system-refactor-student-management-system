package com.softeng306.io;

import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.enums.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.professor.Professor;
import com.softeng306.managers.CourseMgr;
import com.softeng306.managers.ProfessorMgr;
import com.softeng306.validation.CourseValidator;
import com.softeng306.validation.DepartmentValidator;
import com.softeng306.validation.GroupValidator;
import com.softeng306.validation.ProfessorValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseMgrIO {
    private Scanner scanner = new Scanner(System.in);

    private MarkCalculator markCalculator = new MarkCalculator();

    /**
     * Read in a courseId from the user
     *
     * @return String courseId
     */
    public String readCourseId() {
        String courseID;
        // Can make the sameCourseID as boolean, set to false.
        while (true) {
            System.out.println("Give this course an ID: ");
            courseID = scanner.nextLine();
            if (CourseValidator.checkValidCourseIDInput(courseID)) {
                // Check course ID does not already exist for a course
                if (CourseValidator.getCourseFromId(courseID) == null) {
                    break;
                }
                System.out.println("Sorry. The course ID is used. This course already exists.");
            }
        }

        return courseID;
    }

    /**
     * Read in a course name from the user
     *
     * @return String courseName
     */
    public String readCourseName() {
        System.out.println("Enter course Name: ");
        return scanner.nextLine();
    }


    /**
     * Read in the total number of seats for a course
     *
     * @return int totalSeats for a course
     */
    public int readTotalSeats() {
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

    /**
     * Read in the number of academic units for a course
     *
     * @return int AU representing number of Academic Units
     */
    public int readAU() {
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

    /**
     * Read in a course department for a course from the user
     *
     * @return String courseDepartment for a course
     */
    public String readCourseDepartment() {
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

    /**
     * Read in a course type from the user
     *
     * @return String courseType for a course
     */
    public String readCourseType() {
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

    /**
     * Read in the number of groups for a particular stream (lecture, lab, tutorial)
     *
     * @param type       the type of group we are reading in
     * @param compareTo  the upper limit to compare it to
     * @param totalSeats the total number of seats available
     * @return the number of groups the user has inputted
     */
    public int readNoOfGroup(GroupType type, int compareTo, int totalSeats) {
        int noOfGroups;

        while (true) {
            if (type == GroupType.TutorialGroup) {
                System.out.println("Enter the number of " + type.toTypeString().toLowerCase() + " groups:");
            } else {
                System.out.println("Enter the number of " + type.toTypeString().toLowerCase() + " groups: ");
            }
            if (scanner.hasNextInt()) {
                noOfGroups = scanner.nextInt();
                scanner.nextLine();
                boolean checkLimit;
                if (type == GroupType.LectureGroup) {
                    checkLimit = noOfGroups > 0 && noOfGroups <= totalSeats;
                } else {
                    checkLimit = noOfGroups >= 0 && compareTo <= totalSeats;
                }
                if (checkLimit) break;
                System.out.println("Invalid input.");
                printInvalidNoGroup(type);
                System.out.println("Please re-enter");
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return noOfGroups;
    }

    /**
     * Print out a message to console saying that the number of groups is invalid
     *
     * @param type the type of the group to output the message for
     */
    private void printInvalidNoGroup(GroupType type) {
        if (type == GroupType.LabGroup) {
            System.out.println("Number of lab group must be non-negative.");
        } else if (type == GroupType.LectureGroup) {
            System.out.println("Number of lecture group must be positive but less than total seats in this course.");
        } else if (type == GroupType.TutorialGroup) {
            System.out.println("Number of tutorial group must be non-negative.");
        }
    }

    /**
     * Reads in a weekly hour for a group from the user
     *
     * @param type the type of the group the weekly hour is for
     * @param AU   the number of academic units for the course
     * @return int the number of weekly hours for that group
     */
    public int readWeeklyHour(GroupType type, int AU) {
        int weeklyHour;
        while (true) {
            System.out.format("Enter the weekly %s hour for this course: %n", type.toTypeString().toLowerCase());
            if (scanner.hasNextInt()) {
                weeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (weeklyHour < 0 || weeklyHour > AU) {
                    System.out.format("Weekly %s hour out of bound. Please re-enter.%n", type.toTypeString().toLowerCase());
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return weeklyHour;
    }

    /**
     * Reads in and constructs lecture groups for a course from the user
     *
     * @param totalSeats        the total number of seats for the course
     * @param noOfLectureGroups the number of lecture groups for a course
     * @return the lecture groups the user has specified
     */
    public List<Group> readLectureGroups(int totalSeats, int noOfLectureGroups) {
        String lectureGroupName;
        int lectureGroupCapacity;
        int seatsLeft = totalSeats;
        boolean groupNameExists;

        List<Group> lectureGroups = new ArrayList<>();

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
                if (lectureGroups.isEmpty()) {
                    break;
                }
                for (Group lectureGroup : lectureGroups) {
                    if (lectureGroup.getGroupName().equals(lectureGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lecture group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);


            while (true) {
                System.out.println("Enter this lecture group's capacity: ");
                while (true) {
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
                }
                seatsLeft -= lectureGroupCapacity;
                if ((seatsLeft > 0 && i != (noOfLectureGroups - 1)) || (seatsLeft == 0 && i == noOfLectureGroups - 1)) {
                    Group lectureGroup = new Group(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity, GroupType.LectureGroup);

                    lectureGroups.add(lectureGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lecture groups exceeds or does not add up to the total seats for this course.");
                    System.out.println("Please re-enter the capacity for the last lecture group " + lectureGroupName + " you have entered.");
                    seatsLeft += lectureGroupCapacity;
                }
            }
        }

        return lectureGroups;
    }

    /**
     * Reads in and constructs tutorial groups for a course from the user
     *
     * @param totalSeats         the total number of seats for the course
     * @param noOfTutorialGroups the number of tutorial groups for a course
     * @return the tutorial groups the user has specified
     */
    public List<Group> readTutorialGroups(int noOfTutorialGroups, int totalSeats) {
        List<Group> tutorialGroups = new ArrayList<>();
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
                if (tutorialGroups.isEmpty()) {
                    break;
                }
                for (Group tutorialGroup : tutorialGroups) {
                    if (tutorialGroup.getGroupName().equals(tutorialGroupName)) {
                        groupNameExists = true;
                        System.out.println("This tutorial group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            while (true) {
                System.out.println("Enter this tutorial group's capacity: ");
                if (scanner.hasNextInt()) {
                    tutorialGroupCapacity = scanner.nextInt();
                    scanner.nextLine();
                    totalTutorialSeats += tutorialGroupCapacity;
                    if ((i != noOfTutorialGroups - 1) || (totalTutorialSeats >= totalSeats)) {
                        Group tutorialGroup = new Group(tutorialGroupName, tutorialGroupCapacity, tutorialGroupCapacity, GroupType.TutorialGroup);
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
            }
        }

        return tutorialGroups;
    }

    /**
     * Reads in and constructs lab groups for a course from the user
     *
     * @param totalSeats    the total number of seats for the course
     * @param noOfLabGroups the number of lab groups for a course
     * @return the lab groups the user has specified
     */
    public List<Group> readLabGroups(int noOfLabGroups, int totalSeats) {
        List<Group> labGroups = new ArrayList<>();
        int totalLabSeats = 0;
        String labGroupName;
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
                if (labGroups.isEmpty()) {
                    break;
                }
                for (Group labGroup : labGroups) {
                    if (labGroup.getGroupName().equals(labGroupName)) {
                        groupNameExists = true;
                        System.out.println("This lab group already exist for this course.");
                        break;
                    }
                }
            } while (groupNameExists);

            while (true) {
                System.out.println("Enter this lab group's capacity: ");
                int labGroupCapacity = scanner.nextInt();
                scanner.nextLine();
                totalLabSeats += labGroupCapacity;
                if ((i != noOfLabGroups - 1) || (totalLabSeats >= totalSeats)) {
                    Group labGroup = new Group(labGroupName, labGroupCapacity, labGroupCapacity, GroupType.LabGroup);
                    labGroups.add(labGroup);
                    break;
                } else {
                    System.out.println("Sorry, the total capacity you allocated for all the lab groups is not enough for this course.");
                    System.out.println("Please re-enter the capacity for the last lab group " + labGroupName + " you have entered.");
                    totalLabSeats -= labGroupCapacity;
                }
            }
        }

        return labGroups;
    }


    /**
     * Read in a professor from the user for a particular course department
     *
     * @param courseDepartment the course department that the professor should be in
     * @return Professor the professor the user has specified
     */
    public Professor readProfessor(Department courseDepartment) {
        List<String> professorsInDepartment = ProfessorMgr.getInstance().getAllProfIDInDepartment(courseDepartment);
        String profID;
        Professor profInCharge;


        while (true) {
            System.out.println("Enter the ID for the professor in charge please:");
            System.out.println("Enter -h to print all the professors in " + courseDepartment + ".");
            profID = scanner.nextLine();
            while ("-h".equals(profID)) {
                ProfessorMgrIO.printAllProfIDsInDepartment(professorsInDepartment);
                profID = scanner.nextLine();
            }

            profInCharge = ProfessorValidator.checkProfExists(profID);
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

    /**
     * Reads in the user's choice for creating components now
     *
     * @return int the choice the user specified for creating a component
     */
    public int readCreateCourseComponentChoice() {
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

    /**
     * Prints a success message that a course has been successfully added
     *
     * @param courseID the course to print the success message for
     */
    public void printCourseAdded(String courseID) {
        System.out.println("Course " + courseID + " is added");
    }

    /**
     * Prints an information message that the components have not been initialized
     *
     * @param courseID the course that the components have not been initialized for
     */
    public void printComponentsNotInitialized(String courseID) {
        System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
    }

    /**
     * Print course information to the console for a user
     *
     * @param course the course to print the info for
     */
    public void printCourseInfo(Course course) {
        System.out.println(course.getCourseID() + " " + course.getCourseName() + " (Available/Total): " + course.getVacancies() + "/" + course.getTotalSeats());
        System.out.println("--------------------------------------------");
        printVacanciesForGroups(course.getLectureGroups(), GroupType.LectureGroup);

        if (course.getTutorialGroups() != null) {
            System.out.println();
            printVacanciesForGroups(course.getTutorialGroups(), GroupType.TutorialGroup);
        }
        if (course.getLabGroups() != null) {
            System.out.println();
            printVacanciesForGroups(course.getLabGroups(), GroupType.LabGroup);
        }
        System.out.println();
    }

    /**
     * Helper method to print out the vacancies for groups
     *
     * @param groups the groups to print
     * @param type   the type of the groups
     */
    private void printVacanciesForGroups(List<Group> groups, GroupType type) {
        for (Group group : groups) {
            if (type == GroupType.TutorialGroup) {
                System.out.format("%s group %s (Available/Total):  %d/%d%n",
                        type.toTypeString(), group.getGroupName(), group.getAvailableVacancies(), group.getTotalSeats());
            } else {
                System.out.format("%s group %s (Available/Total): %d/%d%n",
                        type.toTypeString(), group.getGroupName(), group.getAvailableVacancies(), group.getTotalSeats());
            }

        }
    }

    /**
     * Print out an error message that the course specified does not exist
     */
    public void printCourseNotExist() {
        System.out.println("This course does not exist. Please check again.");
    }

    /**
     * Reads in the users choice for whether a course should have a final exam
     *
     * @return int whether the user wants to have a final exam for a course
     */
    public int readHasFinalExamChoice() {
        int hasFinalExamChoice;

        System.out.println("Does this course have a final exam? Enter your choice:");
        System.out.println("1. Yes! ");
        System.out.println("2. No, all CAs.");
        hasFinalExamChoice = scanner.nextInt();
        scanner.nextLine();

        return hasFinalExamChoice;
    }

    /**
     * Reads in an exam weighting for a course from the user
     *
     * @return int the exam weight
     */
    public int readExamWeight() {
        int examWeight;

        System.out.println("Please enter weight of the exam: ");
        examWeight = scanner.nextInt();
        scanner.nextLine();
        while (examWeight > 80 || examWeight <= 0) {
            if (examWeight > 80 && examWeight <= 100) {
                System.out.println("According to the course assessment policy, final exam cannot take up more than 80%...");
            }
            System.out.println("Weight entered is invalid, please enter again: ");
            examWeight = scanner.nextInt();
            scanner.nextLine();
        }

        return examWeight;
    }

    /**
     * Print out a message to the user asking to enter assessments
     */
    public void printEnterContinuousAssessments() {
        System.out.println("Okay, please enter some continuous assessments");
    }

    /**
     * Reads in the number of main components for a course from the user
     *
     * @return int number of main components specified
     */
    public int readNoOfMainComponents() {
        int numberOfMain;

        while (true) {
            System.out.println("Enter number of main component(s) to add:");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of main component(s) to add:");
            }
            numberOfMain = scanner.nextInt();
            if (numberOfMain < 0) {
                System.out.println("Please enter a valid positive integer:");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return numberOfMain;
    }

    /**
     * Reads in the main component weightage from the user
     *
     * @param i              the number entry for the main component
     * @param totalWeightage the current total weightage
     * @return the main component weightage
     */
    public int readMainComponentWeightage(int i, int totalWeightage) {
        int weight;
        while (true) {
            System.out.println("Enter main component " + (i + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter main component " + (i + 1) + " weightage:");
            }
            weight = scanner.nextInt();
            if (weight < 0 || weight > totalWeightage) {
                System.out.println("Please enter a weight between 0 ~ " + totalWeightage + ":");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return weight;
    }

    /**
     * Reads in the number of sub components for a main component from the user
     *
     * @param mainComponentNo the main component number the sub components are for
     * @return int the number of sub components
     */
    public int readNoOfSubComponents(int mainComponentNo) {
        int noOfSub;

        while (true) {
            System.out.println("Enter number of sub component under main component " + (mainComponentNo + 1) + ":");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of sub component under main component " + (mainComponentNo + 1) + ":");
            }
            noOfSub = scanner.nextInt();
            if (noOfSub < 0) {
                System.out.println("Please enter a valid integer:");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return noOfSub;
    }

    /**
     * Reads in the sub component weightage from the user
     *
     * @param subComponentNo the sub component to read the weight for
     * @param sub_totWeight  the total subcomponent weightage
     * @return int the sub component weight
     */
    public int readSubWeight(int subComponentNo, int sub_totWeight) {
        int sub_weight;
        while (true) {
            System.out.println("Enter sub component " + (subComponentNo + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter sub component " + (subComponentNo + 1) + " weightage (out of the main component): ");
            }
            sub_weight = scanner.nextInt();
            if (sub_weight < 0 || sub_weight > sub_totWeight) {
                System.out.println("Please enter a weight between 0 ~ " + sub_totWeight + ":");
                continue;
            }
            break;
        }
        scanner.nextLine();

        return sub_weight;
    }

    /**
     * Prints an error when the sub components do not add to 100
     */
    public void printSubComponentWeightageError() {
        System.out.println("ERROR! sub component weightage does not tally to 100");
        System.out.println("You have to reassign!");
    }

    /**
     * Prints an error that the total weightage does not add to 100
     */
    public void printWeightageError() {
        System.out.println("Weightage assigned does not tally to 100!");
        System.out.println("You have to reassign!");
    }

    /**
     * Prints the components for a course
     *
     * @param course the course to print the components for
     */
    public void printComponentsForCourse(Course course) {
        System.out.println(course.getCourseID() + " " + course.getCourseName() + " components: ");
        for (MainComponent each_comp : course.getMainComponents()) {
            System.out.println("    " + each_comp.getComponentName() + " : " + each_comp.getComponentWeight() + "%");
            for (SubComponent each_sub : each_comp.getSubComponents()) {
                System.out.println("        " + each_sub.getComponentName() + " : " + each_sub.getComponentWeight() + "%");
            }
        }
    }

    /**
     * Read in the name for a main component from the user
     *
     * @param totalWeightage  the total weightage for a course
     * @param mainComponentNo the main component number
     * @param mainComponents  the main components
     * @return the main component name
     */
    public String readMainComponentName(int totalWeightage, int mainComponentNo, List<MainComponent> mainComponents) {
        boolean componentExist;
        String mainComponentName;

        do {
            componentExist = false;
            System.out.println("Total weightage left to assign: " + totalWeightage);
            System.out.println("Enter main component " + (mainComponentNo + 1) + " name: ");
            mainComponentName = scanner.nextLine();

            if (mainComponents.isEmpty()) {
                break;
            }
            if (mainComponentName.equals("Exam")) {
                System.out.println("Exam is a reserved assessment.");
                componentExist = true;
                continue;
            }
            for (MainComponent mainComponent : mainComponents) {
                if (mainComponent.getComponentName().equals(mainComponentName)) {
                    componentExist = true;
                    System.out.println("This sub component already exist. Please enter.");
                    break;
                }
            }
        } while (componentExist);

        return mainComponentName;
    }

    /**
     * Read in the sub components specified by a user for a course
     *
     * @param noOfSub the number of subcomponents
     * @return List of sub components user has specified
     */
    public List<SubComponent> readSubComponents(int noOfSub) {
        List<SubComponent> subComponents = new ArrayList<>();

        boolean flagSub = true;
        int subWeight;
        String subComponentName;
        boolean componentExist;

        while (flagSub) {

            int sub_totWeight = 100;
            for (int j = 0; j < noOfSub; j++) {
                do {
                    componentExist = false;
                    System.out.println("Total weightage left to assign to sub component: " + sub_totWeight);
                    System.out.println("Enter sub component " + (j + 1) + " name: ");
                    subComponentName = scanner.nextLine();

                    if (subComponents.isEmpty()) {
                        break;
                    }
                    if (subComponentName.equals("Exam")) {
                        System.out.println("Exam is a reserved assessment.");
                        componentExist = true;
                        continue;
                    }
                    for (SubComponent subComponent : subComponents) {
                        if (subComponent.getComponentName().equals(subComponentName)) {
                            componentExist = true;
                            System.out.println("This sub component already exist. Please enter.");
                            break;
                        }
                    }
                } while (componentExist);

                subWeight = readSubWeight(j, sub_totWeight);

                //Create Subcomponent
                SubComponent sub = new SubComponent(subComponentName, subWeight);
                subComponents.add(sub);
                sub_totWeight -= subWeight;
            }
            if (sub_totWeight != 0 && noOfSub != 0) {
                printSubComponentWeightageError();
                subComponents.clear();
                flagSub = true;
            } else {
                flagSub = false;
            }
            //exit if weight is fully allocated
        }

        return subComponents;
    }

    /**
     * Read in a sub component name from the user
     *
     * @param subComponents  the current sub components
     * @param sub_totWeight  the total sub component weight
     * @param subComponentNo the sub component number
     * @return String the name of the sub component specified
     */
    public String readSubComponentName(List<SubComponent> subComponents, int sub_totWeight, int subComponentNo) {
        boolean componentExist;
        String subComponentName;
        do {
            componentExist = false;
            System.out.println("Total weightage left to assign to sub component: " + sub_totWeight);
            System.out.println("Enter sub component " + (subComponentNo + 1) + " name: ");
            subComponentName = scanner.nextLine();

            if (subComponents.isEmpty()) {
                break;
            }
            if (subComponentName.equals("Exam")) {
                System.out.println("Exam is a reserved assessment.");
                componentExist = true;
                continue;
            }
            for (SubComponent subComponent : subComponents) {
                if (subComponent.getComponentName().equals(subComponentName)) {
                    componentExist = true;
                    System.out.println("This sub component already exist. Please enter.");
                    break;
                }
            }
        } while (componentExist);

        return subComponentName;
    }

    /**
     * Print an info message that the course specified is empty
     */
    public void printEmptyCourseComponents(Course course) {
        System.out.println("Currently course " + course.getCourseID() + " " + course.getCourseName() + " does not have any assessment component.");
    }

    /**
     * Print courses
     *
     * @param courses the courses to print
     */
    public void printCourses(List<Course> courses) {
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (Course course : courses) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }

    /**
     * Print all the course ids
     *
     * @param courses the courses to print ids for
     */
    public void printAllCourseIds(List<Course> courses) {
        for (Course course : courses) {
            String courseID = course.getCourseID();
            System.out.println(courseID);
        }
    }

    /**
     * Print error that course assessment is settled
     */
    public void printCourseworkWeightageEnteredError() {
        System.out.println("Course Assessment has been settled already!");
    }

    /**
     * Print course statistics header for a particular course
     *
     * @param course the course to print statistics for
     */
    public void printCourseStatisticsHeader(Course course) {
        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + course.getCourseID() + "\tCourse Name: " + course.getCourseName());
        System.out.println("Course AU: " + course.getAU());
        System.out.println();
        System.out.print("Total Slots: " + course.getTotalSeats());
        int enrolledNumber = (course.getTotalSeats() - course.getVacancies());
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double) enrolledNumber / (double) course.getTotalSeats() * 100d));
        System.out.println();
    }

    /**
     * Print a main component to the user
     *
     * @param mainComponent the main component to print
     * @param courseMarks   needed to calculate the average mark
     */
    public void printMainComponent(MainComponent mainComponent, List<Mark> courseMarks) {
        System.out.print("Main Component: " + mainComponent.getComponentName());
        System.out.print("\tWeight: " + mainComponent.getComponentWeight() + "%");

        MarkCalculator markCalculator = new MarkCalculator();
        System.out.println("\t Average: " + markCalculator.computeComponentMark(courseMarks, mainComponent.getComponentName()));
    }

    /**
     * Print statistics for subcomponents
     *
     * @param subComponents the subcomponents to print out
     * @param courseMarks   needed to calculate the average mark
     */
    public void printSubcomponents(List<SubComponent> subComponents, List<Mark> courseMarks) {
        for (SubComponent subComponent : subComponents) {
            printSubComponentInfo(subComponent);
            System.out.println("\t Average: " + markCalculator.computeComponentMark(courseMarks, subComponent.getComponentName()));
        }
        System.out.println();
    }

    /**
     * Print the info for a sub component
     *
     * @param subComponent the sub component to print the info for
     */
    public void printSubComponentInfo(SubComponent subComponent) {
        System.out.print("Sub Component: " + subComponent.getComponentName());
        System.out.print("\tWeight: " + subComponent.getComponentWeight() + "% (in main component)");
    }

    /**
     * Print statistics for an exam to the user
     *
     * @param exam        the exam to print statistics on
     * @param courseMarks needed to calculate average
     */
    public void printExamStatistics(MainComponent exam, List<Mark> courseMarks) {
        System.out.print("Final Exam");
        System.out.print("\tWeight: " + exam.getComponentWeight() + "%");
        System.out.println("\t Average: " + markCalculator.computeExamMark(courseMarks));
    }

    /**
     * Print message that course has no exam
     */
    public void printNoExamMessage() {
        System.out.println("This course does not have final exam.");
    }

    /**
     * Print overall performance based on course marks
     *
     * @param courseMarks the coursemarks to calculate statistics for
     */
    public void printOverallPerformance(List<Mark> courseMarks) {
        System.out.println();
        System.out.print("Overall Performance: ");
        System.out.printf("%4.2f \n", markCalculator.computerOverallMark(courseMarks));

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();
    }

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public Course readCourseFromUser() {
        String courseID;
        Course currentCourse;
        while (true) {
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while ("-h".equals(courseID)) {
                printAllCourseIds(CourseMgr.getInstance().getCourses());
                courseID = scanner.nextLine();
            }

            currentCourse = CourseValidator.getCourseFromId(courseID);
            if (currentCourse == null) {
                System.out.println("Invalid Course ID. Please re-enter.");
            } else {
                break;
            }
        }
        return currentCourse;
    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public String readDepartmentFromUser() {
        String courseDepartment;
        while (true) {
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                Department.printAllDepartment();
                courseDepartment = scanner.nextLine();
            }
            if (DepartmentValidator.checkDepartmentValidation(courseDepartment)) {
                List<String> validCourseString;
                validCourseString = CourseMgr.getInstance().getCourseIdsInDepartment(courseDepartment);
                if (validCourseString.size() == 0) {
                    System.out.println("Invalid choice of department.");
                } else {
                    break;
                }
            }
        }
        return courseDepartment;
    }

}
