package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.Course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseFileProcessor extends FileProcessor<ICourse> {

    private static final String COURSE_FILE_PATH = "data/courseFile.json";

    /**
     * Loads a list of all the courses from {@value COURSE_FILE_PATH}.
     * @return A list of all the courses that is loaded from the file.
     */
    @Override
    public List<ICourse> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File courseFile = Paths.get(COURSE_FILE_PATH).toFile();
        ArrayList<ICourse> allCourses = new ArrayList<>();

        try {
            allCourses = new ArrayList<>(Arrays.asList(objectMapper.readValue(courseFile, Course[].class)));
        } catch (IOException e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        }

        return allCourses;
    }

    /**
     * Writes a new course into {@value COURSE_FILE_PATH}.
     * @param course the new course to write to the file
     */
    @Override
    public void writeNewEntryToFile(ICourse course) {
        try {
            List<ICourse> courses = loadFile();
            courses.add(course);

            writeToFile(COURSE_FILE_PATH, courses);
        } catch (IOException e) {
            System.out.println("Error in adding a course to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Writes the updated courses to {@value COURSE_FILE_PATH}.
     * @param updatedCourses the list of all courses, with updated courses
     */
    @Override
    public void updateFileContents(List<ICourse> updatedCourses) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(COURSE_FILE_PATH).toFile(), updatedCourses);
        } catch (IOException e) {
            System.out.println("Error in backing up courses.");
            e.printStackTrace();
        }
    }
}
