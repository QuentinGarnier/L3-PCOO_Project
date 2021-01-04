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

    public GMWindow() {
        super();
        setup();

        //Barre de menu en haut :
        menu();

        //Différentes pages (onglets) :
        ProgramTabPanel programTabPanel = new ProgramTabPanel(XMLReader.getPrograms(), XMLReader.getStudents());
        StudentTabPanel studentTabPanel = new StudentTabPanel(XMLReader.getStudents(), XMLReader.getSchoolClasses());
        ClassesManagementPanel classesManagementPanel = new ClassesManagementPanel(XMLReader.getPrograms());

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
        Color colorBG = new Color(80,80,80);
        Color colorFG = new Color(220,220,220);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorBG);
        menuBar.setBorder(border);

        JMenu menu1 = new JMenu("Fichier");
        menu1.setFont(new Font(menu1.getFont().getName(), Font.PLAIN, 16));
        menu1.setForeground(colorFG);

        JMenuItem premier = new JMenuItem(new ActionExit());
        premier.setBackground(colorBG);
        premier.setForeground(colorFG);
        premier.setFont(new Font(premier.getFont().getName(), Font.PLAIN, 16));
        premier.setPreferredSize(new Dimension(200, 30));
        premier.setBorder(border);

        menu1.add(premier);
        menuBar.add(menu1);
        setJMenuBar(menuBar);
    }

    private class ActionExit extends AbstractAction {
        private ActionExit() {
            super("Quitter");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public void display() {
        setVisible(true);
    }

    private void clear() {
        getContentPane().removeAll();
    }
}
