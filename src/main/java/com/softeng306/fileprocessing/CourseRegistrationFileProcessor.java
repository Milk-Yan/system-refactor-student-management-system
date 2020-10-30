package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.course.courseregistration.ICourseRegistration;
import com.softeng306.domain.course.courseregistration.CourseRegistration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseRegistrationFileProcessor extends FileProcessor<ICourseRegistration> {

    private static final String COURSE_REGISTRATION_FILE_PATH = "data/courseRegistrationFile.json";

    /**
     * Loads a list of all the course registrations from {@value COURSE_REGISTRATION_FILE_PATH}.
     * @return A list of all the course registrations that is loaded from the file.
     */
    @Override
    public List<ICourseRegistration> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File courseRegistrationFile = Paths.get(COURSE_REGISTRATION_FILE_PATH).toFile();
        ArrayList<ICourseRegistration> allCourseRegistrations = new ArrayList<>();

        try {
            allCourseRegistrations = new ArrayList<>(Arrays.asList(objectMapper.readValue(courseRegistrationFile, CourseRegistration[].class)));
        } catch (IOException e) {
            System.out.println("Error happens when loading courses.");
            e.printStackTrace();
        }

        return allCourseRegistrations;
    }

    /**
     * Writes a new course registration into {@value COURSE_REGISTRATION_FILE_PATH}.
     * @param courseRegistration the new course registration to write to the file
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
     * Writes the updated course registrations to {@value COURSE_REGISTRATION_FILE_PATH}.
     * @param updatedCourseRegistrations the list of all course registrations,
     *                                   with updated course registrations
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
