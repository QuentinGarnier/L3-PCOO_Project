package graphics.tabpanels;

import program.Program;
import teachingunit.SchoolClass;
import teachingunit.block.Block;
import teachingunit.block.SimpleBlock;
import xmlreader.XMLReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Class: JPanel Tab to have a view of all courses.
 * @author Quentin Garnier
 */

public class ClassesManagementPanel extends CustomTabPanel {
    private ArrayList<Program> programs;

    /**
     * Constructor
     * @param prgs: a list of all programs.
     */
    public ClassesManagementPanel(ArrayList<Program> prgs) {
        super(new BorderLayout());
        create(prgs);
    }

    private void create(ArrayList<Program> prgs) {
        this.programs = prgs;

        //TITLE:
        title("Structure des programmes");

        //BODY:
        createHierarchy(prgs);

        //BUTTONS:
        JButton buttonAddProg = new JButton(new AddProgram());
        buttonAddProg.setToolTipText("Ajouter un nouveau programme");
        JButton buttonAddClass = new JButton(new AddClass());
        buttonAddClass.setToolTipText("Ajouter un cours à un programme");

        //FOOTER:
        footer(buttonAddProg, buttonAddClass);
    }

    private void createHierarchy(ArrayList<Program> ps) {
        JPanel jPanel = new JPanel();
        JScrollPane scroll = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel lines = new JLabel();
        lines.setFont(new Font("Serif", Font.BOLD, 18));
        String linesTxt = "<html><body>";

        for(Program p : ps) {
            linesTxt += "<p><u>" + p.toString() + "</u><br /><br />";
            if(p.getBlocks() != null) for (Block b : p.getBlocks()) {
                linesTxt += "<div STYLE=\"padding-left: 50px;\"> &gt; " + b.toString() + "</div><br />";
                if (!(b instanceof SimpleBlock)) for (SchoolClass scl : b.getClasses())
                    linesTxt += "<div STYLE=\"padding-left: 100px;\"> &gt; " + scl.toString() + "</div><br />";
            }

            linesTxt += "</p><br /><br />";
        }
        lines.setText(linesTxt + "</body></html>");

        jPanel.add(lines);
        this.add(scroll, BorderLayout.CENTER);
    }

    private void reset() {
        removeAll();
        create(XMLReader.getPrograms());
    }

    /**
     * For the classes 'AddAction' and 'ModifyAction' below.
     * @param programNotClass is true for AddProgramButton and false for AddClassButton.
     */
    private void createPopup(boolean programNotClass) {
        String progOrClass = (programNotClass? "programme": "cours");
        JPanel popup = new JPanel(new GridLayout(0, 1));
        JLabel textName = new JLabel("Nom du "+ progOrClass + " :");
        JTextField nameField = new JTextField();
        JLabel textCode = new JLabel("Code du " + progOrClass + " :");
        JTextField codeField = new JTextField();
        JLabel warning = new JLabel("<html><br />ATTENTION : vous ne pourrez pas supprimer le " + progOrClass + " créé.</html>");
        popup.add(textName);
        popup.add(nameField);
        popup.add(textCode);
        popup.add(codeField);
        JLabel textCredits = new JLabel("Nombre de crédits :");
        JSpinner creditsField = new JSpinner(new SpinnerNumberModel(0,0,100,1));
        JLabel progListLabel = new JLabel("Programme associé :");
        JComboBox programsList = new JComboBox(programs.toArray(new Program[0]));
        if(!programNotClass) {
            popup.add(textCredits);
            popup.add(creditsField);
            popup.add(progListLabel);
            popup.add(programsList);
        }
        popup.add(warning);

        int result = JOptionPane.showConfirmDialog(null, popup, "Add " + (programNotClass? "program": "class"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "ATTENTION : vous ne pourrez pas supprimer le " + progOrClass + " créé. Souhaitez-vous continuer ?",
                    "Confirm creation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.OK_OPTION) {
                if (nameField.getText().length() == 0 || codeField.getText().length() == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Erreur : un des champs est vide.",
                            "Error: empty field", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if(programNotClass) {
                        XMLReader.addProgram(new Program(nameField.getText(), codeField.getText()));
                    } else {
                        int credits = (Integer) creditsField.getValue();
                        SchoolClass course = new SchoolClass(nameField.getText(), codeField.getText(), credits);
                        XMLReader.addCourse(course);
                        programs.get(programsList.getSelectedIndex()).addClass(course);
                    }
                    reset();
                }
            }
        }
    }

    private class AddProgram extends AbstractAction {
        private AddProgram() {
            super("Ajouter un programme");
        }

        public void actionPerformed(ActionEvent e) {
            createPopup(true);
        }

    }

    private class AddClass extends AbstractAction {
        private AddClass() {
            super("Ajouter un cours");
        }

        public void actionPerformed(ActionEvent e) {
            createPopup(false);
        }
    }
}
