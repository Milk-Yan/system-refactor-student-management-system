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
        boolean valid = RegexValidator.checkStringRegexFormat(profID, PROFESSOR_ID_REGEX);
        if (!valid) {
            System.out.println("Wrong format of prof ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted professor name is in the correct format.
     *
     * @param professorName The inputted professor name.
     * @return boolean indicates whether the professor person name is valid.
     */
    public static boolean checkValidProfessorNameInput(String professorName) {
        boolean valid = RegexValidator.checkStringRegexFormat(professorName, PROFESSOR_NAME_REGEX);
        if (!valid) {
            System.out.println("Wrong format of name.");
        }
        return valid;
    }

    /**
     * Checks whether this professor ID is used by other professors.
     *
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public static Professor checkProfExists(String profID) {
        List<Professor> anyProf = ProfessorMgr.getInstance().getProfessors().stream().filter(p -> profID.equals(p.getProfID())).collect(Collectors.toList());
        if (anyProf.size() == 0) {
            return null;
        }
        return anyProf.get(0);

    }
}
