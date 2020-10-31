package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.managers.StudentCourseMarkMgr;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for the MarkCalculator class.
 * Implements methods which are used to get the marks for Courses and their components.
 */
public class MarkCalculator implements IMarkCalculator {

    @Override
    public double computeAverageMarkForCourseComponent(String courseID, String componentName) {
        List<IStudentCourseMark> marksForCourse = new ArrayList<>();
        for (IStudentCourseMark studentCourseMark : StudentCourseMarkMgr.getInstance().getStudentCourseMarks()) {
            if (studentCourseMark.getCourse().getCourseId().equals(courseID)) {
                marksForCourse.add(studentCourseMark);
            }
        }

        return computeAverageComponentMark(marksForCourse, componentName);
    }

    @Override
    public double computeOverallMarkForCourse(String courseID) {
        List<IStudentCourseMark> marksForCourse = new ArrayList<>();
        for (IStudentCourseMark studentCourseMark : StudentCourseMarkMgr.getInstance().getStudentCourseMarks()) {
            if (studentCourseMark.getCourse().getCourseId().equals(courseID)) {
                marksForCourse.add(studentCourseMark);
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
    private double computeAverageComponentMark(List<IStudentCourseMark> thisCourseMark, String thisComponentName) {
        double averageMark = 0;
        for (IStudentCourseMark studentCourseMark : thisCourseMark) {
            List<IMainComponentMark> thisComponentMarks = studentCourseMark.getCourseWorkMarks();

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
    public double computeOverallMark(List<IStudentCourseMark> thisCourseMark) {
        double averageMark = 0;
        for (IStudentCourseMark mark : thisCourseMark) {
            averageMark += mark.getTotalMark();
        }
        return averageMark / thisCourseMark.size();
    }

    @Override
    public double convertMarkToGradePoints(IStudentCourseMark studentCourseMark) {
        double gradePercentage = studentCourseMark.getTotalMark();

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
