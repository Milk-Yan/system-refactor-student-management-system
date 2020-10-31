package com.softeng306.domain.professor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.enums.Department;

/**
 * Interface to define operations for a professor.
 * Professors can be the coordinator for a course.
 * Provides methods to get information.
 */
@JsonDeserialize(as = Professor.class)
public interface IProfessor {
    /**
     * @return The ID of this professor
     */
    String getProfessorId();

    /**
     * @return The name of this professor
     */
    String getName();

    /**
     * @return The department of this professor
     */
    Department getDepartment();

}
