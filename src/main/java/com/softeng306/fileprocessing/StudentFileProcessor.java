package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.student.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentFileProcessor extends FileProcessor {

    private static final String STUDENT_FILE_PATH = "data/studentFile.json";

    /**
     * Write a new student information into the file.
     *
     * @param student a student to be added into the file
     */
    public static void writeStudentsIntoFile(Student student) {
        try {
            List<Student> students = loadStudents();
            students.add(student);

            writeToFile(STUDENT_FILE_PATH, students);
        } catch (IOException e) {
            System.out.println("Error in adding a student to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Load all the students' information from file into the system.
     *
     * @return a list of all the students.
     */
    public static List<Student> loadStudents() {
        ObjectMapper objectMapper = new ObjectMapper();
        File studentFile = Paths.get(STUDENT_FILE_PATH).toFile();
        ArrayList<Student> allStudents = new ArrayList<>();

        try {
            allStudents = new ArrayList<>(Arrays.asList(objectMapper.readValue(studentFile, Student[].class)));
            updateStudentIDs(allStudents);
        } catch (IOException e) {
            System.out.println("Error occurs when loading students.");
            e.printStackTrace();
        }

        return allStudents;
    }
}
