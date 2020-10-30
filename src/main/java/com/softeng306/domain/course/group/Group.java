package com.softeng306.domain.course.group;

import com.softeng306.enums.GroupType;

/**
 * Represents study groups (LectureGroup, TutorialGroup and LabGroup) for a course.
 * A course must have at least one lecture group.
 * A course can have many or no tutorial groups and lab groups.
 * Student enrolled in this course must also be enrolled in one of the groups of each type.
 */

public class Group implements IGroup {

    private String groupName;
    private int availableVacancies;
    private GroupType groupType;

    private int totalSeats;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public Group() {

    }

    /**
     * Creates a group with the group name, the current available vacancy of this course, and the total seats for this group.
     *
     * @param groupName          This group's name.
     * @param availableVacancies This group's current available vacancy.
     * @param totalSeats         This group's total seats.
     */
    public Group(String groupName, int availableVacancies, int totalSeats, GroupType groupType) {
        this.groupType = groupType;
        this.groupName = groupName;
        this.availableVacancies = availableVacancies;
        this.totalSeats = totalSeats;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public int getAvailableVacancies() {
        return this.availableVacancies;
    }

    @Override
    public int getTotalSeats() {
        return totalSeats;
    }

    @Override
    public void enrolledIn() {
        this.availableVacancies -= 1;
    }


    @Override
    public GroupType getGroupType() {
        return groupType;
    }

}
