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
    SubComponent getSubComponent();

    double getMark();

    void setMark(double mark);
}
