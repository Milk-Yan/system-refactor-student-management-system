package com.softeng306.validation;

import com.softeng306.enums.CourseType;
import com.softeng306.domain.course.Course;
import com.softeng306.managers.CourseMgr;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CourseValidator {
    private static Scanner scanner = new Scanner(System.in);
    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });

    private static final String COURSE_ID_REGEX = "^[A-Z]{2}[0-9]{3,4}$";

    /**
     * Checks whether the inputted course ID is in the correct format.
     *
     * @param courseID The inputted course ID.
     * @return boolean indicates whether the inputted course ID is valid.
     */
    public static boolean checkValidCourseIDInput(String courseID) {
        boolean valid = RegexValidator.checkStringRegexFormat(courseID, COURSE_ID_REGEX);
        if (!valid) {
            System.out.println("Wrong format of course ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted course type is valid.
     *
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    public static boolean checkCourseTypeValidation(String courseType) {
        if (CourseType.getAllCourseType().contains(courseType)) {
            return true;
        }
        System.out.println("The course type is invalid. Please re-enter.");
        return false;
    }

    /**
     * Prompts the user to input an existing course.
     *
     * @return the inputted course.
     */
    public static Course checkCourseExists() {
        String courseID;
        Course currentCourse;
        while (true) {
            System.out.println("Enter course ID (-h to print all the course ID):");
            courseID = scanner.nextLine();
            while ("-h".equals(courseID)) {
                CourseMgr.getInstance().printAllCourseIds();
                courseID = scanner.nextLine();
            }

            System.setOut(dummyStream);
            currentCourse = checkCourseExists(courseID);
            if (currentCourse == null) {
                System.setOut(originalStream);
                System.out.println("Invalid Course ID. Please re-enter.");
            } else {
                break;
            }
        }
        System.setOut(originalStream);
        return currentCourse;
    }


    /**
     * Checks whether this course ID is used by other courses.
     *
     * @param courseID The inputted course ID.
     * @return the existing course or else null.
     */
    public static Course checkCourseExists(String courseID) {
        List<Course> anyCourse = CourseMgr.getInstance().getCourses().stream().filter(c -> courseID.equals(c.getCourseID())).collect(Collectors.toList());
        if (anyCourse.size() == 0) {
            return null;
        }
        System.out.println("Sorry. The course ID is used. This course already exists.");
        return anyCourse.get(0);
    }


}
