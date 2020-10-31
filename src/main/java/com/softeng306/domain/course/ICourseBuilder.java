package com.softeng306.domain.course;

import com.softeng306.domain.exceptions.ProfessorNotFoundException;

import java.util.Map;

/**
 * Interface for course builders.
 * Used to create new instances of academic courses.
 * Defines responsibilities involved in building an academic course.
 */
public interface ICourseBuilder {
    /**
     * Sets the ID for the new course.
     *
     * @param id
     */
    void setCourseID(String id);

    /**
     * Sets the name for the new course.
     *
     * @param name
     */
    void setCourseName(String name);

    /**
     * Sets the course coordinator of the new course.
     *
     * @param profInCharge
     * @throws ProfessorNotFoundException
     */
    void setCourseCoordinator(String profInCharge) throws ProfessorNotFoundException;

    /**
     * Sets the total number of seats available in the new course.
     *
     * @param totalSeats
     */
    void setCourseCapacity(int totalSeats);

    /**
     * Sets the lecture groups for the new course.
     *
     * @param lectureGroups
     */
    void setLectureGroups(Map<String, Double> lectureGroups);

    /**
     * Sets the tutorial groups for the new course.
     *
     * @param tutorialGroups
     */
    void setTutorialGroups(Map<String, Double> tutorialGroups);

    /**
     * Sets the lab groups for the new course.
     *
     * @param labGroups
     */
    void setLabGroups(Map<String, Double> labGroups);

    /**
     * Sets the academic units for the new course.
     *
     * @param academicUnits
     */
    void setAcademicUnits(int academicUnits);

    /**
     * Sets the department of the new course.
     *
     * @param department
     */
    void setCourseDepartment(String department);

    /**
     * Sets the type of the new course.
     *
     * @param Type
     */
    void setCourseType(String Type);

    /**
     * Sets the weekly lecture hour of the new course.
     *
     * @param lecWeeklyHour
     */
    void setLecWeeklyHour(int lecWeeklyHour);

    /**
     * Sets the weekly tutorial hour of the new course.
     *
     * @param tutWeeklyHour
     */
    void setTutWeeklyHour(int tutWeeklyHour);

    /**
     * Sets the weekly lab hour of the new course.
     *
     * @param labWeeklyHour
     */
    void setLabWeeklyHour(int labWeeklyHour);

    /**
     * Returns the new course with the set properties.
     *
     * @return
     */
    ICourse build();

}
