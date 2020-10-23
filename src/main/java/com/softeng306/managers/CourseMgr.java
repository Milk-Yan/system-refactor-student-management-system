package com.softeng306.managers;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.course.group.LabGroup;
import com.softeng306.domain.course.group.LectureGroup;
import com.softeng306.domain.course.group.TutorialGroup;
import com.softeng306.domain.professor.Professor;
import com.softeng306.io.CourseMgrIO;
import com.softeng306.io.FILEMgr;
import com.softeng306.validation.*;

import java.util.*;
import java.util.stream.Collectors;


public class CourseMgr {
    private Scanner scanner = new Scanner(System.in);

    /**
     * A list of all the courses in this school.
     */
    public static List<Course> courses = new ArrayList<>(0);

    private static CourseMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseMgr() {
    }

    /**
     * Return the CourseMgr singleton, if not initialised already, create an instance.
     *
     * @return CourseMgr the singleton instance
     */
    public static CourseMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new CourseMgr();
        }

        return singleInstance;
    }

    /**
     * Creates a new course and stores it in the file.
     */
    public void addCourse() {
        // Read in parameters to create new Course
        String courseID = CourseMgrIO.readCourseId();
        String courseName = CourseMgrIO.readCourseName();

        int totalSeats = CourseMgrIO.readTotalSeats();

        int AU = CourseMgrIO.readAU();

        String courseDepartment = CourseMgrIO.readCourseDepartment();

        String courseType = CourseMgrIO.readCourseType();

        int noOfLectureGroups = CourseMgrIO.readNoOfLectureGroups(totalSeats);

        int lecWeeklyHour = CourseMgrIO.readLecWeeklyHour(AU);

        List<LectureGroup> lectureGroups = CourseMgrIO.readLectureGroups(totalSeats, noOfLectureGroups);

        int noOfTutorialGroups = CourseMgrIO.readNoOfTutorialGroups(noOfLectureGroups, totalSeats);

        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = CourseMgrIO.readTutWeeklyHour(AU);
        }

        List<TutorialGroup> tutorialGroups = CourseMgrIO.readTutorialGroups(noOfTutorialGroups, totalSeats);


        int noOfLabGroups = CourseMgrIO.readNoOfLabGroups(noOfLectureGroups, totalSeats);

        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = CourseMgrIO.readLabWeeklyHour(AU);
        }

        List<LabGroup> labGroups = CourseMgrIO.readLabGroups(noOfLabGroups, totalSeats);

        Professor profInCharge = CourseMgrIO.readProfessor(courseDepartment);

        // Create new Course
        // TODO: Replace with builder
        Course course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, AU, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);
        // Update Course in files
        FILEMgr.writeCourseIntoFile(course);
        CourseMgr.courses.add(course);

        int addCourseComponentChoice = CourseMgrIO.readCreateCourseComponentChoice();

        // Don't add course components option selected
        if (addCourseComponentChoice == 2) {
            CourseMgrIO.printComponentsNotInitialized(courseID);
        } else {
            enterCourseWorkComponentWeightage(course);
            CourseMgrIO.printCourseAdded(courseID);
        }
        printCourses();
    }

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots() {
        // TODO: move to MainMenuIO
        System.out.println("checkAvailableSlots is called");
        Course currentCourse;

        while (true) {
            currentCourse = CourseValidator.checkCourseExists();
            if (currentCourse != null) {
                CourseMgrIO.printCourseInfo(currentCourse);
                break;
            } else {
                CourseMgrIO.printCourseNotExist();
            }
        }
    }

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public void enterCourseWorkComponentWeightage(Course currentCourse) {
        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.
        int numberOfMain;
        int weight;
        int noOfSub;
        int sub_weight;

        System.out.println("enterCourseWorkComponentWeightage is called");
        if (currentCourse == null) {
            currentCourse = CourseValidator.checkCourseExists();
        }


        List<MainComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().size() == 0) {
            // empty course
            System.out.println("Currently course " + currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " does not have any assessment component.");

            int hasFinalExamChoice;
            int examWeight = 0;
            while (true) {
                System.out.println("Does this course have a final exam? Enter your choice:");
                System.out.println("1. Yes! ");
                System.out.println("2. No, all CAs.");
                hasFinalExamChoice = scanner.nextInt();
                scanner.nextLine();
                if (hasFinalExamChoice == 1) {
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
                    MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>(0));
                    mainComponents.add(exam);
                    break;
                } else if (hasFinalExamChoice == 2) {
                    System.out.println("Okay, please enter some continuous assessments");
                    break;
                }
            }

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

            boolean componentExist;
            String mainComponentName;
            String subComponentName;
            do {
                int totalWeightage = 100 - examWeight;
                for (int i = 0; i < numberOfMain; i++) {
                    List<SubComponent> subComponents = new ArrayList<>(0);
                    do {
                        componentExist = false;
                        System.out.println("Total weightage left to assign: " + totalWeightage);
                        System.out.println("Enter main component " + (i + 1) + " name: ");
                        mainComponentName = scanner.nextLine();

                        if (mainComponents.size() == 0) {
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

                    do {
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
                    } while (true);
                    scanner.nextLine();
                    totalWeightage -= weight;
                    do {
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
                    } while (true);
                    scanner.nextLine();
                    boolean flagSub = true;
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

                            //Create Subcomponent

                            SubComponent sub = new SubComponent(subComponentName, sub_weight);
                            subComponents.add(sub);
                            sub_totWeight -= sub_weight;
                        }
                        if (sub_totWeight != 0 && noOfSub != 0) {
                            System.out.println("ERROR! sub component weightage does not tally to 100");
                            System.out.println("You have to reassign!");
                            subComponents.clear();
                            flagSub = true;
                        } else {
                            flagSub = false;
                        }
                        //exit if weight is fully allocated
                    }
                    //Create main component
                    MainComponent main = new MainComponent(mainComponentName, weight, subComponents);
                    mainComponents.add(main);
                }

                if (totalWeightage != 0) {
                    // weightage assign is not tallied
                    System.out.println("Weightage assigned does not tally to 100!");
                    System.out.println("You have to reassign!");
                    mainComponents.clear();
                } else {
                    break;
                }
            } while (true);


            //set maincomponent to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            System.out.println("Course Assessment has been settled already!");
        }
        System.out.println(currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " components: ");
        for (MainComponent each_comp : currentCourse.getMainComponents()) {
            System.out.println("    " + each_comp.getComponentName() + " : " + each_comp.getComponentWeight() + "%");
            for (SubComponent each_sub : each_comp.getSubComponents()) {
                System.out.println("        " + each_sub.getComponentName() + " : " + each_sub.getComponentWeight() + "%");
            }
        }
        // Update course into course.csv
    }

    /**
     * Prints the list of courses
     */
    public void printCourses() {
        System.out.println("Course List: ");
        System.out.println("| Course ID | Course Name | Professor in Charge |");
        for (Course course : CourseMgr.courses) {
            System.out.println("| " + course.getCourseID() + " | " + course.getCourseName() + " | " + course.getProfInCharge().getProfName() + " |");
        }
        System.out.println();
    }


    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourses() {
        CourseMgr.courses.stream().map(c -> c.getCourseID()).forEach(System.out::println);
    }

    // TODO: fix name of this method

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    public List<String> printCourseInDepartment(String department) {
        List<Course> validCourses = CourseMgr.courses.stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        List<String> validCourseString = validCourses.stream().map(c -> c.getCourseID()).collect(Collectors.toList());
        validCourseString.forEach(System.out::println);
        if (validCourseString.size() == 0) {
            System.out.println("None.");
        }
        return validCourseString;
    }

}