package graphics.tabpanels;

import graphics.tablemodels.StudentTableModel;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Class: JPanel Tab for a specific student
 * @author Quentin Garnier
 */

public class StudentTabPanel extends CustomTabPanel {
    private Student[] students;
    private SchoolClass[] schoolClasses;
    private StudentTableModel tableModel;
    private JTable tab;
    private int currentStudentIndex;
    private JComboBox studentScrollMenu;

    /**
     * Constructor
     * @param stds: a list of all students
     * @param sclClasses: a list of all existing classes (read from XMLReader) to get the classes' names
     */
    public StudentTabPanel(Student[] stds, SchoolClass[] sclClasses) {
        this(stds, sclClasses, -1); //-1 : par défaut, pas d'étudiant sélectionné
    }

    private StudentTabPanel(Student[] stds, SchoolClass[] sclClasses, int index) {
        super(new BorderLayout());
        create(stds, sclClasses, index);
    }

    private void create(Student[] stds, SchoolClass[] sclClasses, int index) {
        this.currentStudentIndex = index;
        this.students = stds;
        this.schoolClasses = sclClasses;
        this.tableModel = new StudentTableModel(stds[(index<0?0:index)], sclClasses);

        //TITLE & LIST (HEADER):
        studentScrollMenu = new JComboBox(stds);
        studentScrollMenu.setSelectedIndex(index); //par défaut -1 donc aucune sélection
        studentScrollMenu.addActionListener(new ScrollMenu());

        if(index < 0) title("Sélectionnez un étudiant dans la liste : ", studentScrollMenu);
        else title("[" + stds[index].getId() + "] " + stds[index] + " ", studentScrollMenu);

        //ARRAY:
        tab = new JTable(tableModel);
        createTable(tab);

        //BUTTONS:
        JButton buttonAdd = new JButton(new AddAction());
        JButton buttonRemove = new JButton(new RemoveAction());
        buttonAdd.setToolTipText("Ajouter une note à l'étudiant sélectionné");
        buttonRemove.setToolTipText("Supprimer les notes des lignes sélectionnées");

        //FOOTER:
        footer(buttonAdd, buttonRemove);
    }

    private void reset(int index) {
        removeAll();
        create(students, schoolClasses, index);
    }


    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter une note");
        }

        public void actionPerformed(ActionEvent e) {
            if(currentStudentIndex < 0) JOptionPane.showMessageDialog(null, "Veuillez choisir un étudiant avant de lui ajouter une note.", "No student selected!", JOptionPane.WARNING_MESSAGE);
            else tableModel.addGrade(new Grade(20, "1234"), new SchoolClass("TEST","1234", 1));
        }
    }

    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimer la sélection");
        }

        public void actionPerformed(ActionEvent e) {
            if(currentStudentIndex < 0) JOptionPane.showMessageDialog(null,
                    "Vous ne pouvez pas supprimer une note sans avoir sélectionné un étudiant.",
                    "No student selected!", JOptionPane.WARNING_MESSAGE);
            else {
                Object[] options = {"Supprimer", "Annuler"};
                int o = JOptionPane.showOptionDialog(null,
                        "Vous allez supprimer les lignes sélectionnées. Voulez-vous continuer ?",
                        "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);
                if (o == 0) {
                    int[] selection = tab.getSelectedRows();
                    for (int i = selection.length - 1; i >= 0; i--) tableModel.removeGrade(selection[i]);
                }
            }
        }
    }

    private class ScrollMenu extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            reset(studentScrollMenu.getSelectedIndex());
        }
    }
}
