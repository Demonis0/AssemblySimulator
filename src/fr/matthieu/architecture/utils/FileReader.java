package fr.matthieu.architecture.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private String fileName;
    private File file;
    List<String> lines;

    public FileReader(String fileName) {
        this.fileName = fileName;
    }

    public void init() throws IOException {

        this.file = new File(this.fileName);
        this.fileName = file.getName();
        lines = Files.readAllLines(file.toPath());
        Files.write(file.toPath(), lines);
    }

    public void flush() {

        this.lines = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public List<String> getLines() {
        return lines;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
