package graphics;

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

class ProgramTabPanel extends JPanel {
    private ProgramTableModel tableModel;
    private JTable tab;
    private Program[] programs;
    private int currentProgramIndex;
    private Student[] students;
    private JComboBox programsList;

    /**
     * Constructor
     * @param prgs: a list of programs
     * @param stds: a list of all students to get their data
     */
    ProgramTabPanel(Program[] prgs, Student[] stds) {
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
        this.tableModel = new ProgramTableModel(programs[currentProgramIndex], this.students);

        //TITLE:
        title("Programme : " + programs[currentProgramIndex].getName());

        //ARRAY:
        tab = new JTable(tableModel);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setRowHeight(30);
        for(int i=0; i<tab.getModel().getColumnCount(); i++) tab.getColumnModel().getColumn(i).setMinWidth(140);
        JScrollPane scroll = new JScrollPane(tab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll, BorderLayout.CENTER);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        //BUTTONS:
        JPanel footer = new JPanel();
        JButton buttonAdd = new JButton(new AddAction());
        JButton buttonRemove = new JButton(new RemoveAction());
        JButton buttonExport = new JButton(new ExportAction());

        buttonAdd.setToolTipText("Ajouter un étudiant");
        buttonRemove.setToolTipText("Supprimer les lignes sélectionnées");
        buttonExport.setToolTipText("Exporter en format .csv");

        footer.add(buttonAdd);
        footer.add(buttonRemove);
        footer.add(buttonExport);

        //Liste déroulante des programmes :
        //JComboBox programsList;
        programsList = new JComboBox(programs);
        programsList.setSelectedIndex(defaultIndex);
        programsList.addActionListener(new ScrollMenu());

        //FOOTER :
        footer.add(programsList);
        this.add(footer, BorderLayout.SOUTH);
    }

    private void title(String txt) {
        JPanel titleP = new JPanel();
        JLabel titleL = new JLabel(txt, JLabel.CENTER);
        titleL.setFont(new Font("Serif", Font.BOLD, 32));
        titleP.add(titleL);
        this.add(titleP, BorderLayout.NORTH);
    }

    private void reset(int index) {
        removeAll();
        create(programs, students, index);
    }

    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }

        public void actionPerformed(ActionEvent e) {
            tableModel.addStudent(new Student(21604250, "Emma", "Kitty"));
        }
    }

    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimer");
        }

        public void actionPerformed(ActionEvent e) {
            int[] selection = tab.getSelectedRows();
            for(int i = selection.length - 1; i>= 0; i--) tableModel.removeStudent(selection[i]);
        }
    }

    private class ExportAction extends AbstractAction {
        private ExportAction() {
            super("Exporter en CSV");
        }

        public void actionPerformed(ActionEvent e) {
            Minutes.create(programs[currentProgramIndex], students);
        }
    }

    private class ScrollMenu extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            reset(programsList.getSelectedIndex());
        }
    }
}
