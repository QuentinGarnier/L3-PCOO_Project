package graphics.tabpanels;

import program.Program;
import teachingunit.SchoolClass;
import teachingunit.block.Block;
import teachingunit.block.SimpleBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Class: JPanel Tab to have a view of all courses.
 * @author Quentin Garnier
 */

public class ClassesManagementPanel extends CustomTabPanel {
    private Program[] programs;

    /**
     * Constructor
     * @param prgs: a list of all programs.
     */
    public ClassesManagementPanel(Program[] prgs) {
        super(new BorderLayout());
        this.programs = prgs;
        create();
    }

    private void create() {
        //TITLE:
        title("Structure des programmes");

        //BODY:
        createHierarchy(programs);

        //BUTTONS:
        JButton buttonAddProg = new JButton(new AddProgram());
        buttonAddProg.setToolTipText("Ajouter un nouveau programme");
        JButton buttonAddClass = new JButton(new AddClass());
        buttonAddClass.setToolTipText("Ajouter un cours à un programme");

        //FOOTER:
        footer(buttonAddProg, buttonAddClass);
    }

    private void createHierarchy(Program[] ps) {
        JPanel jPanel = new JPanel();
        JScrollPane scroll = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JLabel lines = new JLabel();
        lines.setFont(new Font("Serif", Font.BOLD, 18));
        String linesTxt = "<html><body>";

        for(Program p : ps) {
            linesTxt += "<p><u>" + p.toString() + "</u><br /><br />";
            for(Block b : p.getBlocks()) {
                linesTxt += "<div STYLE=\"padding-left: 50px;\"> &gt; " + b.toString() + "</div><br />";
                if (!(b instanceof SimpleBlock)) for (SchoolClass scl : b.getClasses())
                    linesTxt += "<div STYLE=\"padding-left: 100px;\"> &gt; " + scl.toString() + "</div><br />";
            }
            linesTxt += "</p><br /><br />";
        }
        lines.setText(linesTxt + "</body></html>");

        jPanel.add(lines);
        this.add(scroll);
    }

    private class AddProgram extends AbstractAction {
        private AddProgram() {
            super("Ajouter un programme");
        }

        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AddClass extends AbstractAction {
        private AddClass() {
            super("Ajouter un cours");
        }

        public void actionPerformed(ActionEvent e) {

        }
    }
}
