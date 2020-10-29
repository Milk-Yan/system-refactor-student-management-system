package com.softeng306.managers;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.MainComponentMark;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.mark.SubComponentMark;
import com.softeng306.domain.student.Student;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.StudentFileProcessor;

import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

import com.softeng306.io.StudentMgrIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

/**
 * Manages the student related operations.
 * Contains addStudent, generateStudentId
 */
public class StudentMgr {
    /**
     * A list of all the students in this school.
     */
    private List<Student> students;

    private static StudentMgr singleInstance = null;

    private final MarkCalculator markCalculator;

    private final IFileProcessor<Student> studentFileProcessor;

    /**
     * Override default constructor to implement singleton pattern
     */
    private StudentMgr() {
        studentFileProcessor = new StudentFileProcessor();
        markCalculator = new MarkCalculator();
        students = studentFileProcessor.loadFile();
    }

    /**
     * Return the StudentMgr singleton, if not initialised already, create an instance.
     *
     * @return StudentMgr the singleton instance
     */
    public static StudentMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new StudentMgr();
        }

        return singleInstance;
    }

    public void addStudent(String id, String name, String school, String gender, int year) {
        Student currentStudent = new Student(id, name);

        currentStudent.setStudentSchool(Department.valueOf(school));  //Set school
        currentStudent.setGender(Gender.valueOf(gender));      //gender
        currentStudent.setStudentYear(year);   //student year

        studentFileProcessor.writeNewEntryToFile(currentStudent);
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

                for (SubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    System.out.print("Sub Assessment: " + subComponent.getComponentName() + " -- (" + subComponent.getComponentWeight() + "% * " +
                            mainComponent.getComponentWeight() + "%) --- ");

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
     * Generates the ID of a new student.
     *
     * @return the generated student ID.
     */
    public String generateStudentID() {
        int smallestAvailableIDNumber = findLargestStudentID();

        // randomly generate the last character from A-Z.
        int rand = new Random().nextInt();
        char randomEndNumber = (char) ((rand * (76-65) + 1) + 65);

        return "U" + (smallestAvailableIDNumber+1) + randomEndNumber;
    }

    /**
     * Find the largest student ID by the number value in all the students.
     * If there is no student in DB, this is default 1800000 (2018 into Uni)
     */
    private int findLargestStudentID() {
        int recentStudentID = 0;
        for (Student student : students) {
            recentStudentID = Math.max(recentStudentID, Integer.parseInt(student.getStudentID().substring(1, 8)));
        }

        return recentStudentID > 0 ? recentStudentID : 1800000;
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

    public List<String> generateStudentInformationStrings() {
        List<String> studentInformationStrings = new ArrayList<>();
        for (Student student : StudentMgr.getInstance().getStudents()) {
            String GPA = "not available";
            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            studentInformationStrings.add(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
        return studentInformationStrings;
    }

}
