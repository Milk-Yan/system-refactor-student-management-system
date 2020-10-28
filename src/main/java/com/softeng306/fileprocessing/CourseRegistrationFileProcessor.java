package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.courseregistration.CourseRegistration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseRegistrationFileProcessor extends FileProcessor {

    private static final String COURSE_REGISTRATION_FILE_PATH = "data/courseRegistrationFile.json";

    /**
     * Writes a new course registration record into the file.
     *
     * @param courseRegistration courseRegistration to be added into file
     */
    public void writeCourseRegistrationIntoFile(CourseRegistration courseRegistration) {
        try {
            List<CourseRegistration> courseRegistrations = loadCourseRegistration();
            courseRegistrations.add(courseRegistration);

            writeToFile(COURSE_REGISTRATION_FILE_PATH, courseRegistrations);
        } catch (IOException e) {
            System.out.println("Error in adding a course registration to the file.");
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc} Loads a list of all the course registrations from {@value COURSE_REGISTRATION_FILE_PATH}.
     * @return A list of all the course registrations that is loaded from the file.
     */
    @Override
    public List<CourseRegistration> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File courseRegistrationFile = Paths.get(COURSE_REGISTRATION_FILE_PATH).toFile();
        ArrayList<CourseRegistration> allCourseRegistrations = new ArrayList<>();

        try {
            allCourseRegistrations = new ArrayList<>(Arrays.asList(objectMapper.readValue(courseRegistrationFile, CourseRegistration[].class)));
        } catch (IOException e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        }

        return allCourseRegistrations;
    }
}
