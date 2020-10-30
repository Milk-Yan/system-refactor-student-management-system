package com.softeng306.io;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICourseMgrIO {

    int readCreateCourseComponentChoice();

    void printCourseAdded(String courseID);

    void printComponentsNotInitialisedMessage(String courseID);

    void printCourseInfoString(String courseInfoString);

    void printVacanciesForGroups(String[][] groupInformation, String groupType);

    void printCourseNotExist();

    int readHasFinalExamChoice();

    int readExamWeight();

    void printEnterContinuousAssessments();

    int readNoOfMainComponents();

    int readMainComponentWeightage(int i, int totalWeightage);

    int readNoOfSubComponents(int mainComponentNo);

    void printWeightageError();

    void printComponentsForCourse(String courseId, String courseName, Map<Map<String, String>, Map<String, String>> allGroupInformation);

    String readMainComponentName(int totalWeightage, int mainComponentNo, Set<String> mainComponentNames);

    Map<String, Double> readSubComponents(int numberOfSubComponents);

    void printEmptyCourseComponents(String courseID, String courseName);

    void printCourses(Map<String, List<String>> courseGeneralInfo);

    void printCourseworkWeightageEnteredError();

    void printCourseStatisticsHeader(List<String> courseInfo);

    void printMainComponent(String mainComponentName, int mainComponentWeight, double averageCourseMark);

    void printSubcomponents(String[][] subComponentInformation, Map<String, Double> courseMarks);

    void printExamStatistics(int examWeight, Double examMark);

    void printNoExamMessage();

    void printOverallPerformance(double overallMark);

    String readValidCourseIdFromUser();

    void addCourse();

    void checkAvailableSlots();

    void enterCourseWorkComponentWeightage();

    void printCourseStatistics();

    void printEmptySpace();
}
