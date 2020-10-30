package com.softeng306.domain.course;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.professor.IProfessor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of an academic course.
 * Stores information about a course.
 * Provides implementations of methods to get and set course information.
 * This is a subclass of {@code ICourse}
 */
public class Course implements ICourse {

    private String courseId;
    private String name;
    private int academicUnits;

    private IProfessor courseCoordinator;
    private Department department;
    private CourseType courseType;

    private int vacancies;
    private int capacity;

    private int lectureHoursPerWeek;
    private int tutorialHoursPerWeek = 0;
    private int labHoursPerWeek = 0;

    private List<IGroup> lectureGroups;
    private List<IGroup> tutorialGroups = new ArrayList<>();
    private List<IGroup> labGroups = new ArrayList<>();

    /**
     * The main assessment components for this course
     */
    private List<MainComponent> mainComponents = new ArrayList<>();

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Course() {
    }

    @Override
    public String getCourseId() {
        return courseId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAcademicUnits() {
        return academicUnits;
    }

    @Override
    public IProfessor getCourseCoordinator() {
        return courseCoordinator;
    }

    @Override
    public int getVacancies() {
        return vacancies;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public Department getDepartment() {
        return department;
    }

    @Override
    public CourseType getCourseType() {
        return courseType;
    }

    @Override
    public int getLectureHoursPerWeek() {
        return lectureHoursPerWeek;
    }

    @Override
    public int getTutorialHoursPerWeek() {
        return tutorialHoursPerWeek;
    }

    @Override
    public int getLabHoursPerWeek() {
        return labHoursPerWeek;
    }

    @Override
    public List<IGroup> getLectureGroups() {
        return lectureGroups;
    }

    @Override
    public List<IGroup> getTutorialGroups() {
        return this.tutorialGroups;
    }

    @Override
    public List<IGroup> getLabGroups() {
        return this.labGroups;
    }

    @Override
    public List<MainComponent> getMainComponents() {
        return this.mainComponents;
    }

    @Override
    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public void updateVacanciesForEnrollment() {
        this.vacancies = vacancies - 1;
    }

    @Override
    public void setTutorialGroups(List<IGroup> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    @Override
    public void setLabGroups(List<IGroup> labGroups) {
        this.labGroups = labGroups;
    }

    @Override
    public void setMainComponents(List<MainComponent> mainComponents) {
        this.mainComponents = mainComponents;
    }

    @Override
    public void setTutorialHoursPerWeek(int tutorialHoursPerWeek) {
        this.tutorialHoursPerWeek = tutorialHoursPerWeek;
    }

    @Override
    public void setLectureHoursPerWeek(int lectureHoursPerWeek) {
        this.lectureHoursPerWeek = lectureHoursPerWeek;
    }

    @Override
    public void setLabHoursPerWeek(int labHoursPerWeek) {
        this.labHoursPerWeek = labHoursPerWeek;
    }

    @Override
    public void setAcademicUnits(int academicUnits) {
        this.academicUnits = academicUnits;
    }

    @Override
    public void setCourseId(String id) {
        this.courseId = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCourseCoordinator(IProfessor professor) {
        this.courseCoordinator = professor;
    }

    @Override
    public void setCapacity(int courseCapacity) {
        this.capacity = courseCapacity;
    }

    @Override
    public void setLectureGroups(List<IGroup> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }

    @Override
    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public void setType(CourseType type) {
        this.courseType = type;
    }

    @Override
    public String[][] generateLabGroupInformation() {
        return generateGroupInformation(labGroups);
    }

    @Override
    public String[][] generateTutorialGroupInformation() {
        return generateGroupInformation(tutorialGroups);
    }

    @Override
    public String[][] generateLectureGroupInformation() {
        return generateGroupInformation(lectureGroups);
    }

    /**
     * Local method used to generate string list of information for provided groups.
     * Each item in the returned list contains the information for one group.
     *
     * @param groups The groups to generate a string for
     * @return List containing, for each group provided: [Name, Vacancies, Capacity]
     */
    private String[][] generateGroupInformation(List<IGroup> groups) {
        String[][] groupInfo = new String[groups.size()][3];
        for (int i = 0; i < groups.size(); i++) {
            groupInfo[i][0] = groups.get(i).getGroupName();
            groupInfo[i][1] = String.valueOf(groups.get(i).getAvailableVacancies());
            groupInfo[i][2] = String.valueOf(groups.get(i).getCapacity());
        }
        return groupInfo;
    }

}
