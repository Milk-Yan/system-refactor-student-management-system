package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.mark.Mark;
import com.softeng306.domain.professor.Professor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfessorFileProcessor extends FileProcessor<Professor> {

    private static final String PROFESSOR_FILE_PATH = "data/professorFile.json";

    /**
     * Loads a list of all the professors from {@value PROFESSOR_FILE_PATH}.
     * @return A list of all the professors that is loaded from the file.
     */
    @Override
    public List<Professor> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File professorFile = Paths.get(PROFESSOR_FILE_PATH).toFile();
        ArrayList<Professor> allProfessors = new ArrayList<>();

        try {
            allProfessors = new ArrayList<>(Arrays.asList(objectMapper.readValue(professorFile, Professor[].class)));
        } catch (IOException e) {
            System.out.println("Error occurs when loading professors.");
            e.printStackTrace();
        }

        return allProfessors;
    }

    /**
     * Writes a new professor into {@value PROFESSOR_FILE_PATH}.
     * @param professor the new professor to write to the file
     */
    @Override
    public void writeNewEntryToFile(Professor professor) {
        try {
            List<Professor> professors = loadFile();
            professors.add(professor);

            writeToFile(PROFESSOR_FILE_PATH, professors);
        } catch (IOException e) {
            System.out.println("Error in adding a professor to the file.");
            e.printStackTrace();
        }
    }
}
