package com.softeng306.managers;


import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.MainComponentMark;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.mark.SubComponentMark;
import com.softeng306.domain.student.Student;
import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.StudentMgrIO;
import com.softeng306.validation.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Manages the student related operations.
 * Contains addStudent, generateStudentId
 */
public class StudentMgr {
    private Scanner scanner = new Scanner(System.in);

    /**
     * A list of all the students in this school.
     */
    private List<Student> students;

    private static StudentMgr singleInstance = null;

    private static MarkCalculator markCalculator;

    /**
     * Uses idNumber to generate student ID.
     */
    private static int idNumber = 1800000;

    /**
     * Override default constructor to implement singleton pattern
     */
    private StudentMgr(List<Student> students, MarkCalculator markCalculator) {
        this.markCalculator = markCalculator;
        this.students = students;
    }

    /**
     * Return the StudentMgr singleton, if not initialised already, create an instance.
     *
     * @return StudentMgr the singleton instance
     */
    public static StudentMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new StudentMgr(FILEMgr.loadStudents(), new MarkCalculator());
        }

        return singleInstance;
    }

    public void addStudent(String id, String name, String school, String gender, int year) {
        Student currentStudent = new Student(id, name);

        currentStudent.setStudentSchool(Department.valueOf(school));  //Set school
        currentStudent.setGender(Gender.valueOf(gender));      //gender
        currentStudent.setStudentYear(year);   //student year

        FILEMgr.writeStudentsIntoFile(currentStudent);
        students.add(currentStudent);
    }

    /**
     * Prints transcript (Results of course taken) for a particular student
     */
    public void printStudentTranscript() {
        String studentID = new StudentMgrIO().readStudentIdFromUser();

        double studentGPA = 0d;
        int thisStudentAU = 0;

        List<Mark> thisStudentMark = new ArrayList<>();
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getStudent().getStudentID().equals(studentID)) {
                thisStudentMark.add(mark);
                thisStudentAU += mark.getCourse().getAU();
            }
        }

        if (thisStudentMark.isEmpty()) {
            System.out.println("------ No transcript ready for this student yet ------");
            return;
        }
        System.out.println("----------------- Official Transcript ------------------");
        System.out.print("Student Name: " + thisStudentMark.get(0).getStudent().getStudentName());
        System.out.println("\tStudent ID: " + thisStudentMark.get(0).getStudent().getStudentID());
        System.out.println("AU for this semester: " + thisStudentAU);
        System.out.println();


        for (Mark mark : thisStudentMark) {
            System.out.print("Course ID: " + mark.getCourse().getCourseID());
            System.out.println("\tCourse Name: " + mark.getCourse().getCourseName());

            for (MainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                MainComponent mainComponent = mainComponentMark.getMainComponent();
                Double result = mainComponentMark.getMark();

                System.out.println("Main Assessment: " + mainComponent.getComponentName() + " ----- (" + mainComponent.getComponentWeight() + "%)");
                int mainAssessmentWeight = mainComponent.getComponentWeight();

                for (SubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    System.out.print("Sub Assessment: " + subComponent.getComponentName() + " -- (" + subComponent.getComponentWeight() + "% * " + mainAssessmentWeight + "%) --- ");
                    String subAssessmentName = subComponent.getComponentName();
                    System.out.println("Mark: " + String.valueOf(subComponentMark.getMark()));
                }

                System.out.println("Main Assessment Total: " + result);
                System.out.println();
            }

            System.out.println("Course Total: " + mark.getTotalMark());
            studentGPA += markCalculator.gpaCalculator(mark) * mark.getCourse().getAU();
            System.out.println();
        }
        studentGPA /= thisStudentAU;
        System.out.println("GPA for this semester: " + studentGPA);
        if (studentGPA >= 4.50) {
            System.out.println("On track of First Class Honor!");
        } else if (studentGPA >= 4.0) {
            System.out.println("On track of Second Upper Class Honor!");
        } else if (studentGPA >= 3.5) {
            System.out.println("On track of Second Lower Class Honor!");
        } else if (studentGPA >= 3) {
            System.out.println("On track of Third Class Honor!");
        } else {
            System.out.println("Advice: Study hard");
        }
        System.out.println("------------------ End of Transcript -------------------");

    }

    /**
     * Return the list of all students in the system.
     *
     * @return An list of all students.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudentIds() {
        for (Student s : students) {
            System.out.println(s.getStudentID());
        }
    }

    /**
     * Sets the idNumber variable of this student manager class.
     *
     * @param idNumber static variable idNumber of this class.
     */
    public static void setIdNumber(int idNumber) {
        StudentMgr.idNumber = idNumber;
    }

    /**
     * Generates the ID of a new student.
     *
     * @return the generated student ID.
     */
    public String generateStudentID() {
        String generateStudentID;
        boolean studentIDUsed;
        do {
            int rand = (int) (Math.random() * ((76 - 65) + 1)) + 65;
            String lastPlace = Character.toString((char) rand);
            idNumber += 1;
            generateStudentID = "U" + idNumber + lastPlace;
            studentIDUsed = false;
            for (Student student : this.students) {
                if (generateStudentID.equals(student.getStudentID())) {
                    studentIDUsed = true;
                    break;
                }
            }
            if (!studentIDUsed) {
                break;
            }
        } while (true);
        return generateStudentID;
    }

    public List<String> getExistingStudentIds() {
        List<String> existingStudentIds = new ArrayList<>();
        students.forEach(student -> existingStudentIds.add(student.getStudentID()));
        return existingStudentIds;
    }

    public boolean studentHasCourses(String studentId) {
        List<String> studentCourses = CourseRegistrationMgr.getInstance().getCourseIdsForStudentId(studentId);
        return !studentCourses.isEmpty();
    }

    public Student getStudentFromId(String studentId) {
        Optional<Student> optionalStudent = students.stream().filter(s -> studentId.equals(s.getStudentID())).findFirst();
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else {
            return null;
        }
    }

    public String getStudentName(String studentId) {
        Student student = getStudentFromId(studentId);
        return student.getStudentName();
    }

}
