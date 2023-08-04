package fr.matthieu.architecture.utils;

import fr.matthieu.architecture.errors.ArchitectureException;
import fr.matthieu.architecture.errors.VariableDefinitionException;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    FileReader fileReader;
    List<String> DATA, CODE;

    public Parser(FileReader fileReader) {
        this.fileReader = fileReader;
        this.DATA = new ArrayList<>();
        this.CODE = new ArrayList<>();
    }

    public void update(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public void load() throws ArchitectureException, VariableDefinitionException {
        removeComments();

        if (!validArchitecture()) {
            throw new ArchitectureException("Invalid architecture (Check your file only has one #DATA and #CODE part)");
        }
        readLines();

        parseVariables();
    }

    public void removeComments() {
        List<String> lines = new ArrayList<>();
        for (String line : this.fileReader.getLines()) {
            if (line.equals("")) continue;

            if (!(line.startsWith("!"))) {
                lines.add(line);
            }
        }
        this.fileReader.setLines(lines);
    }

    public boolean validArchitecture() {
        int i = 0;
        int j = 0;

        for (String line : this.fileReader.getLines()) {
            if (line.equals("#DATA")) i++;
            if (line.equals("#CODE")) j++;
        }

        return i == 1 && j == 1;
    }

    public void readLines() {
        boolean data = true;
        int codecounter = 1;
        for (String line : this.fileReader.getLines()) {
            if (line.equals("")) continue;

            if (data) {
                if (line.equals("#CODE")) {
                    data = false;
                } else if (!line.equals("#DATA")) {
                    this.DATA.add(line);
                }
            } else {
                if (line.split(":").length == 1 && line.endsWith(":")) {
                    Constants.mm.addLabel(line.split(":")[0], codecounter-1);
                } else {
                    this.CODE.add(line);
                    codecounter++;
                }
            }
        }
    }

    public void parseVariables() throws VariableDefinitionException {
        for (String line : this.DATA) {
            if (line.equals("")) continue;

            String[] args = line.split(" ");

            if (args.length != 2) {
                throw new VariableDefinitionException("A variable line got too many arguments (" + line + ")");
            }

            try {
                int n = Integer.parseInt(args[0]);
                throw new VariableDefinitionException("A variable's name cannot be an integer (" + line + ")");
            } catch (NumberFormatException e) {}
            try {
                int n = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new VariableDefinitionException("A variable's value have to be an integer (" + line + ")");
            }

            Constants.mm.createVariable(args[0], Integer.parseInt(args[1]));
        }
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public List<String> getDATA() {
        return DATA;
    }

    public void setDATA(List<String> DATA) {
        this.DATA = DATA;
    }

    public List<String> getCODE() {
        return CODE;
    }

    public void setCODE(List<String> CODE) {
        this.CODE = CODE;
    }
}
