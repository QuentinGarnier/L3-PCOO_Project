package graphics.tabpanels;

import graphics.tablemodels.StudentTableModel;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        this.tableModel = new StudentTableModel((index < 0? null: stds[index]), sclClasses);

        //TITLE & LIST (HEADER):
        studentScrollMenu = new JComboBox(stds);
        studentScrollMenu.setSelectedIndex(index); //par défaut -1 donc aucune sélection
        studentScrollMenu.addActionListener(new ScrollMenu());

        if(index < 0) title("Sélectionnez un étudiant dans la liste : ", studentScrollMenu);
        else title("[" + stds[index].getId() + "] " + stds[index] + " ", studentScrollMenu);

        //ARRAY:
        tab = new JTable(tableModel);
        createTable(tab, false);

        //BUTTONS:
        JButton buttonAdd = new JButton(new AddAction());
        JButton buttonModify = new JButton(new ModifyAction());
        JButton buttonRemove = new JButton(new RemoveAction());
        buttonAdd.setToolTipText("Ajouter une note à l'étudiant sélectionné");
        buttonModify.setToolTipText("Modifier une note de l'étudiant sélectionné");
        buttonRemove.setToolTipText("Supprimer les notes des lignes sélectionnées");

        //FOOTER:
        footer(buttonAdd, buttonModify, buttonRemove);
    }

    private void reset(int index) {
        removeAll();
        create(students, schoolClasses, index);
    }

    /**
     * For the classes 'AddAction' and 'ModifyAction' below.
     * @param addNotModify is true for addButton and false for modifyButton.
     */
    private void createPopup(boolean addNotModify) {
        JPanel popup = new JPanel(new GridLayout(0, 1));
        JLabel text = new JLabel(addNotModify ? "Entrez une nouvelle note :" : "Modifier la note sélectionnée :");
        JTextField nbField = new JTextField();
        JComboBox classesList = new JComboBox(schoolClasses);
        JCheckBox checkBox = new JCheckBox("ABI");
        popup.add(text);
        popup.add(nbField);
        if (addNotModify) popup.add(classesList);
        popup.add(checkBox);
        int result = JOptionPane.showConfirmDialog(null, popup, addNotModify ? "Add grade" : "Modify grade",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nbStr;
                if (checkBox.isSelected() && nbField.getText().equals("")) nbStr = "0"; //met à 0 afin de ne pas provoquer d'erreur
                else nbStr = nbField.getText().replaceAll(",", "."); //permet d'écrire (par exemple) 0,5 comme 0.5
                double nb = Double.parseDouble(nbStr);
                if (nb < 0 || nb > 20) errorInputPopup();
                else {
                    SchoolClass cl = schoolClasses[classesList.getSelectedIndex()];
                    if (addNotModify) tableModel.addGrade(new Grade((checkBox.isSelected() ? -1 : nb), cl.getCode()), cl);
                    else tableModel.modifyGrade(tab.getSelectedRow(), nb, checkBox.isSelected());
                }
            } catch (NumberFormatException e) {
                errorInputPopup();
            }
        }
    }

    private void errorInputPopup() {
        JOptionPane.showMessageDialog(null,
                "Erreur : valeur entrée incorrecte. Veuillez indiquer un nombre entre 0 et 20.",
                "Error: not a number", JOptionPane.WARNING_MESSAGE);
    }



    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter une note");
        }

        public void actionPerformed(ActionEvent e) {
            if(currentStudentIndex < 0) JOptionPane.showMessageDialog(null, "Veuillez choisir un étudiant avant de lui ajouter une note.", "No student selected!", JOptionPane.WARNING_MESSAGE);
            else addGradePopup();
        }

        private void addGradePopup() {
            createPopup(true);
        }
    }


    private class ModifyAction extends AbstractAction {
        private ModifyAction() {
            super("Modifier une note");
        }

        public void actionPerformed(ActionEvent e) {
            if(currentStudentIndex < 0 || tab.getSelectedRows().length == 0) JOptionPane.showMessageDialog(null,
                    "Vous devez sélectionner une note pour pouvoir la modifier.",
                    "No grade selected!", JOptionPane.WARNING_MESSAGE);
            else if(tab.getSelectedRows().length > 1) JOptionPane.showMessageDialog(null,
                    "Vous ne pouvez pas modifier plusieurs notes en même temps.",
                    "Too many grades selected!", JOptionPane.WARNING_MESSAGE);
            else modifyGradePopup();
        }

        private void modifyGradePopup() {
            createPopup(false);
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
