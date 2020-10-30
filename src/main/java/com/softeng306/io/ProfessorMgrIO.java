package com.softeng306.io;

import java.util.List;

/**
 * Takes care of all the Input/Output processing needed by the
 * ProfessorMgr.
 */
public class ProfessorMgrIO implements IProfessorMgrIO {

    @Override
    public void printAllProfIDsInDepartment(List<String> professors) {
        if (professors == null || professors.isEmpty()) {
            System.out.println("None.");
        } else {
            professors.forEach(System.out::println);
        }
    }
}
