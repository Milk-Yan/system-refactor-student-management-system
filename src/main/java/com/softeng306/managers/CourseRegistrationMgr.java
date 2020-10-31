package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.courseregistration.ICourseRegistration;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.exceptions.CourseNotFoundException;
import com.softeng306.domain.exceptions.GroupTypeNotFoundException;
import com.softeng306.domain.exceptions.InvalidCourseRegistrationException;
import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.domain.student.IStudent;
import com.softeng306.enums.GroupType;
import com.softeng306.fileprocessing.CourseRegistrationFileProcessor;
import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.io.ICourseRegistrationMgrIO;
import com.softeng306.io.CourseRegistrationMgrIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation for course registration manager operations.
 * Provides implementations for functions must be performed on courses registrations in the academic institute.
 * This is a subclass of {@code ICourseRegistrationMgr}
 */
public class CourseRegistrationMgr implements ICourseRegistrationMgr {

    /**
     * A list of all the course registration records in this school.
     */
    private List<ICourseRegistration> courseRegistrations;

    private static ICourseRegistrationMgr singleInstance = null;

    private final IFileProcessor<ICourseRegistration> courseRegistrationFileProcessor;

    /**
     * Override default constructor to implement singleton pattern
     */
    private CourseRegistrationMgr() {
        courseRegistrationFileProcessor = new CourseRegistrationFileProcessor();
        courseRegistrations = courseRegistrationFileProcessor.loadFile();
    }

    @Override
    public List<String> registerCourse(String studentID, String courseID)
            throws InvalidCourseRegistrationException, StudentNotFoundException, CourseNotFoundException {
        ICourseRegistrationMgrIO io = new CourseRegistrationMgrIO();
        IStudent currentStudent = StudentMgr.getInstance().getStudentFromId(studentID);
        ICourse currentCourse = CourseMgr.getInstance().getCourseFromId(courseID);

        if (courseRegistrationExists(studentID, courseID)) {
            io.printAlreadyRegisteredError();
            throw new InvalidCourseRegistrationException();
        }

        if (currentCourse.getMainComponents().isEmpty()) {
            io.printNoAssessmentMessage(currentCourse.getCourseCoordinator().getName());
            throw new InvalidCourseRegistrationException();
        }

        if (currentCourse.getVacancies() == 0) {
            io.printNoVacancies();
            throw new InvalidCourseRegistrationException();
        }

        io.printRegistrationRequestDetails(currentStudent.getName(), currentStudent.getStudentId(),
                currentCourse.getCourseId(), currentCourse.getName());

        List<IGroup> lecGroups = currentCourse.getLectureGroups();
        IGroupMgr groupMgr = GroupMgr.getInstance();
        IGroup selectedLectureGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LECTURE_GROUP, lecGroups);

        List<IGroup> tutGroups = currentCourse.getTutorialGroups();
        IGroup selectedTutorialGroup = groupMgr.printGroupWithVacancyInfo(GroupType.TUTORIAL_GROUP, tutGroups);

