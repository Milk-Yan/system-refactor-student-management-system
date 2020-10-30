package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.MainComponent;

import java.util.List;

@JsonDeserialize(as = MainComponentMark.class)
public interface IMainComponentMark {
    boolean hasSubComponentMarks();

    ISubComponentMark getSubComponentMark(String courseWorkName);

    void addSubComponentMark(ISubComponentMark subComponentMark);

    MainComponent getMainComponent();

    void setMainComponent(MainComponent mainComponent);

    double getMark();

    void setMark(double mark);

    List<ISubComponentMark> getSubComponentMarks();
}
