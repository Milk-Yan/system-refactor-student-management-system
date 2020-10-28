package com.softeng306.managers;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.student.Student;
import com.softeng306.enums.GroupType;
import com.softeng306.io.*;
import com.softeng306.validation.CourseRegistrationValidator;
import com.softeng306.validation.CourseValidator;
import com.softeng306.validation.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseRegistrationMgr {
    private static Scanner scanner = new Scanner(System.in);
    /**
     * A list of all the course registration records in this school.
     */
    private List<CourseRegistration> courseRegistrations;

    private static CourseRegistrationMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseRegistrationMgr(List<CourseRegistration> courseRegistrations) {
        this.courseRegistrations = courseRegistrations;
    }

    /**
     * Return the CourseRegistrationMgr singleton, if not initialised already, create an instance.
     *
     * @return CourseRegistrationMgr the singleton instance
     */
    public static CourseRegistrationMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new CourseRegistrationMgr(FILEMgr.loadCourseRegistration());
        }

        return singleInstance;
    }


    /**
     * Registers a course for a student
     */
    public List<String> registerCourse(CourseRegistrationMgrIO io, String studentID, String courseID) {
        Student currentStudent = StudentValidator.getStudentFromId(studentID);
        Course currentCourse = CourseValidator.getCourseFromId(courseID);

        if (CourseRegistrationValidator.checkCourseRegistrationExists(studentID, courseID) != null) {
            return null;
        }

        if (currentCourse.getMainComponents().isEmpty()) {
            io.printNoAssessmentMessage(currentCourse);
            return null;
        }

        if (currentCourse.getVacancies() == 0) {
            io.printNoVacancies();
            return null;
        }

        io.printPendingRegistrationMethod(currentCourse, currentStudent);

        List<Group> lecGroups = new ArrayList<>();
        lecGroups.addAll(currentCourse.getLectureGroups());

        GroupMgr groupMgr = GroupMgr.getInstance();

        Group selectedLectureGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LECTURE_GROUP, lecGroups);

        List<Group> tutGroups = new ArrayList<>();
        tutGroups.addAll(currentCourse.getTutorialGroups());

        Group selectedTutorialGroup = groupMgr.printGroupWithVacancyInfo(GroupType.TUTORIAL_GROUP, tutGroups);

        List<Group> labGroups = new ArrayList<>();
        labGroups.addAll(currentCourse.getLabGroups());

        Group selectedLabGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LAB_GROUP, labGroups);

        currentCourse.enrolledIn();
        CourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse, selectedLectureGroup, selectedTutorialGroup, selectedLabGroup);
        FILEMgr.writeCourseRegistrationIntoFile(courseRegistration);

        MarkMgr.getInstance().getMarks().add(MarkMgr.getInstance().initializeMark(currentStudent, currentCourse));

        courseRegistrations.add(courseRegistration);

        List<String> registrationInfo = new ArrayList<>();
        registrationInfo.add(currentStudent.getStudentName());

        registrationInfo.add(selectedLectureGroup.getGroupName());

        if (selectedTutorialGroup != null) {
            registrationInfo.add(selectedTutorialGroup.getGroupName());
        } else {
            registrationInfo.add("");
        }

        if (selectedLabGroup != null) {
            registrationInfo.add(selectedLabGroup.getGroupName());
        } else {
            registrationInfo.add("");
        }

        return registrationInfo;
    }

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    public void printStudents(CourseRegistrationMgrIO io, String courseID, int opt) {
        Course currentCourse = CourseValidator.getCourseFromId(courseID);

        // READ courseRegistrationFILE
        // return List of Object(student,course,lecture,tut,lab)
        List<CourseRegistration> allCourseRegistrations = FILEMgr.loadCourseRegistration();

        List<CourseRegistration> courseRegistrationList = new ArrayList<>();
        for (CourseRegistration courseRegistration : allCourseRegistrations) {
            if (courseRegistration.getCourse().getCourseID().equals(currentCourse.getCourseID())) {
                courseRegistrationList.add(courseRegistration);
            }
        }

        if (courseRegistrationList.isEmpty()) {
            io.printNoEnrolmentsError();
        }

        if (opt == 1) {
            sortByLectureGroup(courseRegistrationList);
            io.printByGroup(courseRegistrationList, GroupType.LECTURE_GROUP);

        } else if (opt == 2) {
            if (!courseRegistrationList.isEmpty() && courseRegistrationList.get(0).getCourse().getTutorialGroups().isEmpty()) {
                io.printNoGroup(GroupType.TUTORIAL_GROUP);
                io.printEndOfSection();
                return;
            }
            sortByTutorialGroup(courseRegistrationList);
            io.printByGroup(courseRegistrationList, GroupType.TUTORIAL_GROUP);

        } else if (opt == 3) {
            if (!courseRegistrationList.isEmpty() && courseRegistrationList.get(0).getCourse().getLabGroups().isEmpty()) {
                io.printNoGroup(GroupType.LAB_GROUP);
                io.printEndOfSection();
                return;
            }
            sortByLabGroup(courseRegistrationList);
            io.printByGroup(courseRegistrationList, GroupType.LAB_GROUP);

        } else {
            io.printInvalidInputError();
        }

        io.printEndOfSection();
    }

    /**
     * Return the list of all course registrations in the system.
     *
     * @return An list of all course registrations.
     */
    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    /**
     * Sort the list of course registrations of a course according to their ascending
     * normal alphabetical order of names of the lecture groups, ignoring cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByLectureGroup(List<CourseRegistration> courseRegistrations) {
        courseRegistrations.sort((o1, o2) -> {
            // in the case where there are no lectures, we don't care about
            // the ordering.
            if (o1.getLectureGroup() == null || o2.getLectureGroup() == null) {
                return 0;
            }

            String group1 = o1.getLectureGroup().getGroupName().toUpperCase();
            String group2 = o2.getLectureGroup().getGroupName().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        });
    }

    /**
     * Sort the list of course registrations of a course according to their ascending
     * normal alphabetical order of the names of the tutorial groups, ignoring cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByTutorialGroup(List<CourseRegistration> courseRegistrations) {
        courseRegistrations.sort((s1, s2) -> {
            // in the case where there are no tutorials, we don't care about
            // the ordering.
            if (s1.getTutorialGroup() == null || s2.getTutorialGroup() == null) {
                return 0;
            }

            String group1 = s1.getTutorialGroup().getGroupName().toUpperCase();
            String group2 = s2.getTutorialGroup().getGroupName().toUpperCase();

            //ascending order
            return group1.compareTo(group2);

        });
    }

    /**
     * Sort the list of course registrations of a course according to their ascending
     * normal alphabetical order of the names of the lab groups, ignoring cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByLabGroup(List<CourseRegistration> courseRegistrations) {
        courseRegistrations.sort((o1, o2) -> {
            // in the case where there are no labs, we don't care about
            // the ordering.
            if (o1.getLabGroup() == null || o2.getLabGroup() == null) {
                return 0;
            }

            String group1 = o1.getLabGroup().getGroupName().toUpperCase();
            String group2 = o2.getLabGroup().getGroupName().toUpperCase();

            //ascending order
            return group1.compareTo(group2);
        });
    }

    public List<String> getCourseIdsForStudentId(String studentId) {
        List<String> courseIds = new ArrayList<>();
        for (CourseRegistration courseRegistration : courseRegistrations) {
            if (courseRegistration.getStudent().getStudentID().equals(studentId)) {
                courseIds.add(courseRegistration.getCourse().getCourseID());
            }
        }

        return courseIds;
    }

    public int getStudentTotalAU(Student student) {
        int total = 0;
        for (CourseRegistration courseRegistration : courseRegistrations) {
            if (courseRegistration.getStudent().equals(student)) {
                total += courseRegistration.getCourse().getAU();
            }
        }
        return total;
    }


}
