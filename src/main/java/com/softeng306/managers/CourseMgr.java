package com.softeng306.managers;

import com.softeng306.Enum.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.component.CourseworkComponent;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.mark.MainComponentMark;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.professor.Professor;
import com.softeng306.io.CourseMgrIO;
import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.validation.*;

import java.util.*;
import java.util.stream.Collectors;


public class CourseMgr {
    /**
     * A list of all the courses in this school.
     */
    private List<Course> courses;

    private static CourseMgr singleInstance = null;

    private CourseMgrIO courseMgrIO = new CourseMgrIO();

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseMgr(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Return the CourseMgr singleton, if not initialised already, create an instance.
     *
     * @return CourseMgr the singleton instance
     */
    public static CourseMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new CourseMgr(FILEMgr.loadCourses());
        }

        return singleInstance;
    }

    /**
     * Creates a new course and stores it in the file.
     */
    public void addCourse() {
        // Read in parameters to create new Course
        String courseID = courseMgrIO.readCourseId();
        String courseName = courseMgrIO.readCourseName();

        int totalSeats = courseMgrIO.readTotalSeats();

        int AU = courseMgrIO.readAU();

        String courseDepartment = courseMgrIO.readCourseDepartment();

        String courseType = courseMgrIO.readCourseType();
        // TODO: Refactor methods for lecturegroups, tutorialgroups, and labgroups
        int noOfLectureGroups = courseMgrIO.readNoOfGroup(GroupType.LectureGroup, totalSeats, totalSeats);

        int lecWeeklyHour = courseMgrIO.readLecWeeklyHour(AU);

        List<Group> lectureGroups = courseMgrIO.readLectureGroups(totalSeats, noOfLectureGroups);

        int noOfTutorialGroups = courseMgrIO.readNoOfGroup(GroupType.TutorialGroup, noOfLectureGroups, totalSeats);

        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = courseMgrIO.readTutWeeklyHour(AU);
        }

        List<Group> tutorialGroups = courseMgrIO.readTutorialGroups(noOfTutorialGroups, totalSeats);

        int noOfLabGroups = courseMgrIO.readNoOfGroup(GroupType.LabGroup, noOfLectureGroups, totalSeats);

        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = courseMgrIO.readLabWeeklyHour(AU);
        }

        List<Group> labGroups = courseMgrIO.readLabGroups(noOfLabGroups, totalSeats);

        Professor profInCharge = courseMgrIO.readProfessor(courseDepartment);

        // Create new Course
        // TODO: Replace with builder
        Course course = new Course(courseID, courseName, profInCharge, totalSeats, totalSeats, lectureGroups, tutorialGroups, labGroups, AU, courseDepartment, courseType, lecWeeklyHour, tutWeeklyHour, labWeeklyHour);
        // Update Course in files
        FILEMgr.writeCourseIntoFile(course);
        courses.add(course);

        int addCourseComponentChoice = courseMgrIO.readCreateCourseComponentChoice();

        // Don't add course components option selected
        if (addCourseComponentChoice == 2) {
            courseMgrIO.printComponentsNotInitialized(courseID);
        } else {
            enterCourseWorkComponentWeightage(course);
            courseMgrIO.printCourseAdded(courseID);
        }
        printCourses();
    }

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots() {
        // TODO: move to MainMenuIO
        System.out.println("checkAvailableSlots is called");

        while (true) {
            Course currentCourse = CourseValidator.checkCourseExists();
            if (currentCourse != null) {
                courseMgrIO.printCourseInfo(currentCourse);
                break;
            } else {
                courseMgrIO.printCourseNotExist();
            }
        }
    }

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public void enterCourseWorkComponentWeightage(Course currentCourse) {
        System.out.println("enterCourseWorkComponentWeightage is called");
        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.

        if (currentCourse == null) {
            currentCourse = CourseValidator.checkCourseExists();
        }

        List<MainComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().isEmpty()) {
            // empty course
            courseMgrIO.printEmptyCourseComponents(currentCourse);

            int hasFinalExamChoice = 0;
            int examWeight = 0;
            while (hasFinalExamChoice < 1 || hasFinalExamChoice > 2) {
                hasFinalExamChoice = courseMgrIO.readHasFinalExamChoice();
                if (hasFinalExamChoice == 1) {
                    examWeight = courseMgrIO.readExamWeight();
                    MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>(0));
                    mainComponents.add(exam);
                } else if (hasFinalExamChoice == 2) {
                    courseMgrIO.printEnterContinuousAssessments();
                }
            }

            int numberOfMain = courseMgrIO.readNoOfMainComponents();

            while (true) {
                int totalWeightage = 100 - examWeight;
                for (int i = 0; i < numberOfMain; i++) {
                    List<SubComponent> subComponents = new ArrayList<>(0);

                    String mainComponentName = courseMgrIO.readMainComponentName(totalWeightage, i, mainComponents);

                    int weight = courseMgrIO.readMainComponentWeightage(i, totalWeightage);
                    totalWeightage -= weight;

                    int noOfSub = courseMgrIO.readNoOfSub(i);

                    boolean flagSub = true;
                    while (flagSub) {

                        int sub_totWeight = 100;
                        for (int j = 0; j < noOfSub; j++) {

                            String subComponentName = courseMgrIO.readSubComponentName(subComponents, sub_totWeight, j);

                            int subWeight = courseMgrIO.readSubWeight(j, sub_totWeight);

                            //Create Subcomponent
                            SubComponent sub = new SubComponent(subComponentName, subWeight);
                            subComponents.add(sub);
                            sub_totWeight -= subWeight;
                        }
                        if (sub_totWeight != 0 && noOfSub != 0) {
                            courseMgrIO.printSubComponentWeightageError();
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
                    courseMgrIO.printWeightageError();
                    mainComponents.clear();
                } else {
                    break;
                }
            }

            //set maincomponent to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            courseMgrIO.printCourseworkWeightageEnteredError();
        }

        courseMgrIO.printComponentsForCourse(currentCourse);

        // Update course into course.csv
    }

    /**
     * Prints the list of courses
     */
    public void printCourses() {
        courseMgrIO.printCourses(courses);
    }

    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourseIds() {
        courseMgrIO.printAllCourseIds(courses);
    }

    public List<String> getCourseIdsInDepartment(String department) {
        List<Course> validCourses = courses.stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        List<String> courseIdsForDepartment = validCourses.stream().map(c -> c.getCourseID()).collect(Collectors.toList());
        return courseIdsForDepartment;
    }

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    public void printCourseStatistics() {
        System.out.println("printCourseStatistics is called");

        Course currentCourse = CourseValidator.checkCourseExists();
        String courseID = currentCourse.getCourseID();

        List<Mark> thisCourseMark = new ArrayList<>(0);
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                thisCourseMark.add(mark);
            }
        }

        System.out.println("*************** Course Statistic ***************");
        System.out.println("Course ID: " + currentCourse.getCourseID() + "\tCourse Name: " + currentCourse.getCourseName());
        System.out.println("Course AU: " + currentCourse.getAU());
        System.out.println();
        System.out.print("Total Slots: " + currentCourse.getTotalSeats());
        int enrolledNumber = (currentCourse.getTotalSeats() - currentCourse.getVacancies());
        System.out.println("\tEnrolled Student: " + enrolledNumber);
        System.out.printf("Enrollment Rate: %4.2f %%\n", ((double) enrolledNumber / (double) currentCourse.getTotalSeats() * 100d));
        System.out.println();


        int examWeight = 0;
        boolean hasExam = false;
        double averageMark = 0;
        // Find marks for every assessment components
        for (CourseworkComponent courseworkComponent : currentCourse.getMainComponents()) {
            String thisComponentName = courseworkComponent.getComponentName();

            if (thisComponentName.equals("Exam")) {
                examWeight = courseworkComponent.getComponentWeight();
//                Leave the exam report to the last
                hasExam = true;
            } else {
                averageMark = 0;
                System.out.print("Main Component: " + courseworkComponent.getComponentName());
                System.out.print("\tWeight: " + courseworkComponent.getComponentWeight() + "%");

                averageMark += MarkMgr.getInstance().computeMark(thisCourseMark, thisComponentName);

                averageMark = averageMark / thisCourseMark.size();
                System.out.println("\t Average: " + averageMark);

                List<SubComponent> thisSubComponents = ((MainComponent) courseworkComponent).getSubComponents();
                if (thisSubComponents.size() == 0) {
                    continue;
                }
                for (SubComponent subComponent : thisSubComponents) {
                    averageMark = 0;
                    System.out.print("Sub Component: " + subComponent.getComponentName());
                    System.out.print("\tWeight: " + subComponent.getComponentWeight() + "% (in main component)");
                    String thisSubComponentName = subComponent.getComponentName();

                    averageMark += MarkMgr.getInstance().computeMark(thisCourseMark, thisSubComponentName);

                    averageMark = averageMark / thisCourseMark.size();
                    System.out.println("\t Average: " + averageMark);
                }
                System.out.println();
            }

        }

        if (hasExam) {
            averageMark = 0;
            System.out.print("Final Exam");
            System.out.print("\tWeight: " + examWeight + "%");
            for (Mark mark : thisCourseMark) {
                List<MainComponentMark> courseMarks = mark.getCourseWorkMarks();

                for (MainComponentMark mainComponentMark : courseMarks) {
                    MainComponent mainComponent = mainComponentMark.getMainComponent();
                    double value = mainComponentMark.getMark();
                    if (mainComponent.getComponentName().equals("Exam")) {
                        averageMark += value;
                        break;
                    }
                }
            }
            averageMark = averageMark / thisCourseMark.size();
            System.out.println("\t Average: " + averageMark);
        } else {
            System.out.println("This course does not have final exam.");
        }


        System.out.println();

        System.out.print("Overall Performance: ");
        averageMark = 0;
        for (Mark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        averageMark = averageMark / thisCourseMark.size();
        System.out.printf("%4.2f \n", averageMark);

        System.out.println();
        System.out.println("***********************************************");
        System.out.println();
    }


     /* Return the list of all courses in the system.
     * @return An list of all courses.
     */
    public List<Course> getCourses(){
        return courses;
    }

}