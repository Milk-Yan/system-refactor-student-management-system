package com.softeng306.domain.course.component;

import java.util.List;

/**
 * Represents a main assessment component of a course.
 * This class is a subclass of {@code CourseworkComponent}.
 * A course can have many main assessment components.
 */
public class MainComponent extends CourseworkComponent {

    /**
     * This main component's sub components.
     */
    private List<SubComponent> subComponents;

    /**
     *
     */
    public final static String COMPONENT_NAME = "main component";

    /**
     * Default constructor. Required for Jackson serialization.
     */
    public MainComponent() {

    }

    /**
     * Creates a main component with component name, component weightage and sub components.
     *
     * @param componentName   the name of the assessment component
     * @param componentWeight the componentWeight of the assessment component
     * @param subComponents   the sub components of the assessment component
     */
    public MainComponent(String componentName, int componentWeight, List<SubComponent> subComponents) {
        super(componentName, componentWeight);
        this.subComponents = subComponents;
    }

    /**
     * Gets the sub components of this main component.
     *
     * @return the sub components of this main component.
     */
    public List<SubComponent> getSubComponents() {
        return this.subComponents;
    }
}
