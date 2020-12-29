package graphics;

import program.Program;
import student.Student;
import xmlreader.XMLReader;

import javax.swing.*;
import java.awt.*;

/**
 * Class: GradesManagement Window (GMWindow)
 * @author Quentin Garnier
 */

public class GMWindow extends JFrame {
    private XMLReader data;

    //parametres = XMLReader uniquement ! à modifier quand xmlreader fini
    public GMWindow(Program[] ps, Student[] stds) {
        super();
        setup();
        //this.data = xmlReader;

        //Barre de menu en haut :
        menu(); //(fonction à modifier,juste un test)

        //Différentes pages (onglets) :
        ProgramTabPanel programTabPanel = new ProgramTabPanel(ps,stds);
        StudentTabPanel studentTabPanel = new StudentTabPanel(stds[0], ps[0].getBlocks()[0].getClasses());

        //Gestion des onglets :
        JTabbedPane tabs = new JTabbedPane(); //tabs regroupe tous les onglets
        tabs.setTabPlacement(JTabbedPane.TOP); //place la barre des onglets en haut
        tabs.addTab("Programmes", programTabPanel);
        tabs.addTab("Étudiants", studentTabPanel);
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
        menuBar.setFont(new Font("Serif", Font.PLAIN, 30));

        JMenu menu1 = new JMenu("Programmes");
        JMenuItem premier = new JMenuItem("L3 Informatique");
        menu1.add(premier);
        JMenuItem second = new JMenuItem("L3 Maths-info");
        menu1.add(second);
        JMenuItem optionPrg = new JMenuItem("Contenus des programmes");
        menu1.add(optionPrg);
        menuBar.add(menu1);

        JMenu menu2 = new JMenu("Étudiants");
        menuBar.add(menu2);

        setJMenuBar(menuBar);
    }

    public void display() {
        setVisible(true);
    }

    private void clear() {
        getContentPane().removeAll();
    }
}
