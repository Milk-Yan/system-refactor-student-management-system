package com.softeng306.managers;


import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.ICourseBuilder;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.Mark;

import com.softeng306.fileprocessing.CourseFileProcessor;
import com.softeng306.fileprocessing.IFileProcessor;

import com.softeng306.enums.GroupType;

import com.softeng306.io.CourseMgrIO;
import com.softeng306.io.MainMenuIO;


import java.util.*;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

public class CourseMgr {
    /**
     * A list of all the courses in this school.
     */
    private List<Course> courses;

    private static CourseMgr singleInstance = null;

    private CourseMgrIO courseMgrIO = new CourseMgrIO();

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

        Course course = completeBuilder.build();
        int addCourseComponentChoice;

        // Update Course in files
        courseFileProcessor.writeNewEntryToFile(course);

        courses.add(course);

        addCourseComponentChoice = courseMgrIO.readCreateCourseComponentChoice();

        // Don't add course components option selected
        if (addCourseComponentChoice == 2) {
            courseMgrIO.printComponentsNotInitialized(course.getCourseID());
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
        //printout the result directly
        MainMenuIO.printMethodCall("checkAvailableSlots");

        while (true) {
            Course currentCourse = readCourseFromUser();
            if (currentCourse != null) {
                courseMgrIO.printCourseInfoString(this.generateCourseInformation(currentCourse));
                courseMgrIO.printVacanciesForGroups(this.generateGroupInformation(currentCourse.getLectureGroups()),GroupType.LECTURE_GROUP.toString());

                if (currentCourse.getTutorialGroups() != null) {
                    courseMgrIO.printEmptySpace();
                    courseMgrIO.printVacanciesForGroups(this.generateGroupInformation(currentCourse.getTutorialGroups()), GroupType.TUTORIAL_GROUP.toString());
                }

                if (currentCourse.getLabGroups() != null) {
                    courseMgrIO.printEmptySpace();
                    courseMgrIO.printVacanciesForGroups(this.generateGroupInformation(currentCourse.getLabGroups()), GroupType.LAB_GROUP.toString());
                }
                courseMgrIO.printEmptySpace();
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

        Set<String> mainComponentNames = new HashSet<>();
        List<MainComponent> mainComponents = new ArrayList<>(0);
        // Check if mainComponent is empty
        if (currentCourse.getMainComponents().isEmpty()) {
            // empty course

            courseMgrIO.printEmptyCourseComponents(currentCourse.getCourseID(), currentCourse.getCourseName());
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
                    HashMap<String, Double> subComponentsMap = new HashMap<>();
                    String mainComponentName = courseMgrIO.readMainComponentName(totalWeightage, i, mainComponentNames);
                    mainComponentNames.add(mainComponentName);

                    int weight = courseMgrIO.readMainComponentWeightage(i, totalWeightage);
                    totalWeightage -= weight;

                    int noOfSub = courseMgrIO.readNoOfSubComponents(i);

                    subComponentsMap = courseMgrIO.readSubComponents(noOfSub);

                    List<SubComponent> subComponentsList = new ArrayList<SubComponent>();
                    for (String key : subComponentsMap.keySet()){
                        SubComponent subComponent = new SubComponent(key, subComponentsMap.get(key).intValue());
                        subComponentsList.add(subComponent);
                    }

                    MainComponent main = new MainComponent(mainComponentName, weight, subComponentsList);
                    mainComponents.add(main);
                }


                if (totalWeightage != 0) {
                    // weightage assign is not tallied
                    courseMgrIO.printWeightageError();
                    mainComponents.clear();
                    mainComponentNames.clear();
                } else {
                    break;
                }
            }
            //set maincomponent to course
            currentCourse.setMainComponents(mainComponents);

        } else {
            courseMgrIO.printCourseworkWeightageEnteredError();
        }

        courseMgrIO.printComponentsForCourse(currentCourse.getCourseID(), currentCourse.getCourseName(), generateComponentInformationForACourses(currentCourse));

        // Update course into course.csv
    }


    /**
     * Displays a list of IDs of all the courses.
     */
    public void printAllCourseIds() {
        courseMgrIO.printAllCourseIds(generateListOfAllCourseIDs());
    }

