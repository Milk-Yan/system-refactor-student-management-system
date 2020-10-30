package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.student.IStudent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation of a file processor for student data.
 * Used to read and write student data from a file.
 * This class extends {@code FileProcessor}
 */
public class StudentFileProcessor extends FileProcessor<IStudent> {
    /**
     * Path to the file storing student data.
     */
    private static final String STUDENT_FILE_PATH = "data/studentFile.json";

    /**
     * Loads a list of all the students from {@value STUDENT_FILE_PATH}.
     *
     * @return A list of all the students loaded from the file.
     */
    @Override
    public List<IStudent> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File studentFile = Paths.get(STUDENT_FILE_PATH).toFile();
        ArrayList<IStudent> allStudents = new ArrayList<>();

        try {
            allStudents = new ArrayList<>(Arrays.asList(objectMapper.readValue(studentFile, IStudent[].class)));
        } catch (IOException e) {
            System.out.println("Error occurs when loading students.");
            e.printStackTrace();
        }

        return allStudents;
    }

    /**
     * Writes a new student into {@value STUDENT_FILE_PATH}.
     *
     * @param student The new student to write to the file.
     */
    @Override
    public void writeNewEntryToFile(IStudent student) {
        try {
            List<IStudent> students = loadFile();
            students.add(student);

            writeToFile(STUDENT_FILE_PATH, students);
        } catch (IOException e) {
            System.out.println("Error in adding a student to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Modifies a list of students in {@value STUDENT_FILE_PATH}.
     *
     * @param updatedStudents the list of all students to modify
     */
    @Override
    public void updateFileContents(List<IStudent> updatedStudents) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(STUDENT_FILE_PATH).toFile(), updatedStudents);
        } catch (IOException e) {
            System.out.println("Error in backing up students.");
            e.printStackTrace();
        }
    }

}
