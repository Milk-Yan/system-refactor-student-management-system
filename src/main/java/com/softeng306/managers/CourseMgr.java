package com.softeng306.managers;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.CourseBuilder;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.professor.Professor;
import com.softeng306.domain.student.Student;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.enums.GroupType;
import com.softeng306.io.CourseMgrIO;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.MainMenuIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        ICourseBuilder builder = new CourseBuilder();

        // Read in parameters to create new Course
        String courseID = courseMgrIO.readCourseId();
        builder.setCourseID(courseID);

        String courseName = courseMgrIO.readCourseName();
        builder.setCourseName(courseName);

        int totalSeats = courseMgrIO.readTotalSeats();
        builder.setTotalSeats(totalSeats);

        int AU = courseMgrIO.readAU();
        builder.setAU(AU);

        Department courseDepartment = courseMgrIO.readCourseDepartment();
        builder.setCourseDepartment(courseDepartment);

        CourseType courseType = courseMgrIO.readCourseType();

        builder.setCourseType(courseType);

        // Lecture groups
        int noOfLectureGroups = courseMgrIO.readNoOfGroup(GroupType.LECTURE_GROUP, totalSeats, totalSeats);
        int lecWeeklyHour = courseMgrIO.readWeeklyHour(GroupType.LECTURE_GROUP, AU);
        List<Group> lectureGroups = courseMgrIO.readLectureGroups(totalSeats, noOfLectureGroups);
        builder.setLecWeeklyHour(lecWeeklyHour);
        builder.setLectureGroups(lectureGroups);

        // Tutorial groups
        int noOfTutorialGroups = courseMgrIO.readNoOfGroup(GroupType.TUTORIAL_GROUP, noOfLectureGroups, totalSeats);
        int tutWeeklyHour = 0;
        if (noOfTutorialGroups != 0) {
            tutWeeklyHour = courseMgrIO.readWeeklyHour(GroupType.TUTORIAL_GROUP, AU);
        }
        List<Group> tutorialGroups = courseMgrIO.readTutorialGroups(noOfTutorialGroups, totalSeats);
        builder.setTutWeeklyHour(tutWeeklyHour);
        builder.setTutorialGroups(tutorialGroups);

        // Lab groups
        int noOfLabGroups = courseMgrIO.readNoOfGroup(GroupType.LAB_GROUP, noOfLectureGroups, totalSeats);
        int labWeeklyHour = 0;
        if (noOfLabGroups != 0) {
            labWeeklyHour = courseMgrIO.readWeeklyHour(GroupType.LAB_GROUP, AU);
        }
        List<Group> labGroups = courseMgrIO.readLabGroups(noOfLabGroups, totalSeats);
        builder.setLabWeeklyHour(labWeeklyHour);
        builder.setLabGroups(labGroups);

        Professor profInCharge = courseMgrIO.readProfessor(courseDepartment);
        builder.setProfInCharge(profInCharge);

        Course course = builder.build();

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
        //printout the result directly
        MainMenuIO.printMethodCall("checkAvailableSlots");

        while (true) {
            Course currentCourse = readCourseFromUser();
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
        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.

        MainMenuIO.printMethodCall("enterCourseWorkComponentWeightage");
        if (currentCourse == null) {
            currentCourse = readCourseFromUser();
        }

        List<MainComponent> mainComponents = new ArrayList<>();
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
                    MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>());
                    mainComponents.add(exam);
                } else if (hasFinalExamChoice == 2) {
                    courseMgrIO.printEnterContinuousAssessments();
                }
            }

            int numberOfMain = courseMgrIO.readNoOfMainComponents();

            while (true) {
                int totalWeightage = 100 - examWeight;
                for (int i = 0; i < numberOfMain; i++) {
                    List<SubComponent> subComponents = new ArrayList<>();

                    String mainComponentName = courseMgrIO.readMainComponentName(totalWeightage, i, mainComponents);

                    int weight = courseMgrIO.readMainComponentWeightage(i, totalWeightage);
                    totalWeightage -= weight;

                    int noOfSub = courseMgrIO.readNoOfSubComponents(i);

                    boolean flagSub = true;
                    while (flagSub) {

                        int sub_totWeight = 100;
                        for (int j = 0; j < noOfSub; j++) {

                            String subComponentName = courseMgrIO.getComponentName(sub_totWeight, subComponents, j, SubComponent.COMPONENT_NAME);

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

    public List<String> getCourseIdsInDepartment(Department department) {
        List<Course> validCourses = new ArrayList<>();
        courses.forEach(course -> {
            if (department.toString().equals(course.getCourseDepartment().toString())) {
                validCourses.add(course);
            }
        });

        List<String> courseIdsForDepartment = new ArrayList<>();
        validCourses.forEach(course -> {
            String courseID = course.getCourseID();
            courseIdsForDepartment.add(courseID);
        });
        return courseIdsForDepartment;
    }

    /**
     * Prints the course statics including enrollment rate, average result for every assessment component and the average overall performance of this course.
     */
    public void printCourseStatistics() {
        MainMenuIO.printMethodCall("printCourseStatistics");

        Course currentCourse = readCourseFromUser();
        String courseID = currentCourse.getCourseID();

        List<Mark> courseMarks = new ArrayList<>();
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                courseMarks.add(mark);
            }
        }

        courseMgrIO.printCourseStatisticsHeader(currentCourse);

        MainComponent exam = null;

        // Find marks for every assessment components
        for (MainComponent mainComponent : currentCourse.getMainComponents()) {
            String componentName = mainComponent.getComponentName();

            if (componentName.equals("Exam")) {
//                Leave the exam report to the last
                exam = mainComponent;
            } else {
                courseMgrIO.printMainComponent(mainComponent, courseMarks);
                List<SubComponent> subComponents = mainComponent.getSubComponents();
                if (!subComponents.isEmpty()) {
                    courseMgrIO.printSubcomponents(subComponents, courseMarks);
                }
            }
        }

        if (exam != null) {
            courseMgrIO.printExamStatistics(exam, courseMarks);
        } else {
            courseMgrIO.printNoExamMessage();
        }
        courseMgrIO.printOverallPerformance(courseMarks);
    }

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public Course readCourseFromUser() {
        return courseMgrIO.readCourseFromUser();
    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public String readDepartmentFromUser() {
        return courseMgrIO.readDepartmentFromUser();
    }

    /**
     * Return the list of all courses in the system.
     *
     * @return An list of all courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourseFromId(String courseId) {
        Optional<Course> optionalCourse = courses.stream().filter(c -> courseId.equals(c.getCourseID())).findFirst();
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            return null;
        }
    }

    public String getCourseName(String courseId) {
        Course course = getCourseFromId(courseId);
        return course.getCourseName();
    }

    public boolean doesCourseHaveExam(String courseId) {
        Course course = getCourseFromId(courseId);
        for (MainComponent mainComponent : course.getMainComponents()) {
            if (mainComponent.getComponentName().equals("Exam")) {
                return true;
            }
        }
        return false;
    }

    public MainComponent getExamFromCourse(String courseId) {
        Course course = getCourseFromId(courseId);
        for (MainComponent mainComponent : course.getMainComponents()) {
            if (mainComponent.getComponentName().equals("Exam")) {
                return mainComponent;
            }
        }
        return null;
    }


    public int getExamWeight(String courseId) {
        MainComponent exam = getExamFromCourse(courseId);
        return exam.getComponentWeight();
    }

    public double getExamMark(String courseId, String studentId) {
        Student student = StudentMgr.getInstance().getStudentFromId(studentId);
        Course course = getCourseFromId(courseId);
//        return course.getExamMarkForStudent(student);
        return 0;
    }

    public double getOverallMark(String courseId, String studentId) {
        Student student = StudentMgr.getInstance().getStudentFromId(studentId);
        Course course = getCourseFromId(courseId);
//        return course.getAssessmentPortfolio().getOverallMark(student);
        return 0;
    }

    public MainComponent getMainComponentFromName(String courseId, String mainComponentName) {
        Course course = getCourseFromId(courseId);
        List<MainComponent> mainComponents = course.getMainComponents();
        Optional<MainComponent> optionalMainComponent = mainComponents.stream().filter(m -> mainComponentName.equals(m.getComponentName())).findFirst();
        if (optionalMainComponent.isPresent()) {
            return optionalMainComponent.get();
        } else {
            return null;
        }
    }

    public List<String> getMainComponentNames(String courseId) {
        Course course = getCourseFromId(courseId);
        List<String> mainComponentNames = new ArrayList<>();
        for (MainComponent mainComponent : course.getMainComponents()) {
            mainComponentNames.add(mainComponent.getComponentName());
        }
        return mainComponentNames;
    }

    public double getMainComponentWeight(String courseId, String mainComponentName) {
        MainComponent mainComponent = getMainComponentFromName(courseId, mainComponentName);
        return mainComponent.getComponentWeight();
    }

    public SubComponent getSubComponentFromName(String courseId, String mainComponentName, String subComponentName) {
        MainComponent mainComponent = getMainComponentFromName(courseId, mainComponentName);
        List<SubComponent> subComponents = mainComponent.getSubComponents();
        Optional<SubComponent> optionalSubComponent = subComponents.stream().filter(s -> subComponentName.equals(s.getComponentName())).findFirst();
        if (optionalSubComponent.isPresent()) {
            return optionalSubComponent.get();
        } else {
            return null;
        }
    }

}