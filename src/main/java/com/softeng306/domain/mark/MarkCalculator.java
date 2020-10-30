package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.managers.MarkMgr;

import java.util.ArrayList;
import java.util.List;

public class MarkCalculator implements IMarkCalculator {

    @Override
    public double computeAverageMarkForCourseComponent(String courseID, String componentName){
        List<IMark> marksForCourse = new ArrayList<>();
        for (IMark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseId().equals(courseID)) {
                marksForCourse.add(mark);
            }
        }

        return computeAverageComponentMark(marksForCourse, componentName);
    }

    @Override
    public double computeOverallMarkForCourse(String courseID){
        List<IMark> marksForCourse = new ArrayList<>();
        for (IMark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getCourse().getCourseId().equals(courseID)) {
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
    private double computeAverageComponentMark(List<IMark> thisCourseMark, String thisComponentName) {
        double averageMark = 0;
        for (IMark mark : thisCourseMark) {
            List<IMainComponentMark> thisComponentMarks = mark.getCourseWorkMarks();

            for (IMainComponentMark mainComponentMark : thisComponentMarks) {
                MainComponent mainComponent = mainComponentMark.getMainComponent();
                if (mainComponent.getName().equals((thisComponentName))) {
                    averageMark += mainComponentMark.getMark();
                    break;
                }

                for (ISubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    if (subComponent.getName().equals((thisComponentName))) {
                        averageMark += subComponentMark.getMark();
                        break;
                    }
                }
            }
        }
        return averageMark / thisCourseMark.size();
    }

    @Override
    public double computeOverallMark(List<IMark> thisCourseMark) {
        double averageMark = 0;
        for (IMark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        return averageMark / thisCourseMark.size();
    }

    @Override
    public double convertMarkToGradePoints(IMark mark) {
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
