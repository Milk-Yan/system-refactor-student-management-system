package com.softeng306.managers;

import com.softeng306.domain.course.group.IGroup;
import com.softeng306.enums.GroupType;

import java.util.List;

/**
 * Interface for defining course group operations.
 * Defines the responsibilities of what functions should be done with course lecture/tutorial/lab groups.
 */
public interface IGroupMgr {
    /**
     * Checks whether the inputted department is valid.
     *
     * @param groupType The type of this group.
     * @param groups    A list of a certain type of groups in a course.
     * @return the name of the group chosen by the user.
     */
    IGroup printGroupWithVacancyInfo(GroupType groupType, List<IGroup> groups);

}
