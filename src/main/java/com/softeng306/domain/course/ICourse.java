package com.softeng306.domain.course;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.MainComponent;
import com.softeng306.domain.course.group.IGroup;
import com.softeng306.domain.professor.IProfessor;
import com.softeng306.domain.professor.Professor;
import com.softeng306.enums.CourseType;
import com.softeng306.enums.Department;

import java.util.List;

@JsonDeserialize(as = Course.class)
public interface ICourse {
    /**
     * Gets the course's ID.
     *
     * @return the ID of this course.
     */
    String getCourseID();

    /**
     * Gets the course's name.
     *
     * @return the name of this course.
     */
    String getCourseName();

    /**
     * @return the number of academic units for this course.
     */
    int getAcademicUnits();

    /**
     * Gets the course's professor in charge.
     *
     * @return the professor in charge of this course.
     */
    IProfessor getProfInCharge();

    /**
     * Gets the course's current vacancy.
     *
     * @return the current vacancy of this course.
     */
    int getVacancies();

    /**
     * Gets the course's total seats.
     *
     * @return the total seats of this course.
     */
    int getTotalSeats();

    /**
     * Gets the course's department.
     *
     * @return the department of this course.
     */
    Department getCourseDepartment();

    /**
     * Gets the course's type.
     *
     * @return the type of this course.
     */
    CourseType getCourseType();

    /**
     * Gets the course's weekly lecture hour.
     *
     * @return the weekly lecture hour of this course.
     */
    int getLecWeeklyHour();

    /**
     * Gets the course's weekly tutorial hour.
     *
     * @return the weekly tutorial hour of this course.
     */
    int getTutWeeklyHour();

    /**
     * Gets the course's weekly lab hour.
     *
     * @return the weekly lab hour of this course.
     */
    int getLabWeeklyHour();

    /**
     * Gets the course's lecture groups.
     *
     * @return the lecture groups of this course.
     */
    List<IGroup> getLectureGroups();

    /**
     * Gets the course's tutorial groups
     *
     * @return the tutorial groups of this course
     */
    List<IGroup> getTutorialGroups();

    /**
     * Gets the course's lab groups.
     *
     * @return the lab groups of this course.
     */
    List<IGroup> getLabGroups();

    /**
     * Gets the course's main assessment components.
     *
     * @return the main assessment components of this course.
     */
    List<MainComponent> getMainComponents();

    /**
     * Sets the course's current current vacancy.
     *
     * @param vacancies this course's vacancy.
     */
    void setVacancies(int vacancies);

    /**
     * Updates the available vacancies of this course after someone has registered this group.
     */
    void enrolledIn();

    /**
     * Sets the tutorial groups of the lecture groups.
     *
     * @param tutorialGroups this course's tutorial groups.
     */
    void setTutorialGroups(List<IGroup> tutorialGroups);

    /**
     * Sets the lab groups of the lecture groups.
     *
     * @param labGroups this course's lab groups.
     */
    void setLabGroups(List<IGroup> labGroups);

    /**
     * Sets the main assessment of the lecture groups.
     *
     * @param mainComponents this course's main assessment.
     */
    void setMainComponents(List<MainComponent> mainComponents);

    /**
     * Sets the weekly hour of the tutorials.
     *
     * @param tutWeeklyHour this course's weekly tutorial hour.
     */
    void setTutWeeklyHour(int tutWeeklyHour);

    void setLecWeeklyHour(int lecWeeklyHour);

    /**
     * Sets the weekly hour of the labs.
     *
     * @param labWeeklyHour this course's weekly lab hour.
     */
    void setLabWeeklyHour(int labWeeklyHour);

    /**
     * Sets the academic unit for the course.
     *
     * @param academicUnits Academic unit for course.
     */
    void setAcademicUnits(int academicUnits);

    /**
     * Sets the IDfor the course.
     *
     * @param id ID for course.
     */
    void setID(String id);

    /**
     * Sets the name for the course.
     *
     * @param name Name for course.
     */
    void setName(String name);

    /**
     * Sets the Professor in charge of the course.
     *
     * @param professor Professor in charge of course.
     */
    void setProfInCharge(IProfessor professor);

    /**
     * Sets the total seats available for this course.
     *
     * @param totalSeats Total seats available for this course.
     */
    void setTotalSeat(int totalSeats);

    /**
     * Sets the lecture groups for this course.
     *
     * @param lectureGroups Lecture groups for this course.
     */
    void setLectureGroups(List<IGroup> lectureGroups);

    /**
     * Sets the department for this course.
     *
     * @param department Department for this course.
     */
    void setCourseDepartment(Department department);

    void setType(CourseType type);

    String[][] generateLabGroupInformation();

    String[][] generateTutorialGroupInformation();

    String[][] generateLectureGroupInformation();

    default String[][] generateGroupInformation(List<IGroup> groups) {
        String[][] groupInfo = new String[groups.size()][3];
        for (int i = 0; i < groups.size(); i++) {
            groupInfo[i][0] = groups.get(i).getGroupName();
            groupInfo[i][1] = String.valueOf(groups.get(i).getAvailableVacancies());
            groupInfo[i][2] = String.valueOf(groups.get(i).getTotalSeats());
        }
        return groupInfo;
    }
}
