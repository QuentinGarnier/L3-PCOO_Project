package graphics.tabpanels;

import graphics.tablemodels.ProgramTableModel;
import minutes.Minutes;
import program.Program;
import student.Student;
import xmlreader.XMLReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class: JPanel Tab for a specific program
 * @author Quentin Garnier
 */

public class ProgramTabPanel extends CustomTabPanel {
    private int currentProgramIndex;
    private JComboBox programsList;

    public ProgramTabPanel() {
        super(new BorderLayout());
        create(0);
    }

    private void create(int defaultIndex) {
        this.currentProgramIndex = defaultIndex; //par défaut
        ProgramTableModel tableModel = new ProgramTableModel(XMLReader.getPrograms().get(currentProgramIndex), XMLReader.getStudents().toArray(new Student[0]));

        //TITLE:
        title("Programme : " + XMLReader.getPrograms().get(currentProgramIndex).getName());

        //ARRAY:
        JTable tab = new JTable(tableModel);
        this.add(createTable(tab, true), BorderLayout.CENTER);

        //PROGRAMS LIST:
        programsList = new JComboBox(XMLReader.getPrograms().toArray(new Program[0]));
        programsList.setSelectedIndex(defaultIndex);
        programsList.addActionListener(new ScrollMenu());
        JLabel comboBoxLabel = new JLabel("Séléctionner un programme : ");

        //BUTTONS:
        JButton buttonExport = new JButton(new ExportAction());
        buttonExport.setToolTipText("Exporter en format .csv");

        //FOOTER:
        footer(comboBoxLabel, programsList, new JLabel("  |  "), buttonExport);
    }

    @Override
    public void reset() {
        reset(currentProgramIndex);
    }

    private void reset(int index) {
        removeAll();
        create(index);
    }

    private class ExportAction extends AbstractAction {
        private ExportAction() {
            super("Exporter en CSV");
        }

        public void actionPerformed(ActionEvent e) {
            Minutes.create(XMLReader.getPrograms().get(currentProgramIndex), XMLReader.getStudents().toArray(new Student[0]));
            JOptionPane.showMessageDialog(null, "Exportation terminée !", "Successful export!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ScrollMenu extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            reset(programsList.getSelectedIndex());
        }
    }
}
