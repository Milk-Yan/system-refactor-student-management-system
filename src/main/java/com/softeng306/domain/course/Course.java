package com.softeng306.domain.course;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.professor.Professor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import java.util.ArrayList;
import java.util.List;


public class Course {

    private String courseId;
    private String name;
    private int academicUnits;

    private Professor courseCoordinator;
    private Department department;
    private CourseType courseType;

    private int vacancies;
    private int capacity;

    private int lectureHoursPerWeek;
    private int tutorialHoursPerWeek = 0;
    private int labHoursPerWeek = 0;

    private List<Group> lectureGroups;
    private List<Group> tutorialGroups = new ArrayList<>();
    private List<Group> labGroups = new ArrayList<>();

    private List<MainComponent> mainComponents = new ArrayList<>();

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Course() {
    }

    /**
     * Gets the course's ID.
     *
     * @return the ID of this course.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Gets the course's name.
     *
     * @return the name of this course.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of academic units for this course.
     */
    public int getAcademicUnits() {
        return academicUnits;
    }

    /**
     * Gets the course's professor in charge.
     *
     * @return the professor in charge of this course.
     */
    public Professor getCourseCoordinator() {
        return courseCoordinator;
    }

    /**
     * Gets the course's current vacancy.
     *
     * @return the current vacancy of this course.
     */
    public int getVacancies() {
        return vacancies;
    }

    /**
     * Gets the course's total seats.
     *
     * @return the total seats of this course.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the course's department.
     *
     * @return the department of this course.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Gets the course's type.
     *
     * @return the type of this course.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Gets the course's weekly lecture hour.
     *
     * @return the weekly lecture hour of this course.
     */
    public int getLectureHoursPerWeek() {
        return lectureHoursPerWeek;
    }

    /**
     * Gets the course's weekly tutorial hour.
     *
     * @return the weekly tutorial hour of this course.
     */
    public int getTutorialHoursPerWeek() {
        return tutorialHoursPerWeek;
    }

    /**
     * Gets the course's weekly lab hour.
     *
     * @return the weekly lab hour of this course.
     */
    public int getLabHoursPerWeek() {
        return labHoursPerWeek;
    }

    /**
     * Gets the course's lecture groups.
     *
     * @return the lecture groups of this course.
     */
    public List<Group> getLectureGroups() {
        return lectureGroups;
    }

    /**
     * Gets the course's tutorial groups
     *
     * @return the tutorial groups of this course
     */
    public List<Group> getTutorialGroups() {
        return this.tutorialGroups;
    }

    /**
     * Gets the course's lab groups.
     *
     * @return the lab groups of this course.
     */
    public List<Group> getLabGroups() {
        return this.labGroups;
    }

    /**
     * Gets the course's main assessment components.
     *
     * @return the main assessment components of this course.
     */
    public List<MainComponent> getMainComponents() {
        return this.mainComponents;
    }

    /**
     * Sets the course's current current vacancy.
     *
     * @param vacancies this course's vacancy.
     */
    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    public void updateVacanciesForEnrollment() {
        this.vacancies = vacancies - 1;
    }

    /**
     * Sets the tutorial groups of the lecture groups.
     *
     * @param tutorialGroups this course's tutorial groups.
     */
    public void setTutorialGroups(List<Group> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    /**
     * Sets the lab groups of the lecture groups.
     *
     * @param labGroups this course's lab groups.
     */
    public void setLabGroups(List<Group> labGroups) {
        this.labGroups = labGroups;
    }

    /**
     * Sets the main assessment of the lecture groups.
     *
     * @param mainComponents this course's main assessment.
     */
    public void setMainComponents(List<MainComponent> mainComponents) {
        this.mainComponents = mainComponents;
    }

    /**
     * Sets the weekly hour of the tutorials.
     *
     * @param tutorialHoursPerWeek this course's weekly tutorial hour.
     */
    public void setTutorialHoursPerWeek(int tutorialHoursPerWeek) {
        this.tutorialHoursPerWeek = tutorialHoursPerWeek;
    }

    public void setLectureHoursPerWeek(int lectureHoursPerWeek) {
        this.lectureHoursPerWeek = lectureHoursPerWeek;
    }

    /**
     * Sets the weekly hour of the labs.
     *
     * @param labHoursPerWeek this course's weekly lab hour.
     */
    public void setLabHoursPerWeek(int labHoursPerWeek) {
        this.labHoursPerWeek = labHoursPerWeek;
    }

    /**
     * Sets the academic unit for the course.
     *
     * @param academicUnits Academic unit for course.
     */
    public void setAcademicUnits(int academicUnits) {
        this.academicUnits = academicUnits;
    }

    /**
     * Sets the IDfor the course.
     *
     * @param id ID for course.
     */
    public void setCourseId(String id) {
        this.courseId = id;
    }

    /**
     * Sets the name for the course.
     *
     * @param name Name for course.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the Professor in charge of the course.
     *
     * @param professor Professor in charge of course.
     */
    public void setCourseCoordinator(Professor professor) {
        this.courseCoordinator = professor;
    }

    /**
     * Sets the total seats available for this course.
     *
     * @param courseCapacity Total seats available for this course.
     */
    public void setCapacity(int courseCapacity) {
        this.capacity = courseCapacity;
    }

    /**
     * Sets the lecture groups for this course.
     *
     * @param lectureGroups Lecture groups for this course.
     */
    public void setLectureGroups(List<Group> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }

    /**
     * Sets the department for this course.
     *
     * @param department Department for this course.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setType(CourseType type) {
        this.courseType = type;
    }

    public String[][] generateLabGroupInformation() {
        return generateGroupInformation(labGroups);
    }

    public String[][] generateTutorialGroupInformation() {
        return generateGroupInformation(tutorialGroups);
    }

    public String[][] generateLectureGroupInformation() {
        return generateGroupInformation(lectureGroups);
    }

    private String[][] generateGroupInformation(List<Group> groups) {
        String[][] groupInfo = new String[groups.size()][3];
        for (int i = 0; i < groups.size(); i++) {
            groupInfo[i][0] = groups.get(i).getGroupName();
            groupInfo[i][1] = String.valueOf(groups.get(i).getAvailableVacancies());
            groupInfo[i][2] = String.valueOf(groups.get(i).getCapacity());
        }
        return groupInfo;
    }
}
