package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.*;
import com.softeng306.domain.student.IStudent;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.MarkFileProcessor;

import com.softeng306.io.IMarkMgrIO;
import com.softeng306.io.MarkMgrIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the mark related operations.
 */
public class MarkMgr implements IMarkMgr {
    /**
     * A list of all the student mark records in this school.
     */
    private List<IMark> marks;

    private static IMarkMgr singleInstance = null;

    private final IFileProcessor<IMark> markFileProcessor;

    /**
     * Override default constructor to implement singleteon pattern
     */
    private MarkMgr() {
        markFileProcessor = new MarkFileProcessor();
        marks = markFileProcessor.loadFile();
    }

    /**
     * Return the IMarkMgr singleton, if not initialised already, create an instance.
     *
     * @return IMarkMgr the singleton instance
     */
    public static IMarkMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new MarkMgr();
        }

        return singleInstance;
    }

    @Override
    public IMark initialiseMark(IStudent student, ICourse course) {
        List<IMainComponentMark> courseWorkMarks = new ArrayList<>();
        double totalMark = 0d;
        List<MainComponent> mainComponents = course.getMainComponents();

        for (MainComponent mainComponent : mainComponents) {
            IMainComponentMark mainComponentMark = new MainComponentMark(mainComponent, 0d);

            for (SubComponent subComponent : mainComponent.getSubComponents()) {
                mainComponentMark.addSubComponentMark(new SubComponentMark(subComponent, 0d));
            }
            courseWorkMarks.add(mainComponentMark);
        }
        IMark mark = new Mark(student, course, courseWorkMarks, totalMark);
        markFileProcessor.writeNewEntryToFile(mark);
        return mark;
    }

    @Override
    public void setCourseworkMark(boolean isExam, String studentID, String courseID) {
        IMarkMgrIO io = new MarkMgrIO();
        List<String> componentNameList = new ArrayList<>();
        List<String> availableChoices = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        List<Boolean> isMainComponent = new ArrayList<>();


        for (IMark mark : marks) {
            if (mark.getCourse().getCourseId().equals(courseID) && mark.getStudent().getStudentId().equals(studentID)) {
                //put the set mark function here
                if (!isExam) {
                    for (IMainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                        MainComponent mainComponent = mainComponentMark.getMainComponent();

                        if (!mainComponent.getName().equals("Exam")
                          && !mainComponentMark.hasSubComponentMarks()) {

                            extractMainComponentDetails(mainComponent, componentNameList,
                              availableChoices, weights, isMainComponent);
                        }

                        extractSubComponentDetails(mainComponent, componentNameList,
                          availableChoices, weights, isMainComponent);
                    }

                    io.printCourseComponentChoices(availableChoices, weights);

                    int choice = io.readCourseComponentChoice(availableChoices.size());
                    if (choice == (availableChoices.size() + 1)) {
                        return;
                    }

                    double assessmentMark = io.readCourseComponentMark();
                    String componentName = componentNameList.get(choice - 1);
                    setComponentMark(mark, isMainComponent.get(choice - 1), componentName, assessmentMark);

                } else {
                    // The user want to enter exam mark.
                    setExamMark(mark);
                }

                markFileProcessor.updateFileContents(marks);
                return;
            }
        }

        io.printStudentNotRegisteredToCourse(courseID);
    }

    @Override
    public List<IMark> getMarks() {
        return marks;
    }

    @Override
    public int getAcademicUnitsForStudent(String studentId) {
        int totalAU = 0;
        for (IMark mark : getMarksForStudent(studentId)) {
            totalAU += mark.getCourse().getAcademicUnits();
        }
        return totalAU;
    }

    @Override
    public List<String> getMarkMessageForStudent(String studentId, int totalAU) {
        List<String> markString = new ArrayList<>();
        List<IMark> marksForStudent = getMarksForStudent(studentId);
        double studentGPA = 0d;

        for (IMark mark : marksForStudent) {
            markString.add("Course ID: " + mark.getCourse().getCourseId() + "\tCourse Name: " + mark.getCourse().getName());

            for (IMainComponentMark mainComponentMark : mark.getCourseWorkMarks()) {
                MainComponent mainComponent = mainComponentMark.getMainComponent();
                double result = mainComponentMark.getMark();

                markString.add("Main Assessment: " + mainComponent.getName() + " ----- (" + mainComponent.getWeight() + "%)");
                int mainAssessmentWeight = mainComponent.getWeight();

                for (ISubComponentMark subComponentMark : mainComponentMark.getSubComponentMarks()) {
                    SubComponent subComponent = subComponentMark.getSubComponent();
                    markString.add("Sub Assessment: " + subComponent.getName() + " -- (" + subComponent.getWeight() + "% * " + mainAssessmentWeight + "%) --- Mark: " + subComponentMark.getMark());
                }

                markString.add("Main Assessment Total: " + result + "\n");
            }

            markString.add("Course Total: " + mark.getTotalMark() + "\n");
            studentGPA += new MarkCalculator().convertMarkToGradePoints(mark) * mark.getCourse().getAcademicUnits();
        }
        studentGPA /= totalAU;
        markString.add("GPA for this semester: " + studentGPA);
        markString.add(getGPAMessage(studentGPA));
        return markString;
    }

    private String getGPAMessage(double studentGPA) {
        if (studentGPA >= 4.50) {
            return "On track of First Class Honor!";
        } else if (studentGPA >= 4.0) {
            return "On track of Second Upper Class Honor!";
        } else if (studentGPA >= 3.5) {
            return "On track of Second Lower Class Honor!";
        } else if (studentGPA >= 3) {
            return "On track of Third Class Honor!";
        } else {
            return "Advice: Study hard";
        }
    }

    private void setComponentMark(IMark mark, boolean isMainComponent, String componentName, double assessmentMark) {
        if (isMainComponent) {
            // This is a stand alone main assessment
            mark.setMainComponentMark(componentName, assessmentMark);
        } else {
            mark.setSubComponentMark(componentName, assessmentMark);
        }
    }

    private void setExamMark(IMark mark) {
        double examMark = new MarkMgrIO().readExamMark();
        mark.setMainComponentMark("Exam", examMark);
    }

    private void extractMainComponentDetails(MainComponent mainComponent, List<String> componentNameList,
                                             List<String> availableChoices, List<Integer> weights,
                                             List<Boolean> isMainComponent) {
        String mainComponentName = mainComponent.getName();
        availableChoices.add(mainComponentName);
        componentNameList.add(mainComponentName);
        weights.add(mainComponent.getWeight());
        isMainComponent.add(true);
    }

    private void extractSubComponentDetails(MainComponent mainComponent, List<String> componentNameList,
                                            List<String> availableChoices, List<Integer> weights,
                                            List<Boolean> isMainComponent) {

        for (SubComponent subComponent : mainComponent.getSubComponents()) {
            componentNameList.add(subComponent.getName());
            availableChoices.add(mainComponent.getName() + "-" + subComponent.getName());
            weights.add(mainComponent.getWeight() * subComponent.getWeight() / 100);
            isMainComponent.add(false);
        }
    }

    private List<IMark> getMarksForStudent(String studentId) {
        List<IMark> studentMarks = new ArrayList<>();
        for (IMark mark : MarkMgr.getInstance().getMarks()) {
            if (mark.getStudent().getStudentId().equals(studentId)) {
                studentMarks.add(mark);
            }
        }
        return studentMarks;
    }

}
