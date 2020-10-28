package com.softeng306.domain.mark;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.student.Student;

import java.util.List;

/**
 * Represents a student mark record associated with one student and a course.
 * Both students and courses can have multiple student mark record, but cannot be duplicate.
 */

public class Mark {

    private Student student;
    private Course course;

    private List<MainComponentMark> mainComponentMarks;

    private double totalMark;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Mark() {

    }

    /**
     * Creates a new student mark record with the student of this student mark record, the course of this student mark record.
     * the course work marks of this student mark record, the total mark of this student mark record.
     *
     * @param student         The student of this student mark record.
     * @param course          The course of this student mark record.
     * @param mainComponentMarks The course work marks of this student mark record.
     * @param totalMark       The total mark of this student mark record.
     */
    public Mark(Student student, Course course, List<MainComponentMark> mainComponentMarks, double totalMark) {
        this.student = student;
        this.course = course;
        this.mainComponentMarks = mainComponentMarks;
        this.totalMark = totalMark;
    }

    /**
     * Gets the student of this student mark record.
     *
     * @return the student of this student mark record.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Gets the course of this student mark record.
     *
     * @return the course of this student mark record.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Gets the course work marks of this student mark record.
     *
     * @return a list contains the course work marks of this student mark record.
     */
    public List<MainComponentMark> getMainComponentMarks() {
        return mainComponentMarks;
    }

    /**
     * Gets the total mark of this student mark record.
     *
     * @return the total mark of this student mark record.
     */
    public double getTotalMark() {
        return totalMark;
    }

    /**
     * Sets the main course work marks of this student mark record.
     *
     * @param courseWorkName The name of this main course work.
     * @param result         The mark obtained in this main course work.
     */
    public void setMainComponentMark(String courseWorkName, double result) {
        for (MainComponentMark mainComponentMark : mainComponentMarks) {
            if (mainComponentMark.getMainComponent().getComponentName().equals(courseWorkName)) {
                if (mainComponentMark.hasSubComponentMarks()) {
                    System.out.println("This main assessment is not stand alone");
                    return;
                }

                double previousResult = mainComponentMark.getMark();
                mainComponentMark.setMark(result);

                this.totalMark += (result - previousResult) * mainComponentMark.getMainComponent().getComponentWeight() / 100d;
                System.out.println("The course work component is successfully set to: " + result);
                System.out.println("The course total mark is updated to: " + this.totalMark);
                return;
            }
        }

        System.out.println("This main assessment component does not exist...");

    }


    /**
     * Sets the sub course work marks of this student mark record.
     *
     * @param courseWorkName The name of this sub course work.
     * @param result         The mark obtained in this sub course work.
     */
    public void setSubComponentMark(String courseWorkName, double result) {
        for (MainComponentMark mainComponentMark : mainComponentMarks) {
            SubComponentMark subComponentMark = mainComponentMark.getSubComponentMark(courseWorkName);
            if (subComponentMark != null) {
                // update subcomponent value
                double previousResult = subComponentMark.getMark();
                subComponentMark.setMark(result);
                double markIncInMain = (result - previousResult) * subComponentMark.getSubComponent().getComponentWeight() / 100d;

                System.out.println("The sub course work component is successfully set to: " + result);
                System.out.println("The main course work component increase by: " + markIncInMain);

                // update main component value
                mainComponentMark.setMark(mainComponentMark.getMark() + markIncInMain);

                // update total mark
                this.totalMark += markIncInMain * mainComponentMark.getMainComponent().getComponentWeight() / 100d;

                System.out.println("The course total mark is updated to: " + this.totalMark);
            }
        }
    }
}
