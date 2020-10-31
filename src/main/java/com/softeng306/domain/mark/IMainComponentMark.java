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

    ISubComponentMark getSubComponentMark(String courseWorkName);

    void addSubComponentMark(ISubComponentMark subComponentMark);

    MainComponent getMainComponent();

    void setMainComponent(MainComponent mainComponent);

    double getMark();

    void setMark(double mark);

    List<ISubComponentMark> getSubComponentMarks();

}
