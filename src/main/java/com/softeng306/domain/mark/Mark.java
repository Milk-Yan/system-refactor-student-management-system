package com.softeng306.domain.mark;

import com.softeng306.domain.course.Course;
import com.softeng306.domain.student.IStudent;

import java.util.List;

/**
 * Represents a student mark record associated with one student and a course.
 * Both students and courses can have multiple student mark record, but cannot be duplicate.
 */

public class Mark implements IMark {

    private IStudent student;
    private Course course;

    private List<IMainComponentMark> courseWorkMarks;

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
     * @param courseWorkMarks The course work marks of this student mark record.
     * @param totalMark       The total mark of this student mark record.
     */
    public Mark(IStudent student, Course course, List<IMainComponentMark> courseWorkMarks, double totalMark) {
        this.student = student;
        this.course = course;
        this.courseWorkMarks = courseWorkMarks;
        this.totalMark = totalMark;
    }

    @Override
    public IStudent getStudent() {
        return student;
    }

    @Override
    public Course getCourse() {
        return course;
    }

    @Override
    public List<IMainComponentMark> getCourseWorkMarks() {
        return courseWorkMarks;
    }

    @Override
    public double getTotalMark() {
        return totalMark;
    }

    @Override
    public void setMainComponentMark(String courseWorkName, double result) {
        for (IMainComponentMark mainComponentMark : courseWorkMarks) {
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


    @Override
    public void setSubComponentMark(String courseWorkName, double result) {
        for (IMainComponentMark mainComponentMark : courseWorkMarks) {
            ISubComponentMark subComponentMark = mainComponentMark.getSubComponentMark(courseWorkName);
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
