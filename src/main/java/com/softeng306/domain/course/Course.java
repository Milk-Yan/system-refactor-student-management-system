package com.softeng306.domain.course;

import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.professor.Professor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import java.util.ArrayList;
import java.util.List;


public class Course implements ICourse {

    private String courseID;
    private String courseName;
    private int academicUnits;

    private Professor profInCharge;
    private Department courseDepartment;
    private CourseType courseType;

    private int vacancies;
    private int totalSeats;

    private int lecWeeklyHour;
    private int tutWeeklyHour = 0;
    private int labWeeklyHour = 0;

    private List<IGroup> lectureGroups;
    private List<IGroup> tutorialGroups = new ArrayList<>();
    private List<IGroup> labGroups = new ArrayList<>();

    private List<MainComponent> mainComponents = new ArrayList<>();

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Course() {
    }

    @Override
    public String getCourseID() {
        return courseID;
    }

    @Override
    public String getCourseName() {
        return courseName;
    }

    @Override
    public int getAcademicUnits() {
        return academicUnits;
    }

    @Override
    public Professor getProfInCharge() {
        return profInCharge;
    }

    @Override
    public int getVacancies() {
        return vacancies;
    }

    @Override
    public int getTotalSeats() {
        return totalSeats;
    }

    @Override
    public Department getCourseDepartment() {
        return courseDepartment;
    }

    @Override
    public CourseType getCourseType() {
        return courseType;
    }

    @Override
    public int getLecWeeklyHour() {
        return lecWeeklyHour;
    }

    @Override
    public int getTutWeeklyHour() {
        return tutWeeklyHour;
    }

    @Override
    public int getLabWeeklyHour() {
        return labWeeklyHour;
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
    public void enrolledIn() {
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
    public void setTutWeeklyHour(int tutWeeklyHour) {
        this.tutWeeklyHour = tutWeeklyHour;
    }

    @Override
    public void setLecWeeklyHour(int lecWeeklyHour) {
        this.lecWeeklyHour = lecWeeklyHour;
    }

    @Override
    public void setLabWeeklyHour(int labWeeklyHour) {
        this.labWeeklyHour = labWeeklyHour;
    }

    @Override
    public void setAcademicUnits(int academicUnits) {
        this.academicUnits = academicUnits;
    }

    @Override
    public void setID(String id) {
        this.courseID = id;
    }

    @Override
    public void setName(String name) {
        this.courseName = name;
    }

    @Override
    public void setProfInCharge(Professor professor) {
        this.profInCharge = professor;
    }

    @Override
    public void setTotalSeat(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public void setLectureGroups(List<IGroup> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }

    @Override
    public void setCourseDepartment(Department department) {
        this.courseDepartment = department;
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

}
