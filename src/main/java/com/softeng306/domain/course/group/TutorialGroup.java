package com.softeng306.domain.course.group;

/**
 * Represents TutorialGroup for a course.
 * A course can have many lecture groups.
 * This class is a subclass of {@code Group}.

 */

public class TutorialGroup extends Group{

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public TutorialGroup() {

    }

    /**
     * Creates a tutorial group given group name, available vacancies and total seats.
     * @param groupName This tutorial group's name.
     * @param availableVacancies This tutorial group's current available vacancy.
     * @param totalSeats This tutorial group's total seats.
     */
    public TutorialGroup(String groupName, int availableVacancies, int totalSeats) {
        super(groupName, availableVacancies, totalSeats);
    }
}