    public List<String> getCourseIdsInDepartment(String departmentName) {
        Department department = Department.valueOf(departmentName);
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

        courseMgrIO.printCourseStatisticsHeader(generateCourseInformationFromCourse(currentCourse));

        MainComponent exam = null;

        // Find marks for every assessment components
        for (MainComponent mainComponent : currentCourse.getMainComponents()) {
            String componentName = mainComponent.getComponentName();

            if (componentName.equals("Exam")) {
//                Leave the exam report to the last
                exam = mainComponent;
            } else {
                courseMgrIO.printMainComponent(mainComponent.getComponentName(),mainComponent.getComponentWeight(), markCalculator.computeComponentMark(courseMarks, mainComponent.getComponentName()));
                List<SubComponent> subComponents = mainComponent.getSubComponents();
                if (!subComponents.isEmpty()) {
                    String[][] subComponentInformation = this.generateSubComponentInformation(subComponents);
                    HashMap<String, Double> subComponentMarks = this.generateComponentMarkInformation(subComponents,courseMarks);
                    courseMgrIO.printSubcomponents(subComponentInformation, subComponentMarks);
                }
            }
        }

        if (exam != null) {
            courseMgrIO.printExamStatistics(exam.getComponentWeight(), markCalculator.computeExamMark(courseMarks));
        } else {
            courseMgrIO.printNoExamMessage();
        }
        courseMgrIO.printOverallPerformance(markCalculator.computerOverallMark(courseMarks));
    }


    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public Course readCourseFromUser() {
        String validCourseID = courseMgrIO.readValidCourseIdFromUser();
        return getCourseFromId(validCourseID);
    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public String readDepartmentFromUser() {
        return courseMgrIO.readDepartmentWithMoreThanOneCourseFromUser();
    }

    /**
     * Return the list of all courses in the system.
     *
     * @return An list of all courses.
     */
    public List<Course> getCourses() {
        return courses;
    }


    public String generateCourseInformation(Course course){
        String infoString = course.getCourseID() + " " + course.getCourseName() + " (Available/Total): " + course.getVacancies() + "/" + course.getTotalSeats();
        return infoString;
    }

    public String[][] generateGroupInformation(List<Group> groups){
        String[][] groupInfo = new String[groups.size()][3];
        for(int i = 0; i<groups.size(); i++){
            groupInfo[i][0] = groups.get(i).getGroupName();
            groupInfo[i][1] = String.valueOf(groups.get(i).getAvailableVacancies());
            groupInfo[i][2] = String.valueOf(groups.get(i).getTotalSeats());
        }
        return groupInfo;
    }

    public String[][] generateSubComponentInformation(List<SubComponent> subComponents){
        //HashMap<String, Integer> map = new HashMap<>();
        String[][] map = new String[subComponents.size()][2];
        int i = 0;
        for(SubComponent subComponent : subComponents){
            map[i][0] = subComponent.getComponentName();
            map[i][1] = String.valueOf(subComponent.getComponentWeight());
            i++;
        }
        return map;
    }

    public HashMap<String, Double> generateComponentMarkInformation(List<SubComponent> subComponents, List<Mark> marks){
        HashMap<String, Double> map = new HashMap<>();
        for(SubComponent subComponent : subComponents){
            double mark = markCalculator.computeComponentMark(marks, subComponent.getComponentName());
            map.put(subComponent.getComponentName(), mark);
        }
        return map;
    }

    public List<String> generateCourseInformationFromCourse(Course course){
        List<String> courseInformation = new ArrayList<String>();
        courseInformation.add(course.getCourseID());
        courseInformation.add(course.getCourseName());
        courseInformation.add(String.valueOf(course.getAU()));
        courseInformation.add(String.valueOf(course.getTotalSeats()));
        courseInformation.add(String.valueOf(course.getVacancies()));
        return courseInformation;
    }

    public List<String> generateListOfAllCourseIDs(){
        List<String> courseIDs = new ArrayList<>();
        for(Course course : courses){
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
    public Course getCourseFromId(String courseID) {
        List<Course> anyCourse = CourseMgr.getInstance().getCourses().stream().filter(c -> courseID.equals(c.getCourseID())).collect(Collectors.toList());
        if (anyCourse.isEmpty()) {
            return null;
        }
        return anyCourse.get(0);
    }

    public HashMap<String, List<String>> generateGeneralInformationForAllCourses(){
        HashMap<String, List<String>> generalCourseInfoMap = new HashMap<>();
        for(Course course : courses) {
            List<String> generalCourseInfo = new ArrayList<>();
            generalCourseInfo.add(course.getCourseName());
            generalCourseInfo.add(course.getProfInCharge().getProfName());
            generalCourseInfoMap.put(course.getCourseID(),generalCourseInfo);
        }
        return generalCourseInfoMap;

    }

    public HashMap<HashMap<String, String>, HashMap<String,String>> generateComponentInformationForACourses(Course course){
        HashMap<HashMap<String, String>, HashMap<String, String>> map = new HashMap<>();
        for (MainComponent eachComp : course.getMainComponents()) {
            HashMap<String, String> mainComponentInfo = new HashMap<>();
            mainComponentInfo.put(eachComp.getComponentName(), String.valueOf(eachComp.getComponentWeight()));

            HashMap<String,String> subComponentsInfo = new HashMap<>();
            for (SubComponent eachSub : eachComp.getSubComponents()) {
                subComponentsInfo.put(eachSub.getComponentName(), String.valueOf(eachSub.getComponentWeight()));
            }

            map.put(mainComponentInfo, subComponentsInfo);
        }
        return map;
    }

    public boolean checkContainsDepartment(String courseDepartment){
        DepartmentMgr departmentMgr = new DepartmentMgr();
        return departmentMgr.contains(courseDepartment);
    }

    public List<String> getAllDepartmentsNameList(){
        DepartmentMgr departmentMgr = new DepartmentMgr();
        return departmentMgr.getListOfDepartments();
    }

    public int getNumberOfLectureGroups(int compareTo, int totalSeats){
        return courseMgrIO.readNoOfGroup(GroupType.LECTURE_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyLectureHour(int AU){
        return courseMgrIO.readWeeklyHour(GroupType.LECTURE_GROUP.toString(), AU);
    }

    public int getNumberOfLabGroups(int compareTo, int totalSeats){
        return courseMgrIO.readNoOfGroup(GroupType.LAB_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyLabHour(int AU){
        return courseMgrIO.readWeeklyHour(GroupType.LAB_GROUP.toString(), AU);
    }

    public int getNumberOfTutorialGroups(int compareTo, int totalSeats){
        return courseMgrIO.readNoOfGroup(GroupType.TUTORIAL_GROUP.toString(), compareTo, totalSeats);
    }

    public int getReadWeeklyTutorialHour(int AU){
        return courseMgrIO.readWeeklyHour(GroupType.TUTORIAL_GROUP.toString(), AU);
    }


    public String getCourseName(String courseId) {
        Course course = getCourseFromId(courseId);
        return course.getCourseName();
    }

    public List<String> getListCourseTypes(){
        return CourseType.getAllCourseTypes();
    }

    public String getMainComponentString(){
        return MainComponent.COMPONENT_NAME;
    }

    public String getSubComponentString(){
        return SubComponent.COMPONENT_NAME;
    }



}