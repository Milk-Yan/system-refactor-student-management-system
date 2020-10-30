package com.softeng306.domain.course.component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = CourseworkComponent.class)
public interface ICourseworkComponent {
    /**
     * Gets the component name
     *
     * @return the name of this component
     */
    String getComponentName();

    /**
     * Gets the weight of this component
     *
     * @return the weight of this component
     */
    int getComponentWeight();
}
