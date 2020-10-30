package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.SubComponent;

/**
 * Stores the mark for a student for a sub component.
 */
public class SubComponentMark implements ISubComponentMark {

    private SubComponent subComponent;
    private double mark;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public SubComponentMark() {

    }

    public SubComponentMark(SubComponent subComponent, double mark) {
        this.subComponent = subComponent;
        this.mark = mark;
    }

    @Override
    public SubComponent getSubComponent() {
        return subComponent;
    }

    @Override
    public double getMark() {
        return mark;
    }

    @Override
    public void setMark(double mark) {
        this.mark = mark;
    }
}
