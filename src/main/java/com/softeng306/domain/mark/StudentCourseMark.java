package com.softeng306.domain.mark;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.student.IStudent;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student mark record associated with one student and a course.
 * Both students and courses can have multiple student mark record, but cannot be duplicate.
 */
public class StudentCourseMark implements IStudentCourseMark {

    private IStudent student;
    private ICourse course;

    private List<IMainComponentMark> courseWorkMarks;

    private double totalMark;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public StudentCourseMark() {

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
    public StudentCourseMark(IStudent student, ICourse course, List<IMainComponentMark> courseWorkMarks, double totalMark) {
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
    public ICourse getCourse() {
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
    public List<Double> setMainComponentMark(String courseWorkName, double result) throws IllegalArgumentException {
        List<Double> resultList = new ArrayList<>();
        for (IMainComponentMark mainComponentMark : courseWorkMarks) {
            if (mainComponentMark.getMainComponent().getName().equals(courseWorkName)) {
                if (mainComponentMark.hasSubComponentMarks()) {

                    return resultList;
                }

                double previousResult = mainComponentMark.getMark();
                mainComponentMark.setMark(result);

                totalMark += (result - previousResult) * mainComponentMark.getMainComponent().getWeight() / 100d;
                resultList.add(result);
                resultList.add(totalMark);
                return resultList;
            }
        }

        throw new IllegalArgumentException("This main assessment component does not exist...");
    }

    @Override
    public List<Double> setSubComponentMark(String courseWorkName, double result) {
        List<Double> resultList = new ArrayList<>();
        for (IMainComponentMark mainComponentMark : courseWorkMarks) {
            ISubComponentMark subComponentMark = mainComponentMark.getSubComponentMark(courseWorkName);
            if (subComponentMark != null) {
                // update subcomponent value
                double previousResult = subComponentMark.getMark();
                subComponentMark.setMark(result);
                double markIncInMain = (result - previousResult) * subComponentMark.getSubComponent().getWeight() / 100d;

                resultList.add(result);
                resultList.add(markIncInMain);

                // update main component value
                mainComponentMark.setMark(mainComponentMark.getMark() + markIncInMain);

                // update total mark
                totalMark += markIncInMain * mainComponentMark.getMainComponent().getWeight() / 100d;

                resultList.add(totalMark);
            }
        }

        return resultList;
    }

}
