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
     * Gets whether the MainComponentMark has SubComponentMarks or not.
     * @return true if the MainComponentMark has SubComponentMarks, otherwise false.
     */
    boolean hasSubComponentMarks();

    /**
     * Gets the SubComponentMark for a given SubComponent of the MainComponent, identified by the SubComponents name.
     * @param courseWorkName the name of the SubComponent to get marks for
     * @return SubComponentMark for courseWorkName
     */
    ISubComponentMark getSubComponentMark(String courseWorkName);

    /**
     * Adds a mark for a SubComponent within the MainComponent.
     * @param subComponentMark the subComponentMark to add.
     */
    void addSubComponentMark(ISubComponentMark subComponentMark);

    /**
     * Gets the MainComponent this mark is for
     * @return MainComponent
     */
    MainComponent getMainComponent();

    /**
     * Sets the MainComponent this mark is for
     * @param mainComponent
     */
    void setMainComponent(MainComponent mainComponent);

    /**
     * Gets the mark for the MainComponent this MainComponentMark is for
     * @return mark
     */
    double getMark();

    /**
     * Sets the mark for the MainComponent this MainComponentMark is for
     * @param mark
     */
    void setMark(double mark);

    List<ISubComponentMark> getSubComponentMarks();

}
