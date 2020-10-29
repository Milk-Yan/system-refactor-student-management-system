package com.softeng306.validation;


import com.softeng306.domain.professor.Professor;
import com.softeng306.managers.ProfessorMgr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfessorValidator {

    public static boolean checkProfessorExists(String profID){
        Optional<Professor> professor = ProfessorMgr
                .getInstance()
                .getProfessors()
                .stream()
                .filter(p -> profID.equals(p.getProfID()))
                .findFirst();

        return professor.isPresent();
    }

}
