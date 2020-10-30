package com.softeng306.domain.course.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.enums.GroupType;

/**
 * Interface for a group.
 * Represents study groups (LectureGroup, TutorialGroup and LabGroup) for a course.
 * A course must have at least one lecture group.
 * A course can have many or no tutorial groups and lab groups.
 * Students enrolled in this course must also be enrolled in one of the groups of each type.
 * Provides methods for getting and setting group information.
 */
@JsonDeserialize(as = Group.class)
public interface IGroup {
    /**
     * @return This group's name.
     */
    String getGroupName();

    /**
     * @return This group's current number of available seats.
     */
    int getAvailableVacancies();

    /**
     * @return This group's total seats.
     */
    int getCapacity();

    /**
     * @return The type of this group: Lecture/Tutorial/Lab
     */
    GroupType getGroupType();

    /**
     * Updates the available vacancies of this group after someone has registered in it.
     */
    void updateVacanciesForEnrollment();

}
