package com.softeng306.managers;

import com.softeng306.domain.course.ICourse;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.component.SubComponent;
import com.softeng306.domain.mark.*;
import com.softeng306.domain.student.IStudent;

import com.softeng306.fileprocessing.IFileProcessor;
import com.softeng306.fileprocessing.StudentCourseMarkFileProcessor;

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

    private final IFileProcessor<IStudentCourseMark> studentCourseMarkFileProcessor;

    /**
     * Override default constructor to implement singleteon pattern
     */
    private StudentCourseMarkMgr() {
        studentCourseMarkFileProcessor = new StudentCourseMarkFileProcessor();
        studentCourseMarks = studentCourseMarkFileProcessor.loadFile();
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

        // loop through all main components and all sub components to set mark to 0
        for (MainComponent mainComponent : mainComponents) {
            IMainComponentMark mainComponentMark = new MainComponentMark(mainComponent, 0d);

            for (SubComponent subComponent : mainComponent.getSubComponents()) {
                mainComponentMark.addSubComponentMark(new SubComponentMark(subComponent, 0d));
            }
            courseWorkMarks.add(mainComponentMark);
        }
        IStudentCourseMark studentCourseMark = new StudentCourseMark(student, course, courseWorkMarks, totalMark);
        studentCourseMarkFileProcessor.writeNewEntryToFile(studentCourseMark);
        return studentCourseMark;
    }

    @Override
    public void setCourseworkMark(boolean isExam, String studentID, String courseID) {
        IStudentCourseMarkMgrIO io = new StudentCourseMarkMgrIO();
        List<String> componentNameList = new ArrayList<>();
        List<String> availableChoices = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        List<Boolean> isMainComponent = new ArrayList<>();

        // loop through all coursework marks to find one corresponding to same course and student
        for (IStudentCourseMark studentCourseMark : studentCourseMarks) {
            if (studentCourseMark.getCourse().getCourseId().equals(courseID) && studentCourseMark.getStudent().getStudentId().equals(studentID)) {
                if (!isExam) {
                    for (IMainComponentMark mainComponentMark : studentCourseMark.getCourseWorkMarks()) {
                        MainComponent mainComponent = mainComponentMark.getMainComponent();

                        if (!mainComponent.getName().equals("Exam")
                                && !mainComponentMark.hasSubComponentMarks()) {
                            // get main component details, directly changes params
                            extractMainComponentDetails(mainComponent, componentNameList,
                                    availableChoices, weights, isMainComponent);
                        }

                        // get sub component details, directly changes params
                        extractSubComponentDetails(mainComponent, componentNameList,
                                availableChoices, weights, isMainComponent);
                    }

                    io.printCourseComponentChoices(availableChoices, weights);

                    // get choice from user
                    int choice = io.readCourseComponentChoice(availableChoices.size());
                    if (choice == (availableChoices.size() + 1)) { // option to quit selected
                        return;
                    }

                    // get mark from user and set for student
                    double assessmentMark = io.readCourseComponentMark();
                    String componentName = componentNameList.get(choice - 1);
                    setComponentMark(studentCourseMark, isMainComponent.get(choice - 1), componentName, assessmentMark);

                } else {
                    // The user want to enter exam studentCourseMark.
                    setExamMark(studentCourseMark);
                }

                studentCourseMarkFileProcessor.updateFileContents(studentCourseMarks);
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

    /**
     * Gets the GPA message depending on the students GPA
     *
     * @param studentGPA The GPA for said student
     * @return The GPA message depending on GPA from said student
     */
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

    /**
     * Sets the component mark for said for specified studentCourseMark
     *
     * @param studentCourseMark The student course mark to set mark for
     * @param isMainComponent Whether the student course mark is for a main component
     * @param componentName The name of the component to set
     * @param assessmentMark The mark to set for the component
     */
    private void setComponentMark(IStudentCourseMark studentCourseMark, boolean isMainComponent, String componentName,
                                  double assessmentMark) {
        IStudentCourseMarkMgrIO io = new StudentCourseMarkMgrIO();
        if (isMainComponent) {
            try {
                // This is a stand alone main assessment
                // Set mark and print results
                List<Double> resultList = studentCourseMark.setMainComponentMark(componentName, assessmentMark);
                io.printMainComponentMarkSetMessage(resultList);
            } catch (IllegalArgumentException e) {
                io.printMainComponentDoesNotExistMessage(e.getMessage());
            }

        } else {
            // Set mark and print result message
            List<Double> resultList = studentCourseMark.setSubComponentMark(componentName, assessmentMark);
            io.printSubComponentMarkSetMessage(resultList);
        }
    }

    /**
     * Sets the exam mark
     *
     * @param studentCourseMark The coursework mark to set the result for
     */
    private void setExamMark(IStudentCourseMark studentCourseMark) {
        IStudentCourseMarkMgrIO io = new StudentCourseMarkMgrIO();
        double examMark = new StudentCourseMarkMgrIO().readExamMark();

        try {
            // Sets and prints results
            List<Double> resultList = studentCourseMark.setMainComponentMark("Exam", examMark);
            io.printMainComponentMarkSetMessage(resultList);
        }
        catch(IllegalArgumentException e) {
            io.printMainComponentDoesNotExistMessage(e.getMessage());
        }
    }

    /**
     * Extracts the main component details to the values passed in
     *
     * @param mainComponent The main component to extract details from
     * @param componentNameList The list of the component names to add to
     * @param availableChoices The list of choices to add to
     * @param weights The list of integer weights to add to
     * @param isMainComponent The list of booleans dictating whether the componenet is a main component to add to
     */
    private void extractMainComponentDetails(MainComponent mainComponent, List<String> componentNameList,
                                             List<String> availableChoices, List<Integer> weights,
                                             List<Boolean> isMainComponent) {
        String mainComponentName = mainComponent.getName();
        availableChoices.add(mainComponentName);
        componentNameList.add(mainComponentName);
        weights.add(mainComponent.getWeight());
        isMainComponent.add(true);
    }

    /**
     * Extracts the sub component details to the values passed in
     *
     * @param mainComponent The sub components to extract the sub component details from
     * @param componentNameList The list of the component names to add to
     * @param availableChoices The list of choices to add to
     * @param weights The list of integer weights to add to
     * @param isMainComponent The list of booleans dictating whether the componenet is a main component to add to
     */
    private void extractSubComponentDetails(MainComponent mainComponent, List<String> componentNameList,
                                            List<String> availableChoices, List<Integer> weights,
                                            List<Boolean> isMainComponent) {

        // loop through all sub components to extract information
        for (SubComponent subComponent : mainComponent.getSubComponents()) {
            componentNameList.add(subComponent.getName());
            availableChoices.add(mainComponent.getName() + "-" + subComponent.getName());
            weights.add(mainComponent.getWeight() * subComponent.getWeight() / 100);
            isMainComponent.add(false);
        }
    }

    /**
     * Gets the list of coursework marks for specified student
     *
     * @param studentId The student ID to get the list of coursework marks for
     * @return the list of student course marks for specified student
     */
    private List<IStudentCourseMark> getCourseMarksForStudent(String studentId) {
        List<IStudentCourseMark> studentMarks = new ArrayList<>();
        for (IStudentCourseMark studentCourseMark : StudentCourseMarkMgr.getInstance().getStudentCourseMarks()) {
            // If student course mark is for student, then add to the list
            if (studentCourseMark.getStudent().getStudentId().equals(studentId)) {
                studentMarks.add(studentCourseMark);
            }
        }
        return studentMarks;
    }

}
