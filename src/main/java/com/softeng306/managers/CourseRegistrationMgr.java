package com.softeng306.managers;

import com.softeng306.Enum.GroupType;
import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.MainMenuIO;
import com.softeng306.validation.*;

import java.util.*;

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
    public void registerCourse() {
        MainMenuIO.printMethodCall("registerCourse");

        Student currentStudent = StudentValidator.checkStudentExists();
        String studentID = currentStudent.getStudentID();

        DepartmentValidator.checkCourseDepartmentExists();

        Course currentCourse = CourseValidator.checkCourseExists();
        String courseID = currentCourse.getCourseID();


        if (CourseRegistrationValidator.checkCourseRegistrationExists(studentID, courseID) != null) {
            return;
        }

        if (currentCourse.getMainComponents().size() == 0) {
            System.out.println("Professor " + currentCourse.getProfInCharge().getProfName() + " is preparing the assessment. Please try to register other courses.");
            return;
        }

        if (currentCourse.getVacancies() == 0) {
            System.out.println("Sorry, the course has no vacancies any more.");
            return;
        }

        System.out.println("Student " + currentStudent.getStudentName() + " with ID: " + currentStudent.getStudentID() +
                " wants to register " + currentCourse.getCourseID() + " " + currentCourse.getCourseName());

        List<Group> lecGroups = new ArrayList<>(0);
        lecGroups.addAll(currentCourse.getLectureGroups());

        GroupMgr groupMgr = GroupMgr.getInstance();

        Group selectedLectureGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LECTURE_GROUP, lecGroups);

        List<Group> tutGroups = new ArrayList<>(0);
        tutGroups.addAll(currentCourse.getTutorialGroups());

        Group selectedTutorialGroup = groupMgr.printGroupWithVacancyInfo(GroupType.TUTORIAL_GROUP, tutGroups);

        List<Group> labGroups = new ArrayList<>(0);
        labGroups.addAll(currentCourse.getLabGroups());

        Group selectedLabGroup = groupMgr.printGroupWithVacancyInfo(GroupType.LAB_GROUP, labGroups);

        currentCourse.enrolledIn();
        CourseRegistration courseRegistration = new CourseRegistration(currentStudent, currentCourse, selectedLectureGroup, selectedTutorialGroup, selectedLabGroup);
        FILEMgr.writeCourseRegistrationIntoFile(courseRegistration);

        courseRegistrations.add(courseRegistration);

        MarkMgr.getInstance().getMarks().add(MarkMgr.getInstance().initializeMark(currentStudent, currentCourse));

        System.out.println("Course registration successful!");
        System.out.print("Student: " + currentStudent.getStudentName());
        System.out.print("\tLecture Group: " + selectedLectureGroup.getGroupName());
        if (currentCourse.getTutorialGroups().size() != 0) {
            System.out.print("\tTutorial Group: " + selectedTutorialGroup.getGroupName());
        }
        if (currentCourse.getLabGroups().size() != 0) {
            System.out.print("\tLab Group: " + selectedLabGroup.getGroupName());
        }
        System.out.println();
    }

    /**
     * Prints the students in a course according to their lecture group, tutorial group or lab group.
     */
    public void printStudents() {
        MainMenuIO.printMethodCall("printStudent");
        Course currentCourse = CourseValidator.checkCourseExists();

        System.out.println("Print student by: ");
        System.out.println("(1) Lecture group");
        System.out.println("(2) Tutorial group");
        System.out.println("(3) Lab group");
        // READ courseRegistrationFILE
        // return List of Object(student,course,lecture,tut,lab)
        List<CourseRegistration> allCourseRegistrations = FILEMgr.loadCourseRegistration();


        List<CourseRegistration> stuArray = new ArrayList<>(0);
        for (CourseRegistration courseRegistration : allCourseRegistrations) {
            if (courseRegistration.getCourse().getCourseID().equals(currentCourse.getCourseID())) {
                stuArray.add(courseRegistration);
            }
        }


        int opt;
        do {
            opt = scanner.nextInt();
            scanner.nextLine();

            // TODO: replace these common ui elements with a library
            System.out.println("------------------------------------------------------");

            if (stuArray.size() == 0) {
                System.out.println("No one has registered this course yet.");
            }

            if (opt == 1) { // print by LECTURE
                Group newLecGroup = null;
                sortByLectureGroup(stuArray);
                if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {  // loop through all of CourseRegistration Obj
                        if (newLecGroup == null || !newLecGroup.getGroupName().equals(stuArray.get(i).getLectureGroup().getGroupName())) {  // if new lecture group print out group name
                            newLecGroup = stuArray.get(i).getLectureGroup();
                            System.out.println("Lecture group : " + newLecGroup.getGroupName());
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }


            } else if (opt == 2) { // print by TUTORIAL
                Group newTutGroup = null;
                sortByTutorialGroup(stuArray);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getTutorialGroups().size() == 0) {
                    System.out.println("This course does not contain any tutorial group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (newTutGroup == null || !newTutGroup.getGroupName().equals(stuArray.get(i).getTutorialGroup().getGroupName())) {
                            newTutGroup = stuArray.get(i).getTutorialGroup();
                            System.out.println("Tutorial group : " + newTutGroup.getGroupName());
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else if (opt == 3) { // print by LAB
                Group newLabGroup = null;
                sortByLabGroup(stuArray);
                if (stuArray.size() > 0 && stuArray.get(0).getCourse().getLabGroups().size() == 0) {
                    System.out.println("This course does not contain any lab group.");
                } else if (stuArray.size() > 0) {
                    for (int i = 0; i < stuArray.size(); i++) {
                        if (newLabGroup == null || !newLabGroup.getGroupName().equals(stuArray.get(i).getLabGroup().getGroupName())) {
                            newLabGroup = stuArray.get(i).getLabGroup();
                            System.out.println("Lab group : " + newLabGroup.getGroupName());
                        }
                        System.out.print("Student Name: " + stuArray.get(i).getStudent().getStudentName());
                        System.out.println(" Student ID: " + stuArray.get(i).getStudent().getStudentID());
                    }
                    System.out.println();
                }

            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
            System.out.println("------------------------------------------------------");
        } while (opt < 1 || opt > 3);
    }

    /**
     * Return the list of all course registrations in the system.
     * @return An list of all course registrations.
     */
    public List<CourseRegistration> getCourseRegistrations() {
        return courseRegistrations;
    }

    /**
     * Sort the list of course registrations of a course according to their ascending
     * normal alphabetical order of names of the lecture groups, ignoring cases.
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




}
