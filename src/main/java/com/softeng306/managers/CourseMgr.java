package com.softeng306.managers;


import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.enums.CourseType;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;

import com.softeng306.fileprocessing.CourseFileProcessor;
import com.softeng306.fileprocessing.IFileProcessor;

import com.softeng306.enums.GroupType;

import com.softeng306.io.ICourseMgrIO;
import com.softeng306.io.MainMenuIO;
import com.softeng306.io.CourseMgrIO;


import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class CourseMgr {
    /**
     * A list of all the courses in this school.
     */
    private List<Course> courses;

    private static CourseMgr singleInstance = null;

    private final IFileProcessor<Course> courseFileProcessor;
    private MarkCalculator markCalculator = new MarkCalculator();

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseMgr() {
        courseFileProcessor = new CourseFileProcessor();
        courses = courseFileProcessor.loadFile();
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
    public void addCourse(ICourseBuilder completeBuilder) {
        ICourseMgrIO courseMgrIO = new CourseMgrIO();

        Course course = completeBuilder.build();
        int addCourseComponentChoice;

        // Update Course in files
        courseFileProcessor.writeNewEntryToFile(course);

        courses.add(course);

        addCourseComponentChoice = courseMgrIO.readCreateCourseComponentChoice();

        // Don't add course components option selected
        if (addCourseComponentChoice == 2) {
            courseMgrIO.printComponentsNotInitialisedMessage(course.getCourseID());
        } else {
            enterCourseWorkComponentWeightage(course);
            courseMgrIO.printCourseAdded(course.getCourseID());
        }
        courseMgrIO.printCourses(generateGeneralInformationForAllCourses());
    }

    /**
     * Checks whether a course (with all of its groups) have available slots and displays the result.
     */
    public void checkAvailableSlots() {
        ICourseMgrIO io = new CourseMgrIO();

        //printout the result directly
        MainMenuIO.printMethodCall("checkAvailableSlots");

        while (true) {
            Course currentCourse;
            try {
                currentCourse = readExistingCourse();
            } catch (CourseNotFoundException e) {
                e.printStackTrace();
                return;
            }

            if (currentCourse != null) {
                io.printCourseInfoString(generateCourseInformation(currentCourse));
                io.printVacanciesForGroups(currentCourse.generateLectureGroupInformation(), GroupType.LECTURE_GROUP.toString());

                if (currentCourse.getTutorialGroups() != null) {
                    io.printEmptySpace();
                    io.printVacanciesForGroups(currentCourse.generateTutorialGroupInformation(), GroupType.TUTORIAL_GROUP.toString());
                }

                if (currentCourse.getLabGroups() != null) {
                    io.printEmptySpace();
                    io.printVacanciesForGroups(currentCourse.generateLabGroupInformation(), GroupType.LAB_GROUP.toString());

                }
                io.printEmptySpace();
                break;
            } else {
                io.printCourseNotExist();
            }
        }
    }

    /**
     * Sets the course work component weightage of a course.
     *
     * @param currentCourse The course which course work component is to be set.
     */
    public void enterCourseWorkComponentWeightage(Course currentCourse) {
        ICourseMgrIO io = new CourseMgrIO();

        // Assume when course is created, no components are added yet
        // Assume once components are created and set, cannot be changed.

        MainMenuIO.printMethodCall("enterCourseWorkComponentWeightage");
        if (currentCourse == null) {
            try {
                currentCourse = readExistingCourse();
            } catch (CourseNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }

        // Make sure course has no components
        if (currentCourse.getMainComponents().isEmpty()) {
            // Course is empty, can create and add new components
            List<MainComponent> mainComponents = addMainComponentsToCourse(io, currentCourse);
            currentCourse.setMainComponents(mainComponents);
        } else {
            io.printCourseworkWeightageEnteredError();
        }

        io.printComponentsForCourse(currentCourse.getCourseID(), currentCourse.getCourseName(), generateComponentInformationForACourses(currentCourse));

        // Update course into course.csv
    }

    /**
     * Creates components for a given course through user input
     *
     * @param io            A CourseMgrIO to use for output
     * @param currentCourse The course to create components for
     * @return
     */
    private List<MainComponent> addMainComponentsToCourse(ICourseMgrIO io, Course currentCourse) {
        List<MainComponent> mainComponents = new ArrayList<>(0);

        io.printEmptyCourseComponents(currentCourse.getCourseID(), currentCourse.getCourseName());
        int examWeight = addExamComponent(io, mainComponents);

        int numberOfMainComponents = io.readNoOfMainComponents();

        while (true) {
            int totalWeightage = addMainComponents(io, examWeight, numberOfMainComponents, mainComponents);

            if (totalWeightage != 0) {
                // weightage assign is not tallied
                io.printWeightageError();
                mainComponents.clear();
            } else {
                break;
            }
        }

        return mainComponents;
    }

    /**
     * Creates new main components from user input
     *
     * @param io                     CourseMgrIO to use for user I/O
     * @param examWeight             Weight of associated course exam
     * @param numberOfMainComponents Number of main components to create
     * @param mainComponents         List of components to add main components to
     * @return
     */
    private int addMainComponents(ICourseMgrIO io, int examWeight, int numberOfMainComponents, List<MainComponent> mainComponents) {
        Set<String> mainComponentNames = new HashSet<>();
        int totalWeightage = 100 - examWeight;

        for (int i = 0; i < numberOfMainComponents; i++) {
            Map<String, Double> subComponentsMap;
            String mainComponentName = io.readMainComponentName(totalWeightage, i, mainComponentNames);

            mainComponentNames.add(mainComponentName);

            int weight = io.readMainComponentWeightage(i, totalWeightage);
            totalWeightage -= weight;

            int noOfSub = io.readNoOfSubComponents(i);
            subComponentsMap = io.readSubComponents(noOfSub);

            List<SubComponent> subComponentsList = new ArrayList<SubComponent>();
            for (String key : subComponentsMap.keySet()) {
                SubComponent subComponent = new SubComponent(key, subComponentsMap.get(key).intValue());
                subComponentsList.add(subComponent);
            }

            MainComponent main = new MainComponent(mainComponentName, weight, subComponentsList);
            mainComponents.add(main);
        }

        return totalWeightage;
    }

    /**
     * Creates an exam main component for a course
     *
     * @param io             CourseMgrIO to use for user I/O
     * @param mainComponents List of components to add exam to
     * @return
     */
    private int addExamComponent(ICourseMgrIO io, List<MainComponent> mainComponents) {
        int hasFinalExamChoice = 0;
        int examWeight = 0;

        while (hasFinalExamChoice < 1 || hasFinalExamChoice > 2) {
            hasFinalExamChoice = io.readHasFinalExamChoice();
            if (hasFinalExamChoice == 1) {
                examWeight = io.readExamWeight();
                MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>());
                mainComponents.add(exam);
            } else if (hasFinalExamChoice == 2) {
                io.printEnterContinuousAssessments();
            }
        }

        return examWeight;
    }

    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourseIds() {
        new CourseMgrIO().printAllCourseIds(generateListOfAllCourseIDs());
    }

    public List<String> getCourseIdsInDepartment(String departmentName) {
        List<Course> validCourses = new ArrayList<>();
        courses.forEach(course -> {
            if (departmentName.equals(course.getCourseDepartment().toString())) {
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
        ICourseMgrIO io = new CourseMgrIO();

        MainMenuIO.printMethodCall("printCourseStatistics");

        Course currentCourse;
        String courseID;
        try {
            currentCourse = readExistingCourse();
            courseID = currentCourse.getCourseID();
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return;
        }

        io.printCourseStatisticsHeader(generateCourseInformationFromCourse(currentCourse));

        MainComponent exam = null;

        // Find marks for every assessment components
        for (MainComponent mainComponent : currentCourse.getMainComponents()) {
            String componentName = mainComponent.getComponentName();

            if (componentName.equals("Exam")) {
//                Leave the exam report to the last
                exam = mainComponent;
            } else {
                io.printMainComponent(mainComponent.getComponentName(), mainComponent.getComponentWeight(), markCalculator.computeAverageMarkForCourseComponent(courseID, mainComponent.getComponentName()));
                List<SubComponent> subComponents = mainComponent.getSubComponents();
                if (!subComponents.isEmpty()) {
                    String[][] subComponentInformation = this.generateSubComponentInformation(subComponents);
                    Map<String, Double> subComponentMarks = this.generateComponentMarkInformation(subComponents, courseID);
                    io.printSubcomponents(subComponentInformation, subComponentMarks);
                }
            }
        }

        if (exam != null) {
            io.printExamStatistics(exam.getComponentWeight(), markCalculator.computeAverageMarkForCourseComponent(courseID, "Exam"));

        } else {
            io.printNoExamMessage();
        }

        io.printOverallPerformance(markCalculator.computeOverallMarkForCourse(courseID));
    }


    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public Course readExistingCourse() throws CourseNotFoundException {
        String validCourseID = new CourseMgrIO().readExistingCourseId();
        return getCourseFromId(validCourseID);
    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public String readExistingDepartment() {
        return new CourseMgrIO().readExistingDepartment();
    }

    /**
     * Return the list of all courses in the system.
     *
     * @return An list of all courses.
     */
    public List<Course> getCourses() {
        return courses;
    }


    public String generateCourseInformation(Course course) {
        String infoString = course.getCourseID() + " " + course.getCourseName() + " (Available/Total): " + course.getVacancies() + "/" + course.getTotalSeats();
        return infoString;
    }

    public String[][] generateSubComponentInformation(List<SubComponent> subComponents) {
        String[][] map = new String[subComponents.size()][2];
        int i = 0;
        for (SubComponent subComponent : subComponents) {
            map[i][0] = subComponent.getComponentName();
            map[i][1] = String.valueOf(subComponent.getComponentWeight());
            i++;
        }
        return map;
    }


    public Map<String, Double> generateComponentMarkInformation(List<SubComponent> subComponents, String courseID) {
        Map<String, Double> map = new HashMap<>();
        for (SubComponent subComponent : subComponents) {
            double mark = markCalculator.computeAverageMarkForCourseComponent(courseID, subComponent.getComponentName());
            map.put(subComponent.getComponentName(), mark);
        }
        return map;
    }

    public List<String> generateCourseInformationFromCourse(Course course) {
        List<String> courseInformation = new ArrayList<String>();
        courseInformation.add(course.getCourseID());
        courseInformation.add(course.getCourseName());
        courseInformation.add(String.valueOf(course.getAcademicUnits()));
        courseInformation.add(String.valueOf(course.getTotalSeats()));
        courseInformation.add(String.valueOf(course.getVacancies()));
        return courseInformation;
    }

    public List<String> generateListOfAllCourseIDs() {
        List<String> courseIDs = new ArrayList<>();
        for (Course course : courses) {
            courseIDs.add(course.getCourseID());
        }
        return courseIDs;
    }


    /**
     * Checks whether this course ID is used by other courses.
     *
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    public Course getCourseFromId(String courseID) throws CourseNotFoundException {
        Optional<Course> course = CourseMgr
                .getInstance()
                .getCourses()
                .stream()
                .filter(c -> courseID.equals(c.getCourseID()))
                .findAny();

        if (!course.isPresent()) {
            throw new CourseNotFoundException(courseID);
        }
        return course.get();
    }

    public Map<String, List<String>> generateGeneralInformationForAllCourses() {
        Map<String, List<String>> generalCourseInfoMap = new HashMap<>();
        for (Course course : courses) {
            List<String> generalCourseInfo = new ArrayList<>();
            generalCourseInfo.add(course.getCourseName());
            generalCourseInfo.add(course.getProfInCharge().getProfName());
            generalCourseInfoMap.put(course.getCourseID(), generalCourseInfo);
        }
        return generalCourseInfoMap;

    }

    public Map<Map<String, String>, Map<String, String>> generateComponentInformationForACourses(Course course) {
        Map<Map<String, String>, Map<String, String>> map = new HashMap<>();
        for (MainComponent eachComp : course.getMainComponents()) {
            Map<String, String> mainComponentInfo = new HashMap<>();
            mainComponentInfo.put(eachComp.getComponentName(), String.valueOf(eachComp.getComponentWeight()));

            Map<String, String> subComponentsInfo = new HashMap<>();
            for (SubComponent eachSub : eachComp.getSubComponents()) {
                subComponentsInfo.put(eachSub.getComponentName(), String.valueOf(eachSub.getComponentWeight()));
            }

            map.put(mainComponentInfo, subComponentsInfo);
        }
        return map;
    }

    public int getNumberOfLectureGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.LECTURE_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyLectureHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LECTURE_GROUP.toString(), academicUnits);
    }

    public int getNumberOfLabGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.LAB_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyLabHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LAB_GROUP.toString(), academicUnits);
    }

    public int getNumberOfTutorialGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.TUTORIAL_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyTutorialHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.TUTORIAL_GROUP.toString(), academicUnits);
    }

    public List<String> getListCourseTypes() {
        return CourseType.getListOfAllCourseTypeNames();
    }

    public String getMainComponentString() {
        return MainComponent.COMPONENT_NAME;
    }

    public String getSubComponentString() {
        return SubComponent.COMPONENT_NAME;
    }

    public boolean checkCourseExists(String courseID) {
        Optional<Course> course = courses.stream()
                .filter(c -> courseID.equals(c.getCourseID()))
                .findFirst();

        return course.isPresent();
    }
}