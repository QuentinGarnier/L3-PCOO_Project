package graphics.tabpanels;

import javax.swing.*;
import java.awt.*;

/**
 * Class: super class for TabPanels
 * @author Quentin Garnier
 */

abstract class CustomTabPanel extends JPanel {
    CustomTabPanel(BorderLayout borderLayout) {
        super(borderLayout);
    }

    CustomTabPanel() {
        super();
    }

    void title(String txt, int size, Component... componentsToAdd) {
        JPanel titleP = new JPanel();
        JLabel titleL = new JLabel(txt, JLabel.CENTER);
        titleL.setFont(new Font("Serif", Font.BOLD, size));
        titleP.add(titleL);
        if(componentsToAdd != null) for(Component c : componentsToAdd) titleP.add(c);
        this.add(titleP, BorderLayout.NORTH);

    }

    void title(String txt, Component... componentsToAdd) {
        title(txt, 32, componentsToAdd);
    }

    JComponent createTable(JTable jTable, boolean fixSizes) {
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setRowHeight(30);
        if(fixSizes) {
            jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i=0; i<jTable.getModel().getColumnCount(); i++) jTable.getColumnModel().getColumn(i).setMinWidth(140);
        }
        JScrollPane scroll = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scroll;
    }

    void footer(Component... componentsToAdd) {
        JPanel footer = new JPanel();
        if(componentsToAdd != null) for(Component c : componentsToAdd) footer.add(c);
        this.add(footer, BorderLayout.SOUTH);
    }

    public abstract void reset();
}
