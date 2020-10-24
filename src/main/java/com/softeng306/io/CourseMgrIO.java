package com.softeng306.io;

import com.softeng306.Enum.CourseType;
import com.softeng306.Enum.Department;
import com.softeng306.Enum.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.course.group.Group;
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
    private Scanner scanner = new Scanner(System.in);
    private PrintStream originalStream = System.out;
    private PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    public String readCourseId() {
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

    public String readCourseName() {
        System.out.println("Enter course Name: ");
        return scanner.nextLine();
    }

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

    public int readNoOfGroup(GroupType type, int compareTo, int totalSeats) {
        int noOfGroups;

        while (true) {
            System.out.println("Enter the number of " + type.toTypeString() + " groups: ");
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

    private void printInvalidNoGroup(GroupType type) {
        if (type == GroupType.LabGroup) {
            System.out.println("Number of lab group must be non-negative.");
        } else if (type == GroupType.LectureGroup) {
            System.out.println("Number of lecture group must be positive but less than total seats in this course.");
        } else if (type == GroupType.TutorialGroup) {
            System.out.println("Number of tutorial group must be non-negative.");
        }
    }

    public int readWeeklyHour(GroupType type, int AU) {
        int weeklyHour;
        while (true) {
            System.out.format("Enter the weekly %s hour for this course: %n", type.toTypeString());
            if (scanner.hasNextInt()) {
                weeklyHour = scanner.nextInt();
                scanner.nextLine();
                if (weeklyHour < 0 || weeklyHour > AU) {
                    System.out.format("Weekly %s hour out of bound. Please re-enter.%n", type.toTypeString());
                } else {
                    break;
                }
            } else {
                System.out.println("Your input " + scanner.nextLine() + " is not an integer.");
            }
        }

        return weeklyHour;
    }

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
                if (lectureGroups.size() == 0) {
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
                    Group lectureGroup = new Group(lectureGroupName, lectureGroupCapacity, lectureGroupCapacity, GroupType.LectureGroup);

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
                if (tutorialGroups.size() == 0) {
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

            do {
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
            } while (true);
        }

        return tutorialGroups;
    }

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
                if (labGroups.size() == 0) {
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


    public Professor readProfessor(String courseDepartment) {
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

    public void printCourseAdded(String courseID) {
        System.out.println("Course " + courseID + " is added");
    }

    public void printComponentsNotInitialized(String courseID) {
        System.out.println("Course " + courseID + " is added, but assessment components are not initialized.");
    }

    // TODO: Reduce duplication in this method
    public void printCourseInfo(Course course) {
        System.out.println(course.getCourseID() + " " + course.getCourseName() + " (Available/Total): " + course.getVacancies() + "/" + course.getTotalSeats());
        System.out.println("--------------------------------------------");
        for (Group lectureGroup : course.getLectureGroups()) {
            System.out.println("Lecture group " + lectureGroup.getGroupName() + " (Available/Total): " + lectureGroup.getAvailableVacancies() + "/" + lectureGroup.getTotalSeats());
        }
        if (course.getTutorialGroups() != null) {
            System.out.println();
            for (Group tutorialGroup : course.getTutorialGroups()) {
                System.out.println("Tutorial group " + tutorialGroup.getGroupName() + " (Available/Total):  " + tutorialGroup.getAvailableVacancies() + "/" + tutorialGroup.getTotalSeats());
            }
        }
        if (course.getLabGroups() != null) {
            System.out.println();
            for (Group labGroup : course.getLabGroups()) {
                System.out.println("Lab group " + labGroup.getGroupName() + " (Available/Total): " + labGroup.getAvailableVacancies() + "/" + labGroup.getTotalSeats());
            }
        }
        System.out.println();
    }

    public void printCourseNotExist() {
        System.out.println("This course does not exist. Please check again.");
    }

    public int readHasFinalExamChoice() {
        int hasFinalExamChoice;

        System.out.println("Does this course have a final exam? Enter your choice:");
        System.out.println("1. Yes! ");
        System.out.println("2. No, all CAs.");
        hasFinalExamChoice = scanner.nextInt();
        scanner.nextLine();

        return hasFinalExamChoice;
    }

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

    public void printEnterContinuousAssessments() {
        System.out.println("Okay, please enter some continuous assessments");
    }

    public int readNoOfMainComponents() {
        int numberOfMain;

        do {
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
        } while (true);
        scanner.nextLine();

        return numberOfMain;
    }

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

    public int readNoOfSub(int i) {
        int noOfSub;

        while (true) {
            System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter number of sub component under main component " + (i + 1) + ":");
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

    public int readSubWeight(int j, int sub_totWeight) {
        int sub_weight;
        do {
            System.out.println("Enter sub component " + (j + 1) + " weightage: ");
            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.println("Sorry. " + input + " is not an integer.");
                System.out.println("Enter sub component " + (j + 1) + " weightage (out of the main component): ");
            }
            sub_weight = scanner.nextInt();
            if (sub_weight < 0 || sub_weight > sub_totWeight) {
                System.out.println("Please enter a weight between 0 ~ " + sub_totWeight + ":");
                continue;
            }
            break;
        } while (true);
        scanner.nextLine();

        return sub_weight;
    }

    public void printSubComponentWeightageError() {
        System.out.println("ERROR! sub component weightage does not tally to 100");
        System.out.println("You have to reassign!");
    }

    public void printWeightageError() {
        System.out.println("Weightage assigned does not tally to 100!");
        System.out.println("You have to reassign!");
    }

    public void printComponentsForCourse(Course course) {
        System.out.println(course.getCourseID() + " " + course.getCourseName() + " components: ");
        for (MainComponent each_comp : course.getMainComponents()) {
            System.out.println("    " + each_comp.getComponentName() + " : " + each_comp.getComponentWeight() + "%");
            for (SubComponent each_sub : each_comp.getSubComponents()) {
                System.out.println("        " + each_sub.getComponentName() + " : " + each_sub.getComponentWeight() + "%");
            }
        }
    }

    public String readMainComponentName(int totalWeightage, int i, List<MainComponent> mainComponents) {
        boolean componentExist;
        String mainComponentName;

        do {
            componentExist = false;
            System.out.println("Total weightage left to assign: " + totalWeightage);
            System.out.println("Enter main component " + (i + 1) + " name: ");
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

                    if (subComponents.size() == 0) {
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

    public String readSubComponentName(List<SubComponent> subComponents, int sub_totWeight, int j) {
        boolean componentExist;
        String subComponentName;
        do {
            componentExist = false;
            System.out.println("Total weightage left to assign to sub component: " + sub_totWeight);
            System.out.println("Enter sub component " + (j + 1) + " name: ");
            subComponentName = scanner.nextLine();

            if (subComponents.size() == 0) {
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

    public void printEmptyCourseComponents(Course course) {
        System.out.println("Currently course " + course.getCourseID() + " " + course.getCourseName() + " does not have any assessment component.");
    }

    public void printCourses(List<Course> courses) {
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (Course course : courses) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }

    public void printAllCourseIds(List<Course> courses) {
        for (Course course : courses) {
            String courseID = course.getCourseID();
            System.out.println(courseID);
        }
    }

    public void printCourseworkWeightageEnteredError() {
        System.out.println("Course Assessment has been settled already!");
    }

}
