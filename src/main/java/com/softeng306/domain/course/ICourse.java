package com.softeng306.domain.course;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.professor.IProfessor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import java.util.List;

/**
 * Interface for a Course.
 * Used to represent a Course offered by the relevant academic institute.
 * Provides methods to get and set course information.
 */
@JsonDeserialize(as = Course.class)
public interface ICourse {
    /**
     * @return The ID of this course.
     */
    String getCourseId();

    /**
     * @return The name of this course.
     */
    String getName();

    /**
     * @return The number of academic units for this course.
     */
    int getAcademicUnits();

    /**
     * @return The professor in charge of this course.
     */
    IProfessor getCourseCoordinator();

    /**
     * @return The number of seats left for this course.
     */
    int getVacancies();

    /**
     * @return The total seats of this course.
     */
    int getCapacity();

    /**
     * @return The department of this course.
     */
    Department getDepartment();

    /**
     * @return The type of this course.
     */
    CourseType getCourseType();

    /**
     * @return The weekly lecture hour of this course.
     */
    int getLectureHoursPerWeek();

    /**
     * @return The weekly tutorial hour of this course.
     */
    int getTutorialHoursPerWeek();

    /**
     * @return The weekly lab hour of this course.
     */
    int getLabHoursPerWeek();

    /**
     * @return The lecture groups of this course.
     */
    List<IGroup> getLectureGroups();

    /**
     * @return The tutorial groups of this course.
     */
    List<IGroup> getTutorialGroups();

    /**
     * @return The lab groups of this course.
     */
    List<IGroup> getLabGroups();

    /**
     * @return The main assessment components of this course.
     */
    List<MainComponent> getMainComponents();

    /**
     * Sets the number of vacancies left for this course.
     *
     * @param vacancies
     */
    void setVacancies(int vacancies);

    /**
     * Updates the available vacancies of this course after someone has registered.
     */
    void updateVacanciesForEnrollment();

    /**
     * Sets the lecture groups available for this course.
     *
     * @param lectureGroups
     */
    void setLectureGroups(List<IGroup> lectureGroups);

    /**
     * Sets the tutorial groups available for this course.
     *
     * @param tutorialGroups
     */
    void setTutorialGroups(List<IGroup> tutorialGroups);

    /**
     * Sets the lab groups available for this course.
     *
     * @param labGroups
     */
    void setLabGroups(List<IGroup> labGroups);

    /**
     * Sets the main assessment of the lecture groups.
     *
     * @param mainComponents this course's main assessment.
     */
    void setMainComponents(List<MainComponent> mainComponents);

    /**
     * Sets the weekly tutorial hour of this course.
     *
     * @param tutWeeklyHour
     */
    void setTutorialHoursPerWeek(int tutWeeklyHour);

    /**
     * Sets the weekly lecture hour of this course.
     *
     * @param lecWeeklyHour
     */
    void setLectureHoursPerWeek(int lecWeeklyHour);

    /**
     * Sets the weekly lab hour of this course.
     *
     * @param labWeeklyHour
     */
    void setLabHoursPerWeek(int labWeeklyHour);

    /**
     * Sets the academic unit for the course.
     *
     * @param academicUnits
     */
    void setAcademicUnits(int academicUnits);

    /**
     * Sets the ID for the course.
     *
     * @param id
     */
    void setCourseId(String id);

    /**
     * Sets the name for the course.
     *
     * @param name
     */
    void setName(String name);

    /**
     * Sets the Professor in charge of the course.
     *
     * @param professor
     */
    void setCourseCoordinator(IProfessor professor);

    /**
     * Sets the total seats available for this course.
     *
     * @param courseCapacity
     */
    void setCapacity(int courseCapacity);

    /**
     * Sets the department for this course.
     *
     * @param department
     */
    void setDepartment(Department department);

    /**
     * Sets the type of this course.
     *
     * @param type
     */
    void setType(CourseType type);

    /**
     * Used to generate a list of strings with the information about this course's lab groups.
     *
     * @return List of strings for lab groups
     */
    String[][] generateLabGroupInformation();

    /**
     * Used to generate a list of strings with the information about this course's tutorial groups.
     *
     * @return List of strings for tutorial groups
     */
    String[][] generateTutorialGroupInformation();

    /**
     * Used to generate a list of strings with the information about this course's lecture groups.
     *
     * @return List of strings for lecture groups
     */
    String[][] generateLectureGroupInformation();

}
