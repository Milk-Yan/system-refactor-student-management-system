package com.softeng306.io;

import java.util.List;

public interface ICourseRegistrationMgrIO {
    void printContainsNoGroupMessage(String type);

    void printNoRegistrationsForCourseMessage();

    void printAlreadyRegisteredError();

    void printInvalidUserInputMessage();

    void printEndOfSection();

    void printNoVacancies();

    void printNoAssessmentMessage(String profName);

    void printRegistrationRequestDetails(String studentName, String studentId, String courseId, String courseName);

    void registerStudentForCourse();

    void printStudents();

    void printGroupString(List<String> groupString);
}
