package com.softeng306.io;

import java.util.List;

public interface IMarkMgrIO {
    void printStudentNotRegisteredToCourse(String courseID);

    void printCourseComponentChoices(List<String> availableChoices, List<Integer> weights);

    int readCourseComponentChoice(int numChoices);

    double readCourseComponentMark();

    double readExamMark();

    void initiateEnteringCourseworkMark(boolean isExam);
}
