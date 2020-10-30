package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.courseregistration.ICourseRegistration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation of a file processor for course registrations.
 * Used to read and write course registrations from a file.
 */
public class CourseRegistrationFileProcessor extends FileProcessor<ICourseRegistration> {
    /**
     * Path to the file for course registration data.
     */
    private static final String COURSE_REGISTRATION_FILE_PATH = "data/courseRegistrationFile.json";

    /**
     * Loads a list of all the course registrations from {@value COURSE_REGISTRATION_FILE_PATH}.
     *
     * @return A list of all the course registrations loaded from the file.
     */
    @Override
    public List<ICourseRegistration> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File courseRegistrationFile = Paths.get(COURSE_REGISTRATION_FILE_PATH).toFile();
        ArrayList<ICourseRegistration> allCourseRegistrations = new ArrayList<>();

        try {
            allCourseRegistrations = new ArrayList<>(Arrays.asList(objectMapper.readValue(courseRegistrationFile, ICourseRegistration[].class)));
        } catch (IOException e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        }

        return allCourseRegistrations;
    }

    /**
     * Writes a new course registration into {@value COURSE_REGISTRATION_FILE_PATH}.
     *
     * @param courseRegistration The new course registration to write to the file.
     */
    @Override
    public void writeNewEntryToFile(ICourseRegistration courseRegistration) {
        try {
            List<ICourseRegistration> courseRegistrations = loadFile();
            courseRegistrations.add(courseRegistration);

            writeToFile(COURSE_REGISTRATION_FILE_PATH, courseRegistrations);
        } catch (IOException e) {
            System.out.println("Error in adding a course registration to the file.");
            e.printStackTrace();
        }
    }

    /**
     * Updates a list of course registrations in {@value COURSE_REGISTRATION_FILE_PATH}.
     *
     * @param updatedCourseRegistrations the list of all course registrations to modify
     */
    @Override
    public void updateFileContents(List<ICourseRegistration> updatedCourseRegistrations) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(Paths.get(COURSE_REGISTRATION_FILE_PATH).toFile(), updatedCourseRegistrations);
        } catch (IOException e) {
            System.out.println("Error in backing up course registrations.");
            e.printStackTrace();
        }
    }

}
