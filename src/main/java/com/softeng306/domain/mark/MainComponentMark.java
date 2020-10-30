package com.softeng306.domain.mark;

import com.softeng306.domain.course.component.MainComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the mark of a student for one main component
 * and stores the subcomponent marks that make it up.
 */
public class MainComponentMark implements IMainComponentMark {

    private MainComponent mainComponent;
    private double mark;
    private List<ISubComponentMark> subComponentMarks;

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

    public MainComponentMark(MainComponent mainComponent, double mark, List<ISubComponentMark> subComponentMarks) {
        this.mainComponent = mainComponent;
        this.mark = mark;
        this.subComponentMarks = subComponentMarks;
    }

    @Override
    public boolean hasSubComponentMarks() {
        if (subComponentMarks == null || subComponentMarks.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public ISubComponentMark getSubComponentMark(String courseWorkName) {
        for (ISubComponentMark subComponentMark : subComponentMarks) {
            if (subComponentMark.getSubComponent().getName().equals(courseWorkName)) {
                return subComponentMark;
            }
        }

        return null;
    }

    @Override
    public void addSubComponentMark(ISubComponentMark subComponentMark) {
        subComponentMarks.add(subComponentMark);
    }

    @Override
    public MainComponent getMainComponent() {
        return mainComponent;
    }

    @Override
    public void setMainComponent(MainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }

    @Override
    public double getMark() {
        return mark;
    }

    @Override
    public void setMark(double mark) {
        this.mark = mark;
    }

    @Override
    public List<ISubComponentMark> getSubComponentMarks() {
        return subComponentMarks;
    }

}
