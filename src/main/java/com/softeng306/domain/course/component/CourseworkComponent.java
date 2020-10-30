package com.softeng306.domain.course.component;

public abstract class CourseworkComponent implements ICourseworkComponent {

    private String componentName;

    private int componentWeight;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public CourseworkComponent() {

    }

    /**
     * Creates a course work components with component name and component weight
     *
     * @param componentName   the name of this coursework component
     * @param componentWeight the weight of this coursework component
     */
    public CourseworkComponent(String componentName, int componentWeight) {
        this.componentName = componentName;
        this.componentWeight = componentWeight;
    }

    @Override
    public String getComponentName() {
        return this.componentName;
    }

    @Override
    public int getComponentWeight() {
        return this.componentWeight;
    }
}
