package graphics.tabpanels;

import graphics.tablemodels.ProgramTableModel;
import minutes.Minutes;
import program.Program;
import student.Student;
import teachingunit.block.Block;
import xmlreader.XMLReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Class: JPanel Tab for a specific program
 * @author Quentin Garnier
 */

public class ProgramTabPanel extends CustomTabPanel {
    private int currentProgramIndex;
    private JComboBox programsList;
    private ArrayList<JCheckBox> checkBoxes;

    public ProgramTabPanel() {
        super(new BorderLayout());
        create(0);
    }

    private void create(int defaultIndex) {
        create(defaultIndex, null);
    }

    private void create(int defaultIndex, boolean[] modelIndexes) {
        this.currentProgramIndex = defaultIndex; //par défaut
        ProgramTableModel tableModel = new ProgramTableModel(XMLReader.getPrograms().get(currentProgramIndex), modelIndexes, XMLReader.getStudents().toArray(new Student[0]));
        Program program = XMLReader.getPrograms().get(currentProgramIndex);

        //TITLE:
        title("Programme : " + program.getName());

        //ARRAY:
        JPanel tabWithFilters = new JPanel(new BorderLayout());
        JPanel filters = new JPanel(new GridLayout(3,0));
        Color bg = new Color(220,220,220);
        filters.setBackground(bg);
        JTable tab = new JTable(tableModel);

        checkBoxes = new ArrayList<JCheckBox>();
        if(program.getBlocks() != null) {
            JCheckBox cbAll = new JCheckBox(new CheckBoxAction(0, "Sélectionner tout"));
            cbAll.setBackground(bg);
            cbAll.setForeground(new Color(50,90,180));
            boolean isTrue = true;
            if (modelIndexes != null) for (boolean b: modelIndexes) if(!b) {
                isTrue = false;
                break;
            }
            cbAll.setSelected(isTrue);
            checkBoxes.add(cbAll);

            int i = 1;
            for (Block b: program.getBlocks()) {
                JCheckBox jcb = new JCheckBox(new CheckBoxAction(i, b.getName()));
                jcb.setBackground(bg);
                if (modelIndexes == null) jcb.setSelected(true);
                else jcb.setSelected(modelIndexes[i-1]);
                checkBoxes.add(jcb);
                i++;
            }
            for(JCheckBox j: checkBoxes) filters.add(j);
        }

        if(program.getBlocks() != null) tabWithFilters.add(filters, BorderLayout.NORTH);
        tabWithFilters.add(createTable(tab, true), BorderLayout.CENTER);
        this.add(tabWithFilters, BorderLayout.CENTER);

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

    private void reset(int index, boolean[] modelIndexes) {
        removeAll();
        create(index, modelIndexes);
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

    private class CheckBoxAction extends AbstractAction {
        private int i;

        private CheckBoxAction(int index, String name) {
            super(name);
            this.i = index;
        }

        public void actionPerformed(ActionEvent e) {
            if (i == 0) for (int j = 1; j < checkBoxes.size(); j++) {
                checkBoxes.get(j).setSelected(checkBoxes.get(0).isSelected());
            }
            boolean[] indexes;
            try {
                indexes = new boolean[XMLReader.getPrograms().get(currentProgramIndex).getBlocks().length];
                for(int k=0; k<indexes.length; k++) indexes[k] = checkBoxes.get(k+1).isSelected();
            } catch(NullPointerException exception) {
                indexes = null;
            }
            reset(currentProgramIndex, indexes);
        }
    }
}
