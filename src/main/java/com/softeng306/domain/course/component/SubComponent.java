package com.softeng306.domain.course.component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a sub-component of a main component.
 * A main component can have many or no sub-components.
 * This class extends {@code CourseWorkComponent}.
 */
public class SubComponent extends CourseworkComponent {

    /**
     * Default constructor. Required for Jackson serialization.
     */

    @JsonIgnore
    public final static String COMPONENT_NAME = "sub component";


    public SubComponent() {

    }

    /**
     * Creates a sub-component with this sub-component's name and this sub-component's weightage.
     * This function makes use of the interface {@code CourseWorkComponent}.
     *
     * @param componentName   This sub-component's name.
     * @param componentWeight This sub-component's weightage.
     */
    public SubComponent(String componentName, int componentWeight) {
        super(componentName, componentWeight);
    }
}
