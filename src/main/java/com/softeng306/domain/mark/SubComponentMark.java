package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.SubComponent;

/**
 * Stores the mark for a student for a sub component.
 */
public class SubComponentMark {

    private SubComponent subComponent;
    private Double mark;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public SubComponentMark() {

    }

    public SubComponentMark(SubComponent subComponent, Double mark) {
        this.subComponent = subComponent;
        this.mark = mark;
    }

    public SubComponent getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(SubComponent subComponent) {
        this.subComponent = subComponent;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
