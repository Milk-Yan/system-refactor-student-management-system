package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.courseregistration.CourseRegistration;
import com.softeng306.domain.mark.Mark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkFileProcessor extends FileProcessor<Mark> {

    private static final String MARK_FILE_PATH = "data/markFile.json";

    /**
     * Loads a list of all the marks from {@value MARK_FILE_PATH}.
     * @return A list of all the marks that is loaded from the file.
     */
    @Override
    public List<Mark> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File markFile = Paths.get(MARK_FILE_PATH).toFile();
        ArrayList<Mark> allStudentMarks = new ArrayList<>();

        try {
            allStudentMarks = new ArrayList<>(Arrays.asList(objectMapper.readValue(markFile, Mark[].class)));
        } catch (IOException e) {
            System.out.println("Error occurs when loading student marks.");
            e.printStackTrace();
        }

        return allStudentMarks;
    }

    /**
     * Writes a new mark into {@value MARK_FILE_PATH}.
     * @param mark the new mark to write to the file
     */
    @Override
    public void writeNewEntryToFile(Mark mark) {
        try {
            List<Mark> marks = loadFile();
            marks.add(mark);

            writeToFile(MARK_FILE_PATH, marks);
        } catch (IOException e) {
            System.out.println("Error in adding a mark to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Writes the updated marks to {@value MARK_FILE_PATH}.
     * @param updatedMarks the list of all marks, with updated marks
     */
    @Override
    public void updateFileContents(List<Mark> updatedMarks) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(MARK_FILE_PATH).toFile(), updatedMarks);
        } catch (IOException e) {
            System.out.println("Error in backing up marks.");
            e.printStackTrace();
        }
    }
}
