package com.softeng306.domain.professor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softeng306.enums.Department;

@JsonDeserialize(as = Professor.class)
public interface IProfessor {
    /**
     * Gets this professor's ID
     *
     * @return the ID of this professor
     */
    String getProfessorId();

    /**
     * Gets this professor's name
     *
     * @return the name of this professor
     */
    String getName();

    /**
     * Gets this professor's department
     *
     * @return the department of this professor
     */
    Department getDepartment();

    /**
     * Sets the department of the professor
     *
     * @param profDepartment this professor's department
     */
    void setDepartment(Department profDepartment);

}
