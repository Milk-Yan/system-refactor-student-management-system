package com.softeng306.validation;


import com.softeng306.domain.professor.Professor;
import com.softeng306.managers.ProfessorMgr;

import java.util.List;
import java.util.stream.Collectors;

public class ProfessorValidator {

    public static boolean checkProfessorExists(String profID){
        List<Professor> anyProf = ProfessorMgr.getInstance().getProfessors().stream().filter(p -> profID.equals(p.getProfID())).collect(Collectors.toList());
        if (anyProf.isEmpty()) {
            return false;
        }
        return true;
    }

}
