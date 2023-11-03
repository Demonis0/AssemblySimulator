package fr.matthieu.architecture;

import fr.matthieu.architecture.errors.ArchitectureException;
import fr.matthieu.architecture.ui.UI;
import fr.matthieu.architecture.utils.FileReader;
import fr.matthieu.architecture.utils.Parser;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Assembly simulator");
        JFrame frame = new JFrame("Assembly Simulator");
        UI ui =new UI();
        frame.setContentPane(ui.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}
