package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
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
     * Dummy method without functionality since we don't need it.
     */
    @Override
    public void writeNewEntryToFile(Professor professor) {
        // dummy method
    }

    /**
     * Dummy method without functionality since we don't need it.
     */
    @Override
    public void updateFileContents(List<Professor> updatedProfessors) {
        // dummy method
    }
}
