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

public class CourseMgr implements ICourseMgr {
    /**
     * A list of all the courses in this school.
     */
    private List<Course> courses;
    private static CourseMgr singleInstance;

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

    @Override
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
            courseMgrIO.printComponentsNotInitialisedMessage(course.getCourseId());
        } else {
            enterCourseWorkComponentWeightage(course);
            courseMgrIO.printCourseAdded(course.getCourseId());
        }
        courseMgrIO.printCourses(generateGeneralInformationForAllCourses());
    }

    @Override
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

    @Override
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

        io.printComponentsForCourse(currentCourse.getCourseId(), currentCourse.getName(), generateComponentInformationForACourses(currentCourse));

        // Update course into course.csv
    }

    @Override
    public void printAllCourseIds() {
        new CourseMgrIO().printAllCourseIds(generateListOfAllCourseIDs());
    }

    @Override
    public List<String> getCourseIdsInDepartment(String departmentName) {
        List<Course> validCourses = new ArrayList<>();
        courses.forEach(course -> {
            if (departmentName.equals(course.getDepartment().toString())) {
                validCourses.add(course);
            }
        });

        List<String> courseIdsForDepartment = new ArrayList<>();
        validCourses.forEach(course -> {
            String courseID = course.getCourseId();
            courseIdsForDepartment.add(courseID);
        });
        return courseIdsForDepartment;
    }

    @Override
    public void printCourseStatistics() {
        ICourseMgrIO io = new CourseMgrIO();

        MainMenuIO.printMethodCall("printCourseStatistics");

        Course currentCourse;
        String courseID;
        try {
            currentCourse = readExistingCourse();
            courseID = currentCourse.getCourseId();
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return;
        }

        io.printCourseStatisticsHeader(generateCourseInformationFromCourse(currentCourse));

        MainComponent exam = null;

        // Find marks for every assessment components
        for (MainComponent mainComponent : currentCourse.getMainComponents()) {
            String componentName = mainComponent.getName();

            if (componentName.equals("Exam")) {
//                Leave the exam report to the last
                exam = mainComponent;
            } else {
                io.printMainComponent(mainComponent.getName(), mainComponent.getWeight(), markCalculator.computeAverageMarkForCourseComponent(courseID, mainComponent.getName()));
                List<SubComponent> subComponents = mainComponent.getSubComponents();
                if (!subComponents.isEmpty()) {
                    String[][] subComponentInformation = this.generateSubComponentInformation(subComponents);
                    Map<String, Double> subComponentMarks = this.generateComponentMarkInformation(subComponents, courseID);
                    io.printSubcomponents(subComponentInformation, subComponentMarks);
                }
            }
        }

        if (exam != null) {
            io.printExamStatistics(exam.getWeight(), markCalculator.computeAverageMarkForCourseComponent(courseID, "Exam"));

        } else {
            io.printNoExamMessage();
        }

        io.printOverallPerformance(markCalculator.computeOverallMarkForCourse(courseID));
    }

    @Override
    public Course readExistingCourse() throws CourseNotFoundException {
        String validCourseID = new CourseMgrIO().readExistingCourseId();
        return getCourseFromId(validCourseID);
    }

    @Override
    public String readExistingDepartment() {
        return new CourseMgrIO().readExistingDepartment();
    }

    @Override
    public Course getCourseFromId(String courseID) throws CourseNotFoundException {
        Optional<Course> course = courses.stream()
                .filter(c -> courseID.equals(c.getCourseId()))
                .findAny();

        if (!course.isPresent()) {
            throw new CourseNotFoundException(courseID);
        }
        return course.get();
    }

    @Override
    public int getNumberOfLectureGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.LECTURE_GROUP.toString(), compareTo, totalSeats);
    }

    @Override
    public int getReadWeeklyLectureHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LECTURE_GROUP.toString(), academicUnits);
    }

    @Override
    public int getNumberOfLabGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.LAB_GROUP.toString(), compareTo, totalSeats);
    }

    @Override
    public int getReadWeeklyLabHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LAB_GROUP.toString(), academicUnits);
    }

    @Override
    public int getNumberOfTutorialGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.TUTORIAL_GROUP.toString(), compareTo, totalSeats);
    }

    @Override
    public int getReadWeeklyTutorialHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.TUTORIAL_GROUP.toString(), academicUnits);
    }

    @Override
    public List<String> getListCourseTypes() {
        return CourseType.getListOfAllCourseTypeNames();
    }

    @Override
    public String getMainComponentString() {
        return MainComponent.COMPONENT_NAME;
    }

    @Override
    public String getSubComponentString() {
        return SubComponent.COMPONENT_NAME;
    }

    @Override
    public boolean checkCourseExists(String courseID) {
        Optional<Course> course = courses.stream()
          .filter(c -> courseID.equals(c.getCourseId()))
          .findFirst();

        return course.isPresent();
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

        io.printEmptyCourseComponents(currentCourse.getCourseId(), currentCourse.getName());
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

    private String generateCourseInformation(Course course) {
        String infoString = course.getCourseId() + " " + course.getName() + " (Available/Total): " + course.getVacancies() + "/" + course.getCapacity();
        return infoString;
    }

    private String[][] generateSubComponentInformation(List<SubComponent> subComponents) {
        String[][] map = new String[subComponents.size()][2];
        int i = 0;
        for (SubComponent subComponent : subComponents) {
            map[i][0] = subComponent.getName();
            map[i][1] = String.valueOf(subComponent.getWeight());
            i++;
        }
        return map;
    }

    private Map<String, Double> generateComponentMarkInformation(List<SubComponent> subComponents, String courseID) {
        Map<String, Double> map = new HashMap<>();
        for (SubComponent subComponent : subComponents) {
            double mark = markCalculator.computeAverageMarkForCourseComponent(courseID, subComponent.getName());
            map.put(subComponent.getName(), mark);
        }
        return map;
    }

    private List<String> generateCourseInformationFromCourse(Course course) {
        List<String> courseInformation = new ArrayList<String>();
        courseInformation.add(course.getCourseId());
        courseInformation.add(course.getName());
        courseInformation.add(String.valueOf(course.getAcademicUnits()));
        courseInformation.add(String.valueOf(course.getCapacity()));
        courseInformation.add(String.valueOf(course.getVacancies()));
        return courseInformation;
    }

    private List<String> generateListOfAllCourseIDs() {
        List<String> courseIDs = new ArrayList<>();
        for (Course course : courses) {
            courseIDs.add(course.getCourseId());
        }
        return courseIDs;
    }

    private Map<String, List<String>> generateGeneralInformationForAllCourses() {
        Map<String, List<String>> generalCourseInfoMap = new HashMap<>();
        for (Course course : courses) {
            List<String> generalCourseInfo = new ArrayList<>();
            generalCourseInfo.add(course.getName());
            generalCourseInfo.add(course.getCourseCoordinator().getName());
            generalCourseInfoMap.put(course.getCourseId(), generalCourseInfo);
        }
        return generalCourseInfoMap;

    }

    private Map<Map<String, String>, Map<String, String>> generateComponentInformationForACourses(Course course) {
        Map<Map<String, String>, Map<String, String>> map = new HashMap<>();
        for (MainComponent eachComp : course.getMainComponents()) {
            Map<String, String> mainComponentInfo = new HashMap<>();
            mainComponentInfo.put(eachComp.getName(), String.valueOf(eachComp.getWeight()));

            Map<String, String> subComponentsInfo = new HashMap<>();
            for (SubComponent eachSub : eachComp.getSubComponents()) {
                subComponentsInfo.put(eachSub.getName(), String.valueOf(eachSub.getWeight()));
            }

            map.put(mainComponentInfo, subComponentsInfo);
        }
        return map;
    }
}