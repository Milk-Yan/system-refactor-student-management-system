package com.softeng306.domain.mark;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.domain.course.component.SubComponent;

@JsonDeserialize(as = SubComponentMark.class)
public interface ISubComponentMark {
    SubComponent getSubComponent();

    double getMark();

    void setMark(double mark);
}