        List<IGroup> labGroups = currentCourse.getLabGroups();
        IGroup selectedLabGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LAB_GROUP, labGroups);

        currentCourse.updateVacanciesForEnrollment();
        ICourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse,
                selectedLectureGroup, selectedTutorialGroup, selectedLabGroup);
        courseRegistrationFileProcessor.writeNewEntryToFile(courseRegistration);

        StudentCourseMarkMgr.getInstance().getStudentCourseMarks().add(StudentCourseMarkMgr.getInstance().initialiseStudentCourseMark(currentStudent, currentCourse));

        courseRegistrations.add(courseRegistration);

        List<String> registrationInfo = new ArrayList<>();
        registrationInfo.add(currentStudent.getName());

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

    @Override
    public void printStudents(String courseID, int opt) throws CourseNotFoundException, GroupTypeNotFoundException {
        ICourseRegistrationMgrIO io = new CourseRegistrationMgrIO();
        ICourse currentCourse = CourseMgr.getInstance().getCourseFromId(courseID);

        // READ courseRegistrationFILE
        // return List of Object(student,course,lecture,tut,lab)
        List<ICourseRegistration> allCourseRegistrations = courseRegistrationFileProcessor.loadFile();

        List<ICourseRegistration> courseRegistrationList = new ArrayList<>();
        for (ICourseRegistration courseRegistration : allCourseRegistrations) {
            if (courseRegistration.getCourse().getCourseId().equals(currentCourse.getCourseId())) {
                courseRegistrationList.add(courseRegistration);
            }
        }

        if (courseRegistrationList.isEmpty()) {
            io.printNoRegistrationsForCourseMessage();
        }

        if (opt == 1) { // user chose to print by lecture group
            sortByLectureGroup(courseRegistrationList);
            List<String> groupString = getGroupString(courseRegistrationList, GroupType.LECTURE_GROUP);
            io.printGroupString(groupString);
        } else if (opt == 2) { // user chose to print by tutorial group
            if (!courseRegistrationList.isEmpty()
                    && courseRegistrationList.get(0).getCourse().getTutorialGroups().isEmpty()) {
                io.printContainsNoGroupMessage(GroupType.TUTORIAL_GROUP.toString());
                io.printEndOfSection();
                return;
            }
            sortByTutorialGroup(courseRegistrationList);
            List<String> groupString = getGroupString(courseRegistrationList, GroupType.TUTORIAL_GROUP);
            io.printGroupString(groupString);

        } else if (opt == 3) { // user chose to print by lab group
            if (!courseRegistrationList.isEmpty()
                    && courseRegistrationList.get(0).getCourse().getLabGroups().isEmpty()) {
                io.printContainsNoGroupMessage(GroupType.LAB_GROUP.toString());
                io.printEndOfSection();
                return;
            }
            sortByLabGroup(courseRegistrationList);
            List<String> groupString = getGroupString(courseRegistrationList, GroupType.LAB_GROUP);
            io.printGroupString(groupString);

        } else {
            io.printInvalidUserInputMessage();
        }

        io.printEndOfSection();
    }

    @Override
    public List<String> getCourseIdsForStudentId(String studentId) {
        List<String> courseIds = new ArrayList<>();
        for (ICourseRegistration courseRegistration : courseRegistrations) {
            if (courseRegistration.getStudent().getStudentId().equals(studentId)) {
                courseIds.add(courseRegistration.getCourse().getCourseId());
            }
        }

        return courseIds;
    }

    /**
     * This method prints the students of a given group
     *
     * @param courseRegistrations the list registrations for a course
     * @param groupType           the group of registration that we want to print
     */
    private List<String> getGroupString(List<ICourseRegistration> courseRegistrations, GroupType groupType)
            throws GroupTypeNotFoundException {
        List<String> groupStringInfo = new ArrayList<>();
        if (courseRegistrations.isEmpty()) {
            return groupStringInfo;
        }

        String groupName = "";
        for (ICourseRegistration courseRegistration : courseRegistrations) {
            String courseRegGroupName = courseRegistration.getGroupByType(groupType).getGroupName();
            if (!groupName.equals(courseRegGroupName)) {
                groupName = courseRegGroupName;
                groupStringInfo.add(groupType.getNameWithCapital() + " group : " + groupName);
            }

            groupStringInfo.add("Student Name: " + courseRegistration.getStudent().getName() + " Student ID: "
                    + courseRegistration.getStudent().getStudentId());
        }
        groupStringInfo.add("");

        return groupStringInfo;
    }

    /**
     * Checks whether this course registration record exists.
     *
     * @param studentID The inputted student ID.
     * @param courseID  The inputted course ID.
     * @return the existing course registration record or else null.
     */
    private boolean courseRegistrationExists(String studentID, String courseID) {
        Optional<ICourseRegistration> courseRegistration = courseRegistrations.stream()
                .filter(cr -> studentID.equals(cr.getStudent().getStudentId()))
                .filter(cr -> courseID.equals(cr.getCourse().getCourseId())).findFirst();

        return courseRegistration.isPresent();
    }

    /**
     * Return the ICourseRegistrationMgr singleton, if not initialised already,
     * create an instance.
     *
     * @return ICourseRegistrationMgr the singleton instance
     */
    public static ICourseRegistrationMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new CourseRegistrationMgr();
        }

        return singleInstance;
    }

    /**
     * Sort the list of course registrations of a course according to their
     * ascending normal alphabetical order of names of the lecture groups, ignoring
     * cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByLectureGroup(List<ICourseRegistration> courseRegistrations) {
        courseRegistrations.sort((o1, o2) -> {
            // in the case where there are no lectures, we don't care about
            // the ordering.
            if (o1.getLectureGroup() == null || o2.getLectureGroup() == null) {
                return 0;
            }

            String group1 = o1.getLectureGroup().getGroupName().toUpperCase();
            String group2 = o2.getLectureGroup().getGroupName().toUpperCase();

            // ascending order
            return group1.compareTo(group2);
        });
    }

    /**
     * Sort the list of course registrations of a course according to their
     * ascending normal alphabetical order of the names of the tutorial groups,
     * ignoring cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByTutorialGroup(List<ICourseRegistration> courseRegistrations) {
        courseRegistrations.sort((s1, s2) -> {
            // in the case where there are no tutorials, we don't care about
            // the ordering.
            if (s1.getTutorialGroup() == null || s2.getTutorialGroup() == null) {
                return 0;
            }

            String group1 = s1.getTutorialGroup().getGroupName().toUpperCase();
            String group2 = s2.getTutorialGroup().getGroupName().toUpperCase();

            // ascending order
            return group1.compareTo(group2);

        });
    }

    /**
     * Sort the list of course registrations of a course according to their
     * ascending normal alphabetical order of the names of the lab groups, ignoring
     * cases.
     *
     * @param courseRegistrations All the course registrations of the course.
     */
    private void sortByLabGroup(List<ICourseRegistration> courseRegistrations) {
        courseRegistrations.sort((o1, o2) -> {
            // in the case where there are no labs, we don't care about
            // the ordering.
            if (o1.getLabGroup() == null || o2.getLabGroup() == null) {
                return 0;
            }

            String group1 = o1.getLabGroup().getGroupName().toUpperCase();
            String group2 = o2.getLabGroup().getGroupName().toUpperCase();

            // ascending order
            return group1.compareTo(group2);
        });
    }
}
