package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.student.IStudent;
import com.softeng306.domain.student.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentFileProcessor extends FileProcessor<IStudent> {

    private static final String STUDENT_FILE_PATH = "data/studentFile.json";

    /**
     * Loads a list of all the students from {@value STUDENT_FILE_PATH}.
     * @return A list of all the students that is loaded from the file.
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
     * @param student the new professor to write to the file
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
     * Writes the updated students to {@value STUDENT_FILE_PATH}.
     * @param updatedStudents the list of all students, with updated students
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
