package graphics.tabpanels;

import graphics.tablemodels.StudentTableModel;
import program.Program;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import xmlreader.XMLReader;

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
    private int currentStudentIndex;
    private JComboBox studentScrollMenu;

    public StudentTabPanel() {
        this(-1); //-1 : par défaut, pas d'étudiant sélectionné
    }

    private StudentTabPanel(int index) {
        super(new BorderLayout());
        create(index);
    }

    private void create(int index) {
        this.currentStudentIndex = index;
        this.tableModel = new StudentTableModel((index < 0? null: XMLReader.getStudents().get(index)), XMLReader.getSchoolClasses());

        //TITLE & LIST (HEADER):
        studentScrollMenu = new JComboBox(XMLReader.getStudents().toArray(new Student[0]));
        studentScrollMenu.setSelectedIndex(index); //par défaut -1 donc aucune sélection
        studentScrollMenu.addActionListener(new ScrollMenu());

        if(index < 0) title("Sélectionnez un étudiant dans la liste : ", studentScrollMenu);
        else title("[" + XMLReader.getStudents().get(index).getId() + "] " + XMLReader.getStudents().get(index) + " ", studentScrollMenu);

        //BODY (ARRAY & UP BUTTONS):
        JPanel body = new JPanel(new BorderLayout());
        JButton buttonAddStudent = new JButton(new AddStudentAction());
        buttonAddStudent.setToolTipText("Ajouter un nouvel étudiant");
        JButton buttonRemoveStudent = new JButton(new RemoveStudentAction());
        buttonRemoveStudent.setToolTipText("Supprimer l'étudiant sélectionné");
        JPanel buttonBar = new JPanel();
        buttonBar.add(buttonAddStudent);
        buttonBar.add(new JLabel(" | "));
        buttonBar.add(buttonRemoveStudent);
        buttonBar.setBackground(new Color(220,220,220));
        body.add(buttonBar, BorderLayout.NORTH);

        tab = new JTable(tableModel);
        body.add(createTable(tab, false), BorderLayout.CENTER);
        this.add(body);

        //BUTTONS (FOOTER):
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
        create(index);
    }

    /**
     * For the classes 'AddAction' and 'ModifyAction' below.
     * @param addNotModify is true for addButton and false for modifyButton.
     */
    private void createPopup(boolean addNotModify) {
        JPanel popup = new JPanel(new GridLayout(0, 1));
        JLabel text = new JLabel(addNotModify ? "Entrez une nouvelle note :" : "Modifier la note sélectionnée :");
        JTextField nbField = new JTextField();
        JComboBox classesList = new JComboBox(XMLReader.getSchoolClasses().toArray(new SchoolClass[0]));
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
                    SchoolClass cl = XMLReader.getSchoolClasses().get(classesList.getSelectedIndex());
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

    private void addStudentPopup() {
        JPanel popup = new JPanel(new GridLayout(0, 1));
        JLabel firstnameLabel = new JLabel("Prénom :");
        JTextField firstnameField = new JTextField();
        JLabel nameLabel = new JLabel("Nom :");
        JTextField nameField = new JTextField();
        JLabel idLabel = new JLabel("Numéro étudiant :");
        JTextField idField = new JTextField();
        JLabel programLabel = new JLabel("Programme (laissez vide si aucun) :");
        JComboBox programsList = new JComboBox(XMLReader.getPrograms().toArray(new Program[0]));
        programsList.setSelectedIndex(-1); //par défaut aucun programme sélectionné
        popup.add(firstnameLabel);
        popup.add(firstnameField);
        popup.add(nameLabel);
        popup.add(nameField);
        popup.add(idLabel);
        popup.add(idField);
        popup.add(programLabel);
        popup.add(programsList);
        int result = JOptionPane.showConfirmDialog(null, popup,"Add student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                if (firstnameField.getText().length() == 0 || nameField.getText().length() == 0)
                    JOptionPane.showMessageDialog(null,
                        "Erreur : un des champs requis est vide.",
                        "Error: missing fields", JOptionPane.WARNING_MESSAGE);
                else {
                    Student student = new Student(id, firstnameField.getText(), nameField.getText());
                    int i = programsList.getSelectedIndex();
                    if(i > -1) student.setProgram(XMLReader.getPrograms().get(i));
                    XMLReader.addStudent(student);
                    reset(currentStudentIndex);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Erreur : numéro étudiant incorrect.",
                        "Error: not a number", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void removeStudentPopup() {
        if (currentStudentIndex < 0)
            JOptionPane.showMessageDialog(null,
                    "Vous devez d'abord sélectionner un étudiant.",
                    "No student selected", JOptionPane.WARNING_MESSAGE);
        else {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "ATTENTION : l'action est définitive. Souhaitez-vous continuer ?",
                    "Confirm deletion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.OK_OPTION) {
                XMLReader.removeStudent(currentStudentIndex);
                JOptionPane.showMessageDialog(null,
                        "L'étudiant à été supprimé.",
                        "Deletion success", JOptionPane.INFORMATION_MESSAGE);
                reset(-1);
            }
        }
    }



    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter une note");
        }

        public void actionPerformed(ActionEvent e) {
            if(currentStudentIndex < 0) JOptionPane.showMessageDialog(null, "Veuillez choisir un étudiant avant de lui ajouter une note.", "No student selected", JOptionPane.WARNING_MESSAGE);
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

    private class AddStudentAction extends AbstractAction {
        private AddStudentAction() {
            super("Ajouter un étudiant");
        }

        public void actionPerformed(ActionEvent e) {
            addStudentPopup();
            reset(studentScrollMenu.getSelectedIndex());
        }
    }

    private class RemoveStudentAction extends AbstractAction {
        private RemoveStudentAction() {
            super("Supprimer l'étudiant");
        }

        public void actionPerformed(ActionEvent e) {
            removeStudentPopup();
            reset(studentScrollMenu.getSelectedIndex());
        }
    }

    private class ScrollMenu extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            reset(studentScrollMenu.getSelectedIndex());
        }
    }
}
