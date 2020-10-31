package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.SubComponent;

/**
 * Interface for a SubComponentMark.
 * Used to represent a Mark for a SubComponent of a Course.
 * Provides methods to get SubComponent this mark is for, and to get and set the grade given to this mark.
 */
@JsonDeserialize(as = SubComponentMark.class)
public interface ISubComponentMark {

    /**
     * Gets the SubComponent this mark is for.
     * @return the SubComponent this mark is for.
     */
    SubComponent getSubComponent();

    /**
     * Gets the grade given for this mark as a double.
     * @return the grade given for this mark as a double.
     */
    double getMark();

    /**
     * Sets the grade given for this mark as a double.
     * @param mark the grade given for this mark as a double.
     */
    void setMark(double mark);
}
