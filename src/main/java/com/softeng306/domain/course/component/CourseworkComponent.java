package com.softeng306.domain.course.component;

public abstract class CourseworkComponent {

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

    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    public String getComponentName() {
        return this.componentName;
    }

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    public int getComponentWeight() {
        return this.componentWeight;
    }
}
