package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.*;
import com.softeng306.domain.student.IStudent;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.MarkFileProcessor;

import com.softeng306.io.IStudentCourseMarkMgrIO;
import com.softeng306.io.StudentCourseMarkMgrIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the mark related operations.
 */
public class StudentCourseMarkMgr implements IStudentCourseMarkMgr {
    /**
     * A list of all the student mark records in this school.
     */
    private List<IStudentCourseMark> studentCourseMarks;

    private static IStudentCourseMarkMgr singleInstance = null;

    private final IFileProcessor<IStudentCourseMark> markFileProcessor;

    /**
     * Override default constructor to implement singleteon pattern
     */
    private StudentCourseMarkMgr() {
        markFileProcessor = new MarkFileProcessor();
        studentCourseMarks = markFileProcessor.loadFile();
    }

    /**
     * Return the IStudentCourseMarkMgr singleton, if not initialised already, create an instance.
     *
     * @return IStudentCourseMarkMgr the singleton instance
     */
    public static IStudentCourseMarkMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new StudentCourseMarkMgr();
        }

        return singleInstance;
    }

    @Override
    public IStudentCourseMark initialiseStudentCourseMark(IStudent student, ICourse course) {
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
        IStudentCourseMark studentCourseMark = new StudentCourseMark(student, course, courseWorkMarks, totalMark);
        markFileProcessor.writeNewEntryToFile(studentCourseMark);
        return studentCourseMark;
    }

    @Override
    public void setCourseworkMark(boolean isExam, String studentID, String courseID) {
        IStudentCourseMarkMgrIO io = new StudentCourseMarkMgrIO();
        List<String> componentNameList = new ArrayList<>();
        List<String> availableChoices = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        List<Boolean> isMainComponent = new ArrayList<>();


        for (IStudentCourseMark studentCourseMark : studentCourseMarks) {
            if (studentCourseMark.getCourse().getCourseId().equals(courseID) && studentCourseMark.getStudent().getStudentId().equals(studentID)) {
                if (!isExam) {
                    for (IMainComponentMark mainComponentMark : studentCourseMark.getCourseWorkMarks()) {
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
                    setComponentMark(studentCourseMark, isMainComponent.get(choice - 1), componentName, assessmentMark);

                } else {
                    // The user want to enter exam studentCourseMark.
                    setExamMark(studentCourseMark);
                }

                markFileProcessor.updateFileContents(studentCourseMarks);
                return;
            }
        }

        io.printStudentNotRegisteredToCourse(courseID);
    }

    @Override
    public List<IStudentCourseMark> getStudentCourseMarks() {
        return studentCourseMarks;
    }

    @Override
    public int getAcademicUnitsForStudent(String studentId) {
        int totalAU = 0;
        for (IStudentCourseMark studentCourseMark : getCourseMarksForStudent(studentId)) {
            totalAU += studentCourseMark.getCourse().getAcademicUnits();
        }
        return totalAU;
    }

    @Override
    public List<String> getMarkMessageForStudent(String studentId, int totalAU) {
        List<String> markString = new ArrayList<>();
        List<IStudentCourseMark> marksForStudent = getCourseMarksForStudent(studentId);
        double studentGPA = 0d;

        for (IStudentCourseMark studentCourseMark : marksForStudent) {
            markString.add("Course ID: " + studentCourseMark.getCourse().getCourseId() + "\tCourse Name: " + studentCourseMark.getCourse().getName());

            for (IMainComponentMark mainComponentMark : studentCourseMark.getCourseWorkMarks()) {
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

            markString.add("Course Total: " + studentCourseMark.getTotalMark() + "\n");
            studentGPA += new MarkCalculator().convertMarkToGradePoints(studentCourseMark) * studentCourseMark.getCourse().getAcademicUnits();
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

    private void setComponentMark(IStudentCourseMark studentCourseMark, boolean isMainComponent, String componentName, double assessmentMark) {
        if (isMainComponent) {
            // This is a stand alone main assessment
            studentCourseMark.setMainComponentMark(componentName, assessmentMark);
        } else {
            studentCourseMark.setSubComponentMark(componentName, assessmentMark);
        }
    }

    private void setExamMark(IStudentCourseMark studentCourseMark) {
        double examMark = new StudentCourseMarkMgrIO().readExamMark();
        studentCourseMark.setMainComponentMark("Exam", examMark);
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

    private List<IStudentCourseMark> getCourseMarksForStudent(String studentId) {
        List<IStudentCourseMark> studentMarks = new ArrayList<>();
        for (IStudentCourseMark studentCourseMark : StudentCourseMarkMgr.getInstance().getStudentCourseMarks()) {
            if (studentCourseMark.getStudent().getStudentId().equals(studentId)) {
                studentMarks.add(studentCourseMark);
            }
        }
        return studentMarks;
    }

}
