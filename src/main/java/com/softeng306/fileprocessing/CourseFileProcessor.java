package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.Course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseFileProcessor extends FileProcessor {

    private static final String COURSE_FILE_PATH = "data/courseFile.json";

    /**
     * Write a new course information into the file.
     *
     * @param course a course to be added into file
     */
    public void writeCourseIntoFile(Course course) {
        try {
            List<Course> courses = loadCourses();
            courses.add(course);

            writeToFile(COURSE_FILE_PATH, courses);
        } catch (IOException e) {
            System.out.println("Error in adding a course to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Load all the courses' information from file into the system.
     *
     * @return a list of all the courses.
     */
    public List<Course> loadCourses() {
        ObjectMapper objectMapper = new ObjectMapper();
        File courseFile = Paths.get(COURSE_FILE_PATH).toFile();
        ArrayList<Course> allCourses = new ArrayList<>();

        try {
            allCourses = new ArrayList<>(Arrays.asList(objectMapper.readValue(courseFile, Course[].class)));
        } catch (IOException e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        }

        return allCourses;
    }

    /**
     * Backs up all the changes of courses made into the file.
     * NOTE THAT BACKUPS MAY NOT WORK, NOT TESTED. WE shouldn't need them in the future.
     *
     * @param courses courses to be backed up
     */
    public void backUpCourse(List<Course> courses) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(COURSE_FILE_PATH).toFile(), courses);
        } catch (IOException e) {
            System.out.println("Error in backing up courses.");
            e.printStackTrace();
        }
    }
}
