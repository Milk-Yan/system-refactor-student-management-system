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
        int weight;
        int noOfSub;
        int subWeight;

        System.out.println("enterCourseWorkComponentWeightage is called");
        if (currentCourse == null) {
            currentCourse = CourseValidator.checkCourseExists();
        }


        List<MainComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().isEmpty()) {
            // empty course
            System.out.println("Currently course " + currentCourse.getCourseID() + " " + currentCourse.getCourseName() + " does not have any assessment component.");

            int hasFinalExamChoice = 0;
            int examWeight = 0;
            while (hasFinalExamChoice < 1 || hasFinalExamChoice > 2) {
                hasFinalExamChoice = CourseMgrIO.readHasFinalExamChoice();
                if (hasFinalExamChoice == 1) {
                    examWeight = CourseMgrIO.readExamWeight();
                    MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>(0));
                    mainComponents.add(exam);
                } else if (hasFinalExamChoice == 2) {
                    CourseMgrIO.printEnterContinuousAssessments();
                }
            }

            int numberOfMain = CourseMgrIO.readNoOfMainComponents();

            boolean componentExist;
            String mainComponentName;
            String subComponentName;
            while (true) {
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

                    weight = CourseMgrIO.readMainComponentWeightage(i, totalWeightage);
                    totalWeightage -= weight;

                    noOfSub = CourseMgrIO.readNoOfSub(i);

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

                            subWeight = CourseMgrIO.readSubWeight(j, sub_totWeight);

                            //Create Subcomponent
                            SubComponent sub = new SubComponent(subComponentName, subWeight);
                            subComponents.add(sub);
                            sub_totWeight -= subWeight;
                        }
                        if (sub_totWeight != 0 && noOfSub != 0) {
                            CourseMgrIO.printSubComponentWeightageError();
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
                    CourseMgrIO.printWeightageError();
                    mainComponents.clear();
                } else {
                    break;
                }
            }

            //set maincomponent to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            System.out.println("Course Assessment has been settled already!");
        }

        CourseMgrIO.printComponentsForCourse(currentCourse);

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