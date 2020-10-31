package com.softeng306.fileprocessing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softeng306.domain.professor.IProfessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation of a file processor for professors.
 * Used to read and write professor data from a file.
 * This class extends {@code FileProcessor}
 */
public class ProfessorFileProcessor extends FileProcessor<IProfessor> {
    /**
     * The path to the file storing professor data.
     */
    private static final String PROFESSOR_FILE_PATH = "data/professorFile.json";

    /**
     * Loads a list of all the professors from {@value PROFESSOR_FILE_PATH}.
     *
     * @return A list of all the professors loaded from the file.
     */
    @Override
    public List<IProfessor> loadFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File professorFile = Paths.get(PROFESSOR_FILE_PATH).toFile();
        ArrayList<IProfessor> allProfessors = new ArrayList<>();

        try {
            allProfessors = new ArrayList<>(Arrays.asList(objectMapper.readValue(professorFile, IProfessor[].class)));
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
    public void writeNewEntryToFile(IProfessor professor) {
        // dummy method
    }

    /**
     * Dummy method without functionality since we don't need it.
     */
    @Override
    public void updateFileContents(List<IProfessor> updatedProfessors) {
        // dummy method
    }

}
