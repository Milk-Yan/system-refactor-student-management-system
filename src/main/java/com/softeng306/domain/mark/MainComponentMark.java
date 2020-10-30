package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.MainComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the mark of a student for one main component
 * and stores the subcomponent marks that make it up.
 */
public class MainComponentMark {

    private MainComponent mainComponent;
    private double mark;
    private List<SubComponentMark> subComponentMarks;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public MainComponentMark() {

    }

    public MainComponentMark(MainComponent mainComponent, double mark) {
        this.mainComponent = mainComponent;
        this.mark = mark;
        this.subComponentMarks = new ArrayList<>();
    }

    public MainComponentMark(MainComponent mainComponent, double mark, List<SubComponentMark> subComponentMarks) {
        this.mainComponent = mainComponent;
        this.mark = mark;
        this.subComponentMarks = subComponentMarks;
    }

    public boolean hasSubComponentMarks() {
        if (subComponentMarks == null || subComponentMarks.isEmpty()) {
            return false;
        }

        return true;
    }

    public SubComponentMark getSubComponentMark(String courseWorkName) {
        for (SubComponentMark subComponentMark : subComponentMarks) {
            if (subComponentMark.getSubComponent().getComponentName().equals(courseWorkName)) {
                return subComponentMark;
            }
        }

        return null;
    }

    public void addSubComponentMark(SubComponentMark subComponentMark) {
        subComponentMarks.add(subComponentMark);
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public void setMainComponent(MainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public List<SubComponentMark> getSubComponentMarks() {
        return subComponentMarks;
    }

    public void setSubComponentMarks(List<SubComponentMark> subComponentMarks) {
        this.subComponentMarks = subComponentMarks;
    }
}
