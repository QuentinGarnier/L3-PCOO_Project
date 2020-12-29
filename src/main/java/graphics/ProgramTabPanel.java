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
    private Program currentProgram;
    private Student[] students;

    /**
     * Constructor
     * @param prgs: a list of programs
     * @param stds: a list of all students to get their data
     */
    ProgramTabPanel(Program[] prgs, Student[] stds) {
        super(new BorderLayout());
        this.programs = prgs;
        this.students = stds;
        this.currentProgram = this.programs[0];
        this.tableModel = new ProgramTableModel(currentProgram, this.students);

        //TITLE:
        title("Programme : " + currentProgram.getName());

        //ARRAY:
        tab = new JTable(tableModel);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setRowHeight(30);
        for(int i=0; i<tab.getModel().getColumnCount(); i++) tab.getColumnModel().getColumn(i).setMinWidth(180);
        JScrollPane scroll = new JScrollPane(tab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll, BorderLayout.CENTER);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        //BUTTONS:
        JPanel buttons = new JPanel();
        JButton buttonAdd = new JButton(new AddAction());
        JButton buttonRemove = new JButton(new RemoveAction());
        JButton buttonExport = new JButton(new ExportAction());

        buttonAdd.setToolTipText("Ajouter un étudiant");
        buttonRemove.setToolTipText("Supprimer les lignes sélectionnées");
        buttonExport.setToolTipText("Exporter en format .csv");

        buttons.add(buttonAdd);
        buttons.add(buttonRemove);
        buttons.add(buttonExport);
        this.add(buttons, BorderLayout.SOUTH);
    }

    private void title(String txt) {
        JPanel titleP = new JPanel();
        JLabel titleL = new JLabel(txt, JLabel.CENTER);
        titleL.setFont(new Font("Serif", Font.BOLD, 32));
        titleP.add(titleL);
        this.add(titleP, BorderLayout.NORTH);
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
            Minutes.create(currentProgram, students);
        }
    }
}
