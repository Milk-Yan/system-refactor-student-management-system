package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.mark.IMarkCalculator;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;

import com.softeng306.enums.CourseType;
import com.softeng306.enums.GroupType;

import com.softeng306.fileprocessing.CourseFileProcessor;
import com.softeng306.fileprocessing.IFileProcessor;

import com.softeng306.io.ICourseMgrIO;
import com.softeng306.io.MainMenuIO;
import com.softeng306.io.CourseMgrIO;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for course manager operations.
 * Provides implementations for functions must be performed on courses in the academic institute.
 * This is a subclass of {@code ICourseMgr}
 */
public class CourseMgr implements ICourseMgr {
    /**
     * A list of all the courses in this school.
     */
    private List<ICourse> courses;
    private static ICourseMgr singleInstance;

    private final IFileProcessor<ICourse> courseFileProcessor;
    private IMarkCalculator markCalculator = new MarkCalculator();

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseMgr() {
        courseFileProcessor = new CourseFileProcessor();
        courses = courseFileProcessor.loadFile();
    }

    /**
     * Return the CourseMgr singleton, if not initialised already, create an
     * instance.
     *
     * @return CourseMgr the singleton instance
     */
    public static ICourseMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new CourseMgr();
        }

        return singleInstance;
    }

    @Override
    public void addCourse(ICourseBuilder completeBuilder) {
        ICourseMgrIO courseMgrIO = new CourseMgrIO();

        ICourse course = completeBuilder.build();
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

        // printout the result directly
        MainMenuIO.printMethodCall("checkAvailableSlots");

        while (true) {
            ICourse currentCourse;
            try {
                currentCourse = readExistingCourse();
            } catch (CourseNotFoundException e) {
                e.printStackTrace();
                return;
            }

            if (currentCourse != null) {
                io.printCourseInfoString(generateCourseInformation(currentCourse));
                io.printVacanciesForGroups(currentCourse.generateLectureGroupInformation(),
                        GroupType.LECTURE_GROUP.toString());

                if (currentCourse.getTutorialGroups() != null) {
                    io.printEmptySpace();
                    io.printVacanciesForGroups(currentCourse.generateTutorialGroupInformation(),
                            GroupType.TUTORIAL_GROUP.toString());
                }

                if (currentCourse.getLabGroups() != null) {
                    io.printEmptySpace();
                    io.printVacanciesForGroups(currentCourse.generateLabGroupInformation(),
                            GroupType.LAB_GROUP.toString());

                }
                io.printEmptySpace();
                break;
            } else {
                io.printCourseNotExist();
            }
        }
    }

    @Override
    public void enterCourseWorkComponentWeightage(ICourse currentCourse) {
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

        io.printComponentsForCourse(currentCourse.getCourseId(), currentCourse.getName(),
                generateComponentInformationForACourses(currentCourse));

        // Update course into course.csv
    }

    @Override
    public void printAllCourseIds() {
        new CourseMgrIO().printAllCourseIds(generateListOfAllCourseIDs());
    }

    @Override
    public List<String> getCourseIdsInDepartment(String departmentName) {
        List<ICourse> validCourses = new ArrayList<>();
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

        ICourse currentCourse;
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
                // Leave the exam report to the last
                exam = mainComponent;
            } else {
                io.printMainComponent(mainComponent.getName(), mainComponent.getWeight(),
                        markCalculator.computeAverageMarkForCourseComponent(courseID, mainComponent.getName()));
                List<SubComponent> subComponents = mainComponent.getSubComponents();
                if (!subComponents.isEmpty()) {
                    String[][] subComponentInformation = this.generateSubComponentInformation(subComponents);
                    Map<String, Double> subComponentMarks = this.generateComponentMarkInformation(subComponents,
                            courseID);
                    io.printSubcomponents(subComponentInformation, subComponentMarks);
                }
            }
        }

        if (exam != null) {
            io.printExamStatistics(exam.getWeight(),
                    markCalculator.computeAverageMarkForCourseComponent(courseID, "Exam"));

        } else {
            io.printNoExamMessage();
        }

        io.printOverallPerformance(markCalculator.computeOverallMarkForCourse(courseID));
    }

    @Override
    public ICourse readExistingCourse() throws CourseNotFoundException {
        String validCourseID = new CourseMgrIO().readExistingCourseId();
        return getCourseFromId(validCourseID);
    }

    @Override
    public String readExistingDepartment() {
        return new CourseMgrIO().readExistingDepartment();
    }

    @Override
    public ICourse getCourseFromId(String courseID) throws CourseNotFoundException {
        Optional<ICourse> course = courses.stream().filter(c -> courseID.equals(c.getCourseId())).findAny();

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
    public int readWeeklyLectureHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LECTURE_GROUP.toString(), academicUnits);
    }

    @Override
    public int getNumberOfLabGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.LAB_GROUP.toString(), compareTo, totalSeats);
    }

    @Override
    public int readWeeklyLabHour(int academicUnits) {
        return new CourseMgrIO().readWeeklyHour(GroupType.LAB_GROUP.toString(), academicUnits);
    }

    @Override
    public int getNumberOfTutorialGroups(int compareTo, int totalSeats) {
        return new CourseMgrIO().readNoOfGroup(GroupType.TUTORIAL_GROUP.toString(), compareTo, totalSeats);
    }

    @Override
    public int readWeeklyTutorialHour(int academicUnits) {
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
        Optional<ICourse> course = courses.stream().filter(c -> courseID.equals(c.getCourseId())).findFirst();

        return course.isPresent();
    }

    /**
     * Adds main assessment components to a given course.
     *
     * @param io            A CourseMgrIO to use for output.
     * @param currentCourse The course to create components for.
     * @return The list of new main components.
     */
    private List<MainComponent> addMainComponentsToCourse(ICourseMgrIO io, ICourse currentCourse) {
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
     * @return The total weight of all created main assessment components.
     */
    private int addMainComponents(ICourseMgrIO io, int examWeight, int numberOfMainComponents,
                                  List<MainComponent> mainComponents) {
        Set<String> mainComponentNames = new HashSet<>();
        int totalWeightage = 100 - examWeight;
        // loops through number of main components specified
        for (int i = 0; i < numberOfMainComponents; i++) {
            Map<String, Double> subComponentsMap;
            String mainComponentName = io.readMainComponentName(totalWeightage, i, mainComponentNames);

            mainComponentNames.add(mainComponentName);

            int weight = io.readMainComponentWeightage(i, totalWeightage);
            totalWeightage -= weight;

            int noOfSub = io.readNoOfSubComponents(i);
            subComponentsMap = io.readSubComponents(noOfSub);

            // create list of subcomponents
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
     * Creates an exam assessment component for a course
     *
     * @param io             CourseMgrIO to use for user I/O
     * @param mainComponents List of components to add exam to
     * @return The weight of the exam (or 0 if no exam).
     */
    private int addExamComponent(ICourseMgrIO io, List<MainComponent> mainComponents) {
        int hasFinalExamChoice = 0;
        int examWeight = 0;


        while (hasFinalExamChoice < 1 || hasFinalExamChoice > 2) {
            hasFinalExamChoice = io.readHasFinalExamChoice();
            if (hasFinalExamChoice == 1) { // course has a final exam
                examWeight = io.readExamWeight();
                MainComponent exam = new MainComponent("Exam", examWeight, new ArrayList<>());
                mainComponents.add(exam);
            } else if (hasFinalExamChoice == 2) { //  course does not have a final exam
                io.printEnterContinuousAssessments();
            }
        }

        return examWeight;
    }

    /**\
     * Generates the course information containing the id, name, vancancies and capacity
     *
     * @param course The course to generate information for
     * @return a string of information about the course
     */
    private String generateCourseInformation(ICourse course) {
        String infoString = course.getCourseId() + " " + course.getName() + " (Available/Total): "
                + course.getVacancies() + "/" + course.getCapacity();
        return infoString;
    }

    /**
     * Generates the sub component information containing the name and the weight of each subcomponent
     *
     * @param subComponents List of subcomponents to generate the list of information for
     * @return a 2d String array with the first dimension comprising the subcomponents and the second dimension
     *      indicating the information for said component. The first index indicates the name whilst the second
     *      index indicates at the weight of the subcomponent
     */
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

    /**
     * Generates the component mark information containing name and mark associated to each component
     *
     * @param subComponents The list of subcomponents to generate the mark information for
     * @param courseID The ID of the course in which the subcomponents belong to
     * @return a map containing the name of the subcomponent as a key and marks for said subcomponent as a value
     */
    private Map<String, Double> generateComponentMarkInformation(List<SubComponent> subComponents, String courseID) {
        Map<String, Double> map = new HashMap<>();
        for (SubComponent subComponent : subComponents) {
            double mark = markCalculator.computeAverageMarkForCourseComponent(courseID, subComponent.getName());
            map.put(subComponent.getName(), mark);
        }
        return map;
    }

    /**
     * Generates the course information from the specified course including the id, name, academic units, capacity
     * and the number of vacancies
     *
     * @param course The course to generate information for
     * @return the list of information of the specified course
     */
    public List<String> generateCourseInformationFromCourse(ICourse course) {
        List<String> courseInformation = new ArrayList<String>();
        courseInformation.add(course.getCourseId());
        courseInformation.add(course.getName());
        courseInformation.add(String.valueOf(course.getAcademicUnits()));
        courseInformation.add(String.valueOf(course.getCapacity()));
        courseInformation.add(String.valueOf(course.getVacancies()));
        return courseInformation;
    }

    /**
     * Generates the list of all course IDs
     *
     * @return the list of all course IDs
     */
    private List<String> generateListOfAllCourseIDs() {
        List<String> courseIDs = new ArrayList<>();
        for (ICourse course : courses) {
            courseIDs.add(course.getCourseId());
        }
        return courseIDs;
    }

    /**
     * Generates a general map of information for all courses
     *
     * @return a map with the key of type string indicating the course ID and the value containing a list of information
     *      related to specified course
     */
    private Map<String, List<String>> generateGeneralInformationForAllCourses() {
        Map<String, List<String>> generalCourseInfoMap = new HashMap<>();
        for (ICourse course : courses) {
            List<String> generalCourseInfo = new ArrayList<>();
            generalCourseInfo.add(course.getName());
            generalCourseInfo.add(course.getCourseCoordinator().getName());
            generalCourseInfoMap.put(course.getCourseId(), generalCourseInfo);
        }
        return generalCourseInfoMap;

    }

    /**
     * Generates the component information for a specified course
     *
     * @param course A course for which to generate information for
     * @return A map containing key values of type map, with the keys of this map containing the name of each
     *      of each main component and the value containing the weight of each main component
     *      The exterior map contains a value of type Map, with this map containing each sub component name as a key,
     *      and each weight for said sub component as a value.
     */
    private Map<Map<String, String>, Map<String, String>> generateComponentInformationForACourses(ICourse course) {
        Map<Map<String, String>, Map<String, String>> map = new HashMap<>();
        // create a map of main components
        for (MainComponent eachComp : course.getMainComponents()) {
            Map<String, String> mainComponentInfo = new HashMap<>();
            mainComponentInfo.put(eachComp.getName(), String.valueOf(eachComp.getWeight()));

            Map<String, String> subComponentsInfo = new HashMap<>();
            // create a map of sub components for each one in specified main component
            for (SubComponent eachSub : eachComp.getSubComponents()) {
                subComponentsInfo.put(eachSub.getName(), String.valueOf(eachSub.getWeight()));
            }

            // map the main component to the sub component
            map.put(mainComponentInfo, subComponentsInfo);
        }
        return map;
    }

}