package com.softeng306.managers;

import com.softeng306.domain.professor.Professor;

import java.util.ArrayList;

/**
 * Manages all the professor related operations
 */
public class ProfessorMgr {
    /**
     * An array list of all the professors in this school.
     */
    public static ArrayList<Professor> professors = new ArrayList<Professor>(0);

    private static ProfessorMgr singleInstance = null;

    /**
     * Override default constructor to implement singleton pattern
     */
    private ProfessorMgr() {
    }

    /**
     * Return the ProfessorMgr singleton, if not initialised already, create an instance.
     *
     * @return ProfessorMgr the singleton instance
     */
    public static ProfessorMgr getInstance() {
        if (singleInstance == null) {
            singleInstance = new ProfessorMgr();
        }

        return singleInstance;
    }

}
