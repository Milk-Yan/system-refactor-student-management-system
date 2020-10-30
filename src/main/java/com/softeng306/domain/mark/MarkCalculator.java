package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.managers.MarkMgr;

import java.util.ArrayList;
import java.util.List;

public class MarkCalculator {

    public double computeAverageMarkForCourseComponent(String courseID, String componentName){
        List<Mark> marksForCourse = new ArrayList<>();
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                marksForCourse.add(mark);
            }
        }

        return computeAverageComponentMark(marksForCourse, componentName);
    }

    public double computeOverallMarkForCourse(String courseID){
        List<Mark> marksForCourse = new ArrayList<>();
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseID().equals(courseID)) {
                marksForCourse.add(mark);
            }
        }

        return computeOverallMark(marksForCourse);
    }


    /**
     * Computes the sum of marks for a particular component of a particular course
     *
     * @param thisCourseMark    the list of mark records belong to a particular course
     * @param thisComponentName the component name interested.
     * @return the sum of component marks
     */
    private double computeAverageComponentMark(List<Mark> thisCourseMark, String thisComponentName) {
        double averageMark = 0;
        for (Mark mark : thisCourseMark) {
            List<MainComponentMark> thisComponentMarks = mark.getCourseWorkMarks();

            for (MainComponentMark mainComponentMark : thisComponentMarks) {
                MainComponent mainComponent = mainComponentMark.getMainComponent();
                if (mainComponent.getComponentName().equals((thisComponentName))) {
                    averageMark += mainComponentMark.getMark();
                    break;
                }

                for (SubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    if (subComponent.getComponentName().equals((thisComponentName))) {
                        averageMark += subComponentMark.getMark();
                        break;
                    }
                }
            }
        }
        return averageMark / thisCourseMark.size();
    }

    /**
     * Computes the overall marks for a particular course.
     *
     * @param thisCourseMark The marks for the course.
     * @return The exam marks for the course.
     */
    public double computeOverallMark(List<Mark> thisCourseMark) {
        double averageMark = 0;
        for (Mark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        return averageMark / thisCourseMark.size();
    }

    /**
     * Computes the gpa gained for this course from the result of this course.
     *
     * @return the grade (in A, B ... )
     */
    public double gpaCalculator(Mark mark) {
        double gradePercentage = mark.getTotalMark();

        if (gradePercentage > 85) {
            // A+, A
            return 5d;
        } else if (gradePercentage > 80) {
            // A-
            return 4.5;
        } else if (gradePercentage > 75) {
            // B+
            return 4d;
        } else if (gradePercentage > 70) {
            // B
            return 3.5;
        } else if (gradePercentage > 65) {
            // B-
            return 3d;
        } else if (gradePercentage > 60) {
            // C+
            return 2.5d;
        } else if (gradePercentage > 55) {
            // C
            return 2d;
        } else if (gradePercentage > 50) {
            // D+
            return 1.5d;
        } else if (gradePercentage > 45) {
            // D
            return 1d;
        } else {
            // F
            return 0d;
        }
    }
}
