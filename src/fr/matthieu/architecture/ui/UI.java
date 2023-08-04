package fr.matthieu.architecture.ui;

import fr.matthieu.architecture.errors.*;
import fr.matthieu.architecture.errors.invalidcommands.*;
import fr.matthieu.architecture.utils.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UI {
    public JList list1;
    public JList list2;
    public JList list3;
    public JButton loadFileButton;
    public JButton simulateButton;
    public JButton stepSimulationButton;
    public JScrollBar scrollBar1;
    public JPanel rootPanel;
    public JPanel panel;
    public JPanel panel2;
    public JPanel panel3;
    public JLabel fileName;
    public JLabel errorMsg;
    public JLabel fileStatus;
    private JLabel reg1;
    private JLabel reg2;
    private JLabel nextIns;
    private JLabel reg3;
    private JLabel reg4;
    private JLabel cou;

    public UI() {
        stepSimulationButton.setEnabled(false);
        simulateButton.setEnabled(false);

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    try {
                    Constants.fr = new FileReader(j.getSelectedFile().getAbsolutePath());
                    Constants.fr.init();
                    fileName.setText(Constants.fr.getFileName());

                    Constants.p = new Parser(Constants.fr);
                    Constants.mm = new MemoryManager();
                    Constants.p.load();
                    fileStatus.setText("Loaded");

                    for (String line : Constants.fr.getLines()) {
                        System.out.println(line);
                    }

                    reloadUI();

                    stepSimulationButton.setEnabled(true);
                    simulateButton.setEnabled(true);

                    Constants.d = new Dispatcher();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ArchitectureException ex) {
                        fileStatus.setText("Error");
                        errorMsg.setText(ex.getMessage());
                    } catch (VariableDefinitionException ex) {
                        fileStatus.setText("Error");
                        errorMsg.setText(ex.getMessage());
                    }
                }
            }
        });
        stepSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Constants.d.dispatch(true);

                    reloadUI();
                } catch (CommandNotFoundException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidLDAException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidSTRException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidPOPException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidPUSHException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidANDException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidORException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidSUBException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidADDException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidNOTException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidINCException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidDIVException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidMODException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidMULException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidDECException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidComparisonException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidJMPException ex) {
                    errorMsg.setText(ex.getMessage());
                }
            }
        });
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Constants.d.dispatch(false);

                    reloadUI();
                } catch (CommandNotFoundException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidLDAException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidSTRException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidPOPException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidPUSHException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidANDException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidORException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidSUBException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidADDException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidNOTException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidINCException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidDIVException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidMODException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidMULException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidDECException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidComparisonException ex) {
                    errorMsg.setText(ex.getMessage());
                } catch (InvalidJMPException ex) {
                    errorMsg.setText(ex.getMessage());
                }
            }
        });
    }

    public void reloadUI() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String name : Constants.mm.getMemorySlots().keySet()) {
            model.addElement(name + ": " + Constants.mm.getVariableValue(name));
        }
        list2.setModel(model);

        model = new DefaultListModel<>();
        for (String line : Constants.p.getCODE()) {
            model.addElement(line);
        }
        list1.setModel(model);

        if (Constants.p.getCODE().size() < Constants.mm.getCounter()) {
            nextIns.setText("ENDED");
            stepSimulationButton.setEnabled(false);
            simulateButton.setEnabled(false);
        } else {
            nextIns.setText(Constants.p.getCODE().get(Constants.mm.getCounter()-1));
        }

        cou.setText(String.valueOf(Constants.mm.getCounter()));

        reg1.setText(String.valueOf(Constants.mm.getRegisters().get(0)));
        reg2.setText(String.valueOf(Constants.mm.getRegisters().get(1)));
        reg3.setText(String.valueOf(Constants.mm.getRegisters().get(2)));
        reg4.setText(String.valueOf(Constants.mm.getRegisters().get(3)));

        model = new DefaultListModel<>();
        for (int i : Constants.mm.getStack()) {
            model.addElement(""+i);
        }
        list3.setModel(model);
        if (Constants.mm.getCounter() < Constants.p.getCODE().size()+1)
        list1.setSelectedIndex(Constants.mm.getCounter()-1);
    }


}
