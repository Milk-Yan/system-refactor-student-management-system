package com.softeng306.domain.course.group;

import com.softeng306.enums.GroupType;

/**
 * Concrete implementation of a course group.
 * Stores information about a lecture group, lab group, or tutorial group for a course
 * This is a subclass of {@code IGroup}
 */
public class Group implements IGroup {

    private String groupName;
    private int availableVacancies;
    private GroupType groupType;

    private int capacity;

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
     * @param groupCapacity      This group's total seats.
     */
    public Group(String groupName, int availableVacancies, int groupCapacity, GroupType groupType) {
        this.groupType = groupType;
        this.groupName = groupName;
        this.availableVacancies = availableVacancies;
        this.capacity = groupCapacity;
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
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void updateVacanciesForEnrollment() {
        this.availableVacancies -= 1;
    }

    @Override
    public GroupType getGroupType() {
        return groupType;
    }

}
