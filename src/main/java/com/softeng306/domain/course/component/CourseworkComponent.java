package com.softeng306.domain.course.component;

/**
 * Abstract representation of an assessment component of a course
 * Subclasses by {@code MainComponent}, {@code SubComponent}
 */
public abstract class CourseworkComponent {

    private String name;
    private int weight;

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public CourseworkComponent() {

    }

    /**
     * Creates a course work components with component name and component weight
     *
     * @param name   the name of this coursework component
     * @param weight the weight of this coursework component
     */
    public CourseworkComponent(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    public int getWeight() {
        return this.weight;
    }
    
}
