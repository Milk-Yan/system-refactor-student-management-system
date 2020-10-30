package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.mark.IStudentCourseMark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation a file processor for marks.
 * Used to write mark data to and from a file.
 * This class extends {@code FileProcessor}
 */
public class MarkFileProcessor extends FileProcessor<IStudentCourseMark> {
    /**
     * The path to the file for mark data.
     */
    private static final String STUDENT_COURSE_MARK_FILE = "data/studentCourseMarkFile.json";

    /**
     * Loads a list of all the marks from {@value STUDENT_COURSE_MARK_FILE}.
     *
     * @return A list of all the marks that is loaded from the file.
     */
    @Override
    public List<IStudentCourseMark> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File studentCourseMarkFile = Paths.get(STUDENT_COURSE_MARK_FILE).toFile();
        ArrayList<IStudentCourseMark> allStudentMarks = new ArrayList<>();

        try {
            allStudentMarks = new ArrayList<>(Arrays.asList(objectMapper.readValue(studentCourseMarkFile, IStudentCourseMark[].class)));
        } catch (IOException e) {
            System.out.println("Error occurs when loading student marks.");
            e.printStackTrace();
        }

        return allStudentMarks;
    }

    /**
     * Writes a new mark into {@value STUDENT_COURSE_MARK_FILE}.
     *
     * @param studentCourseMark The new studentCourseMark to write to the file
     */
    @Override
    public void writeNewEntryToFile(IStudentCourseMark studentCourseMark) {
        try {
            List<IStudentCourseMark> studentCourseMarks = loadFile();
            studentCourseMarks.add(studentCourseMark);

            writeToFile(STUDENT_COURSE_MARK_FILE, studentCourseMarks);
        } catch (IOException e) {
            System.out.println("Error in adding a studentCourseMark to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Modifies a list of marks in {@value STUDENT_COURSE_MARK_FILE}.
     *
     * @param updatedStudentCourseMarks The list of all marks to modify in the file.
     */
    @Override
    public void updateFileContents(List<IStudentCourseMark> updatedStudentCourseMarks) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(STUDENT_COURSE_MARK_FILE).toFile(), updatedStudentCourseMarks);
        } catch (IOException e) {
            System.out.println("Error in backing up marks.");
            e.printStackTrace();
        }
    }

}
