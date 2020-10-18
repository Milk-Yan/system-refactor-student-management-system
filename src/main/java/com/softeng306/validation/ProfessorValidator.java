package com.softeng306.validation;


import com.softeng306.domain.professor.Professor;
import com.softeng306.managers.ProfessorMgr;

import java.util.List;
import java.util.stream.Collectors;

public class ProfessorValidator {
    private static final String PROFESSOR_ID_REGEX = "^P[0-9]{7}[A-Z]$";
    private static final String PROFESSOR_NAME_REGEX = "^[ a-zA-Z]+$";

    /**
     * Checks whether the inputted professor ID is in the correct format.
     *
     * @param profID The inputted professor ID.
     * @return boolean indicates whether the inputted professor ID is valid.
     */
    public static boolean checkValidProfIDInput(String profID) {
        return RegexValidator.checkStringRegexFormat(profID, PROFESSOR_ID_REGEX);
    }

    /**
     * Checks whether the inputted professor name is in the correct format.
     *
     * @param professorName The inputted professor name.
     * @return boolean indicates whether the professor person name is valid.
     */
    public static boolean checkValidProfessorNameInput(String professorName) {
        return RegexValidator.checkStringRegexFormat(professorName, PROFESSOR_NAME_REGEX);
    }

    /**
     * Checks whether this professor ID is used by other professors.
     *
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public static Professor checkProfExists(String profID) {
        List<Professor> anyProf = ProfessorMgr.professors.stream().filter(p -> profID.equals(p.getProfID())).collect(Collectors.toList());
        if (anyProf.size() == 0) {
            return null;
        }
        System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);

    }
}
