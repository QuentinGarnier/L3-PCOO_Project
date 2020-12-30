package graphics.tabpanels;

import graphics.tablemodels.ProgramTableModel;
import minutes.Minutes;
import program.Program;
import student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class: JPanel Tab for a specific program
 * @author Quentin Garnier
 */

public class ProgramTabPanel extends CustomTabPanel {
    private Program[] programs;
    private int currentProgramIndex;
    private Student[] students;
    private JComboBox programsList;

    /**
     * Constructor
     * @param prgs: a list of programs
     * @param stds: a list of all students to get their data
     */
    public ProgramTabPanel(Program[] prgs, Student[] stds) {
        this(prgs, stds, 0);
    }

    private ProgramTabPanel(Program[] prgs, Student[] stds, int defaultIndex) {
        super(new BorderLayout());
        create(prgs, stds, defaultIndex);
    }

    private void create(Program[] prgs, Student[] stds, int defaultIndex) {
        this.programs = prgs;
        this.students = stds;
        this.currentProgramIndex = defaultIndex; //par défaut
        ProgramTableModel tableModel = new ProgramTableModel(programs[currentProgramIndex], this.students);

        //TITLE:
        title("Programme : " + programs[currentProgramIndex].getName());

        //ARRAY:
        JTable tab = new JTable(tableModel);
        createTable(tab, true);

        //PROGRAMS LIST:
        programsList = new JComboBox(programs);
        programsList.setSelectedIndex(defaultIndex);
        programsList.addActionListener(new ScrollMenu());
        JLabel comboBoxLabel = new JLabel("Séléctionner un programme : ");

        //BUTTONS:
        JButton buttonExport = new JButton(new ExportAction());
        buttonExport.setToolTipText("Exporter en format .csv");

        //FOOTER :
        footer(comboBoxLabel, programsList, new JLabel("  |  "), buttonExport);
    }

    private void reset(int index) {
        removeAll();
        create(programs, students, index);
    }

    private class ExportAction extends AbstractAction {
        private ExportAction() {
            super("Exporter en CSV");
        }

        public void actionPerformed(ActionEvent e) {
            Minutes.create(programs[currentProgramIndex], students);
            JOptionPane.showMessageDialog(null, "Exportation terminée !", "Successful export!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ScrollMenu extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            reset(programsList.getSelectedIndex());
        }
    }
}
