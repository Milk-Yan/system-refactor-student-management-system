package com.softeng306.managers;

import com.softeng306.domain.exceptions.StudentNotFoundException;
import com.softeng306.domain.student.IStudent;

import com.softeng306.domain.student.Student;
import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.StudentFileProcessor;

import com.softeng306.enums.Department;
import com.softeng306.enums.Gender;

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
    private List<IStudent> students;

    private static StudentMgr singleInstance = null;

    private final IFileProcessor<IStudent> studentFileProcessor;

    /**
     * Override default constructor to implement singleton pattern
     */
    private StudentMgr() {
        studentFileProcessor = new StudentFileProcessor();
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

    public void createNewStudent(String id, String name, String school, String gender, int year) {
        IStudent currentStudent = new Student(id, name);

        currentStudent.setStudentSchool(Department.valueOf(school));  //Set school
        currentStudent.setGender(Gender.valueOf(gender));      //gender
        currentStudent.setStudentYear(year);   //student year

        studentFileProcessor.writeNewEntryToFile(currentStudent);
        students.add(currentStudent);
    }

    /**
     * Return the list of all students in the system.
     *
     * @return An list of all students.
     */
    public List<IStudent> getStudents() {
        return students;
    }

    /**
     * Displays a list of IDs of all the students.
     */
    public void printAllStudentIds() {
        for (IStudent s : students) {
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
        char randomEndNumber = (char) ((rand * (76 - 65) + 1) + 65);

        return "U" + (smallestAvailableIDNumber + 1) + randomEndNumber;
    }

    /**
     * Find the largest student ID by the number value in all the students.
     * If there is no student in DB, this is default 1800000 (2018 into Uni)
     */
    private int findLargestStudentID() {
        int recentStudentID = 0;
        for (IStudent student : students) {
            recentStudentID = Math.max(recentStudentID, Integer.parseInt(student.getStudentID().substring(1, 8)));
        }

        return recentStudentID > 0 ? recentStudentID : 1800000;
    }

    public boolean studentHasCourses(String studentId) {
        List<String> studentCourses = CourseRegistrationMgr.getInstance().getCourseIdsForStudentId(studentId);
        return !studentCourses.isEmpty();
    }

    public IStudent getStudentFromId(String studentId) throws StudentNotFoundException {
        Optional<IStudent> student = students
                .stream()
                .filter(s -> studentId.equals(s.getStudentID()))
                .findFirst();

        if (!student.isPresent()) {
            throw new StudentNotFoundException(studentId);
        }

        return student.get();
    }

    public String getStudentName(String studentId) throws StudentNotFoundException {
        IStudent student = getStudentFromId(studentId);
        return student.getStudentName();
    }

    public List<String> generateStudentInformationStrings() {
        List<String> studentInformationStrings = new ArrayList<>();
        for (IStudent student : StudentMgr.getInstance().getStudents()) {
            String GPA = "not available";
            if (Double.compare(student.getGPA(), 0.0) != 0) {
                GPA = String.valueOf(student.getGPA());
            }
            studentInformationStrings.add(" " + student.getStudentID() + " | " + student.getStudentName() + " | " + student.getStudentSchool() + " | " + student.getGender() + " | " + student.getStudentYear() + " | " + GPA);
        }
        return studentInformationStrings;
    }

    /**
     * Checks whether this student ID is used by other students.
     *
     * @param studentID This student's ID.
     * @return the existing student or else null.
     */
    public boolean studentExists(String studentID) {
        Optional<IStudent> student = students.stream()
                .filter(s -> studentID.equals(s.getStudentID()))
                .findFirst();

        return student.isPresent();
    }

}
