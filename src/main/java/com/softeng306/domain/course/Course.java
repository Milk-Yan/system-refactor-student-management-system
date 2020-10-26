package com.softeng306.domain.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.Group;
import com.softeng306.domain.professor.Professor;

import java.util.ArrayList;
import java.util.List;


public class Course {
    /**
     * The ID of this course.
     */
    private String courseID;

    /**
     * The name of this course.
     */
    private String courseName;

    /** The AU of this course.
     *
     */
    @JsonProperty("AU")
    private int AU;

    /**
     * The professor in charge of this course.
     */
    private Professor profInCharge;

    /**
     * The department this course belongs to.
     */
    private Department courseDepartment;

    /**
     * The type of this course.
     */
    private CourseType courseType;

    /**
     * The current vacancy of this course.
     */
    private int vacancies;

    /**
     * The total seats of this course.
     */
    private int totalSeats;

    /**
     * The weekly lecture hour of this course.
     */
    private int lecWeeklyHour;

    /**
     * The lecture groups of this course.
     */
    private List<Group> lectureGroups;


    /**
     * The weekly tutorial hour of this course.
     */
    private int tutWeeklyHour = 0;


    /**
     * The tutorial groups of this course.
     */
    private List<Group> tutorialGroups = new ArrayList<>(0);

    /**
     * The weekly lab hour of this course.
     */
    private int labWeeklyHour = 0;

    /**
     * The lab groups of this course.
     */
    private List<Group> labGroups = new ArrayList<>(0);

    /**
     * The assessment components of this course.
     */
    private List<MainComponent> mainComponents = new ArrayList<>(0);

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Course() {}

    /**
     * Gets the course's ID.
     * @return the ID of this course.
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * Gets the course's name.
     * @return the name of this course.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets the course's AU.
     * @return the AU of this course.
     */
    public int getAU() { return AU; }

    /**
     * Gets the course's professor in charge.
     * @return the professor in charge of this course.
     */
    public Professor getProfInCharge() {
        return profInCharge;
    }

    /**
     * Gets the course's current vacancy.
     * @return the current vacancy of this course.
     */
    public int getVacancies() { return vacancies; }

    /**
     * Gets the course's total seats.
     * @return the total seats of this course.
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * Gets the course's department.
     * @return the department of this course.
     */
    public Department getCourseDepartment(){
        return courseDepartment;
    }

    /**
     * Gets the course's type.
     * @return the type of this course.
     */
    public CourseType getCourseType(){
        return courseType;
    }

    /**
     * Gets the course's weekly lecture hour.
     * @return the weekly lecture hour of this course.
     */
    public int getLecWeeklyHour(){
        return lecWeeklyHour;
    }

    /**
     * Gets the course's weekly tutorial hour.
     * @return the weekly tutorial hour of this course.
     */
    public int getTutWeeklyHour(){
        return tutWeeklyHour;
    }

    /**
     * Gets the course's weekly lab hour.
     * @return the weekly lab hour of this course.
     */
    public int getLabWeeklyHour(){
        return labWeeklyHour;
    }

    /**
     * Gets the course's lecture groups.
     * @return the lecture groups of this course.
     */
    public List<Group> getLectureGroups() {
        return lectureGroups;
    }

    /**
     * Gets the course's tutorial groups
     * @return the tutorial groups of this course
     */
    public List<Group> getTutorialGroups() {
        return this.tutorialGroups;
    }

    /**
     * Gets the course's lab groups.
     * @return the lab groups of this course.
     */
    public List<Group> getLabGroups() {
        return this.labGroups;
    }

    /**
     * Gets the course's main assessment components.
     * @return the main assessment components of this course.
     */
    public List<MainComponent> getMainComponents() {
        return this.mainComponents;
    }

    /**
     * Sets the course's current current vacancy.
     * @param vacancies this course's vacancy.
     */
    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    public void enrolledIn() { this.vacancies = vacancies - 1; }

    /**
     * Sets the tutorial groups of the lecture groups.
     * @param tutorialGroups this course's tutorial groups.
     */
    public void setTutorialGroups(List<Group> tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    /**
     * Sets the lab groups of the lecture groups.
     * @param labGroups this course's lab groups.
     */
    public void setLabGroups(List<Group> labGroups) {
        this.labGroups = labGroups;
    }

    /**
     * Sets the main assessment of the lecture groups.
     * @param mainComponents this course's main assessment.
     */
    public void setMainComponents(List<MainComponent> mainComponents) {
        this.mainComponents = mainComponents;
    }

    /**
     * Sets the weekly hour of the tutorials.
     * @param tutWeeklyHour this course's weekly tutorial hour.
     */
    public void setTutWeeklyHour(int tutWeeklyHour){
        this.tutWeeklyHour = tutWeeklyHour;
    }

    public void setLecWeeklyHour(int lecWeeklyHour) { this.lecWeeklyHour = lecWeeklyHour; }
    /**
     * Sets the weekly hour of the labs.
     * @param labWeeklyHour this course's weekly lab hour.
     */
    public void setLabWeeklyHour(int labWeeklyHour){
        this.labWeeklyHour = labWeeklyHour;
    }

    /**
     * Sets the academic unit for the course.
     * @param AU Academic unit for course.
     */
    public void setAU(int AU) { this.AU = AU; }

    /**
     * Sets the IDfor the course.
     * @param id ID for course.
     */
    public void setID(String id) { this.courseID = id; }

    /**
     * Sets the name for the course.
     * @param name Name for course.
     */
    public void setName(String name) { this.courseName = name; }

    /**
     * Sets the Professor in charge of the course.
     * @param professor Professor in charge of course.
     */
    public void setProfInCharge(Professor professor) { this.profInCharge = professor; }

    /**
     * Sets the total seats available for this course.
     * @param totalSeats Total seats available for this course.
     */
    public void setTotalSeat(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    /**
     * Sets the lecture groups for this course.
     * @param lectureGroups Lecture groups for this course.
     */
    public void setLectureGroups(List<Group> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }

    /**
     * Sets the department for this course.
     * @param department Department for this course.
     */
    public void setCourseDepartment(Department department) {
        this.courseDepartment = department;
    }

    public void setType(CourseType type) {
        this.courseType = type;
    }


}
