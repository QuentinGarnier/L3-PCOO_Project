package graphics;

import graphics.tabpanels.*;
import xmlreader.XMLReader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class: GradesManagement Window (GMWindow)
 * @author Quentin Garnier
 */

public class GMWindow extends JFrame {
    private Color colorBG = new Color(80,80,80);
    private Color colorFG = new Color(220,220,220);
    private Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    public GMWindow() {
        super();
        setup();

        //Barre de menu en haut :
        menu();

        //Différentes pages (onglets) :
        ProgramTabPanel programTabPanel = new ProgramTabPanel();
        StudentTabPanel studentTabPanel = new StudentTabPanel();
        ClassesManagementPanel classesManagementPanel = new ClassesManagementPanel();

        //Gestion des onglets :
        JTabbedPane tabs = new JTabbedPane(); //tabs regroupe tous les onglets
        tabs.setTabPlacement(JTabbedPane.TOP); //place la barre des onglets en haut
        tabs.addTab("Programmes", programTabPanel);
        tabs.addTab("Étudiants", studentTabPanel);
        tabs.addTab("Organisation des cours", classesManagementPanel);

        getContentPane().setBackground(new Color(80, 80, 80));
        getContentPane().add(tabs);
    }

    private void setup() {
        setTitle("GM - GradesManagement");
        setSize(1400,900);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setResizable(true); //true à l'avenir en adaptant les éléments à la taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void menu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorBG);
        menuBar.setBorder(border);

        JMenu menu1 = new JMenu("Fichier");
        menu1.setFont(new Font(menu1.getFont().getName(), Font.PLAIN, 16));
        menu1.setForeground(colorFG);

        JMenuItem menuSave = setupJMenuItem(new ActionSave());
        JMenuItem menuExit = setupJMenuItem(new ActionExit());

        menu1.add(menuSave);
        menu1.add(menuExit);
        menuBar.add(menu1);
        setJMenuBar(menuBar);
    }

    public void display() {
        setVisible(true);
    }

    private void clear() {
        getContentPane().removeAll();
    }

    private JMenuItem setupJMenuItem(AbstractAction action) {
        JMenuItem jMenuItem = new JMenuItem(action);
        jMenuItem.setBackground(colorBG);
        jMenuItem.setForeground(colorFG);
        jMenuItem.setFont(new Font(jMenuItem.getFont().getName(), Font.PLAIN, 16));
        jMenuItem.setPreferredSize(new Dimension(200, 30));
        jMenuItem.setBorder(border);
        return jMenuItem;
    }

    private class ActionSave extends AbstractAction {
        private ActionSave() {
            super("Sauvegarder");
        }

        public void actionPerformed(ActionEvent e) {
            XMLReader.write();
            JOptionPane.showMessageDialog(null, "Sauvegarde terminée !", "Successful save", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ActionExit extends AbstractAction {
        private ActionExit() {
            super("Quitter");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
