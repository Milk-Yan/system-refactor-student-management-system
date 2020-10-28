package com.softeng306.managers;


import com.softeng306.domain.course.Course;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.MainComponentMark;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.mark.MarkCalculator;
import com.softeng306.domain.mark.SubComponentMark;
import com.softeng306.domain.student.Student;
import com.softeng306.io.FILEMgr;
import com.softeng306.io.MarkMgrIO;
import com.softeng306.io.StudentMgrIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the mark related operations.
 */

public class MarkMgr {
    /**
     * A list of all the student mark records in this school.
     */
    private List<Mark> marks;

    private static MarkMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private MarkMgr(List<Mark> marks) {
        this.marks = marks;
    }


    /**
     * Return the MarkMgr singleton, if not initialised already, create an instance.
     *
     * @return MarkMgr the singleton instance
     */
    public static MarkMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new MarkMgr(FILEMgr.loadStudentMarks());
        }

        return singleInstance;
    }


    /**
     * Initializes marks for a student when he/she just registered a course.
     *
     * @param student the student this mark record belongs to.
     * @param course  the course this mark record about.
     * @return the new added mark.
     */
    public Mark initializeMark(Student student, Course course) {
        List<MainComponentMark> courseWorkMarks = new ArrayList<>();
        double totalMark = 0d;
        List<MainComponent> mainComponents = course.getMainComponents();

        for (MainComponent mainComponent : mainComponents) {
            MainComponentMark mainComponentMark = new MainComponentMark(mainComponent, 0d);

            for (SubComponent subComponent : mainComponent.getSubComponents()) {
                mainComponentMark.addSubComponentMark(new SubComponentMark(subComponent, 0d));
            }
            courseWorkMarks.add(mainComponentMark);
        }
        Mark mark = new Mark(student, course, courseWorkMarks, totalMark);
        FILEMgr.updateStudentMarks(mark);
        return mark;
    }

    /**
     * Sets the coursework mark for the mark record.
     *
     * @param isExam whether this coursework component refers to "Exam"
     */
    public void setCourseWorkMark(boolean isExam) {
        MarkMgrIO.printFunctionCall("enterCourseWorkMark");

        String studentID = new StudentMgrIO().readStudentIdFromUser();
        String courseID = CourseMgr.getInstance().readCourseFromUser().getCourseID();

        for (Mark mark : marks) {
            if (mark.getCourse().getCourseID().equals(courseID) && mark.getStudent().getStudentID().equals(studentID)) {
                //put the set mark function here
                if (!isExam) {
                    ArrayList<String> availableChoices = new ArrayList<>();
                    ArrayList<Double> weights = new ArrayList<>();
                    ArrayList<Boolean> isMainAss = new ArrayList<>();

                    for (MainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                        MainComponent mainComponent = mainComponentMark.getMainComponent();
                        if (!mainComponent.getComponentName().equals("Exam")
                                && !mainComponentMark.hasSubComponents()) {
                            availableChoices.add(mainComponent.getComponentName());
                            weights.add((double) mainComponent.getComponentWeight());
                            isMainAss.add(true);
                        }

                        for (SubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                            SubComponent subComponent = subComponentMark.getSubComponent();
                            availableChoices.add(mainComponent.getComponentName() + "-" + subComponent.getComponentName());
                            weights.add((double) mainComponent.getComponentWeight() * (double) subComponent.getComponentWeight() / 100d);
                            isMainAss.add(false);
                        }
                    }

                    MarkMgrIO.printCourseComponentChoices(availableChoices, weights);

                    int choice = MarkMgrIO.readCourseComponentChoice(availableChoices.size());
                    if (choice == (availableChoices.size() + 1)) {
                        return;
                    }

                    double assessmentMark = MarkMgrIO.readCourseComponentMark();
                    if (isMainAss.get(choice - 1)) {
                        // This is a stand alone main assessment
                        mark.setMainCourseWorkMarks(availableChoices.get(choice - 1), assessmentMark);
                    } else {
                        mark.setSubCourseWorkMarks(availableChoices.get(choice - 1).split("-")[1], assessmentMark);
                    }

                } else {
                    // The user want to enter exam mark.
                    double examMark = MarkMgrIO.readExamMark();
                    mark.setMainCourseWorkMarks("Exam", examMark);
                }

                return;
            }
        }

        MarkMgrIO.printStudentNotRegisteredToCourse(courseID);
    }

    /**
     * Return the list of all marks in the system.
     *
     * @return An list of all marks.
     */
    public List<Mark> getMarks() {
        return marks;
    }


    public double getSubComponentMark(Student student, String mainComponentName, String subComponentName) {
        for (Mark mark : marks) {
            if (mark.getStudent().equals(student))
                for (MainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                    if (mainComponentMark.getMainComponent().getComponentName().equals(mainComponentName)) {
                        return mainComponentMark.getSubComponentMark(subComponentName).getMark();
                    }
                }
        }
        return 0;
    }

    public double getMainComponentMark(Student student, String mainComponentName) {
        for (Mark mark : marks) {
            if (mark.getStudent().equals(student))
                for (MainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                    if (mainComponentMark.getMainComponent().getComponentName().equals(mainComponentName)) {
                        return mainComponentMark.getMark();
                    }
                }
        }
        return 0;
    }

    public List<Mark> getMarksForStudent(String studentId) {
        List<Mark> studentMarks = new ArrayList<>();
        for (Mark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getStudent().getStudentID().equals(studentId)) {
                studentMarks.add(mark);
            }
        }
        return studentMarks;
    }

    public int getAUForStudent(String studentId) {
        int totalAU = 0;
        for (Mark mark : getMarksForStudent(studentId)) {
            totalAU += mark.getCourse().getAU();
        }
        return totalAU;
    }

    public List<String> getMarkStringForStudent(String studentId, int totalAU) {
        List<String> markString = new ArrayList<>();
        List<Mark> marksForStudent = getMarksForStudent(studentId);
        double studentGPA = 0d;

        for (Mark mark : marksForStudent) {
            markString.add("Course ID: " + mark.getCourse().getCourseID() + "\tCourse Name: " + mark.getCourse().getCourseName());

            for (MainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                MainComponent mainComponent = mainComponentMark.getMainComponent();
                Double result = mainComponentMark.getMark();

                markString.add("Main Assessment: " + mainComponent.getComponentName() + " ----- (" + mainComponent.getComponentWeight() + "%)");
                int mainAssessmentWeight = mainComponent.getComponentWeight();

                for (SubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    markString.add("Sub Assessment: " + subComponent.getComponentName() + " -- (" + subComponent.getComponentWeight() + "% * " + mainAssessmentWeight + "%) --- Mark: " + subComponentMark.getMark());
                }

                markString.add("Main Assessment Total: " + result);
                markString.add("");
            }

            System.out.println("Course Total: " + mark.getTotalMark());
            studentGPA += new MarkCalculator().gpaCalculator(mark) * mark.getCourse().getAU();
            System.out.println();

        }
        studentGPA /= totalAU;
        markString.add("GPA for this semester: " + studentGPA);
        if (studentGPA >= 4.50) {
            markString.add("On track of First Class Honor!");
        } else if (studentGPA >= 4.0) {
            markString.add("On track of Second Upper Class Honor!");
        } else if (studentGPA >= 3.5) {
            markString.add("On track of Second Lower Class Honor!");
        } else if (studentGPA >= 3) {
            markString.add("On track of Third Class Honor!");
        } else {
            markString.add("Advice: Study hard");
        }
        return markString;
    }


}
