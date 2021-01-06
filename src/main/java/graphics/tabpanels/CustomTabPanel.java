package graphics.tabpanels;

import javax.swing.*;
import java.awt.*;

/**
 * Class: super class for TabPanels
 * @author Quentin Garnier
 */

abstract class CustomTabPanel extends JPanel {
    Color buttonColor = new Color(60,100,140);
    Color fgColor = new Color(230,230,230);

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
        Color bgColor = new Color(80,80,80);
        JPanel footer = new JPanel();
        footer.setBackground(bgColor);
        if(componentsToAdd != null) for(Component c : componentsToAdd) {
            c.setForeground(fgColor);
            if (c instanceof JButton) c.setBackground(buttonColor);
            else if (c instanceof JComboBox) c.setForeground(Color.BLACK);
            footer.add(c);
        }
        this.add(footer, BorderLayout.SOUTH);
    }

    public abstract void reset();
}
