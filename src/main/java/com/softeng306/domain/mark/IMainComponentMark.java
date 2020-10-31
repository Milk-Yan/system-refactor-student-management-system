package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.MainComponent;

import java.util.List;

/**
 * Interface for a MainComponentMark.
 * Used to represent a Mark for a MainComponent of a Course.
 * Provides methods to get and set the MainComponents marks, and it's SubComponents
 */
@JsonDeserialize(as = MainComponentMark.class)
public interface IMainComponentMark {

    /**
     * Gets whether the MainComponent has SubComponent Marks or not.
     * @return true if the MainComponent has SubComponent Marks, otherwise false.
     */
    boolean hasSubComponentMarks();

    /**
     * Gets the SubComponentMark for a given SubComponent of this MainComponent, identified by the SubComponents name.
     * @param courseWorkName the name of the SubComponent to get marks for
     * @return SubComponentMark for courseWorkName
     */
    ISubComponentMark getSubComponentMark(String courseWorkName);

    /**
     * Adds a mark for a SubComponent within this MainComponent.
     * @param subComponentMark the subComponentMark to add.
     */
    void addSubComponentMark(ISubComponentMark subComponentMark);

    MainComponent getMainComponent();

    void setMainComponent(MainComponent mainComponent);

    double getMark();

    void setMark(double mark);

    List<ISubComponentMark> getSubComponentMarks();

}
