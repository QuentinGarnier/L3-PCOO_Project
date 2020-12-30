package graphics.tabpanels;

import graphics.tablemodels.StudentTableModel;
import student.Student;
import teachingunit.SchoolClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class: JPanel Tab for a specific student
 * @author Quentin Garnier
 */

public class StudentTabPanel extends CustomTabPanel {
    private StudentTableModel tableModel;
    private JTable tab;

    /**
     * Constructor
     * @param student: a specific student
     * @param schoolClasses: a list of all existing classes (read from XMLReader) to get the classes' names
     */
    public StudentTabPanel(Student[] student, SchoolClass[] schoolClasses) {
        super(new BorderLayout());
        tableModel = new StudentTableModel();

        //TITLE:
        title("[TITLE HERE]");

        //ARRAY:
        tab = new JTable(tableModel);
        createTable(tab);

        //BUTTONS:
        JButton buttonAdd = new JButton(new AddAction());
        JButton buttonRemove = new JButton(new RemoveAction());
        buttonAdd.setToolTipText("Ajouter un étudiant");
        buttonRemove.setToolTipText("Supprimer les lignes sélectionnées");

        //FOOTER:
        footer(buttonAdd, buttonRemove);
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
            Object[] options = {"Supprimer","Annuler"};
            int o = JOptionPane.showOptionDialog(null,
                    "Vous allez supprimer les lignes sélectionnées. Voulez-vous continuer ?",
                    "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[1]);
            if(o == 0) {
                int[] selection = tab.getSelectedRows();
                for (int i = selection.length - 1; i >= 0; i--) tableModel.removeStudent(selection[i]);
            }
        }
    }
}
