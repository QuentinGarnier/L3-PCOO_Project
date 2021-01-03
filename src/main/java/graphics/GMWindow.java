package graphics;

import graphics.tabpanels.*;
import program.Program;
import student.Student;
import teachingunit.SchoolClass;
import xmlreader.XMLReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class: GradesManagement Window (GMWindow)
 * @author Quentin Garnier
 */

public class GMWindow extends JFrame {
    private XMLReader data;

    //parametres = XMLReader uniquement ! à modifier quand xmlreader fini
    public GMWindow(Program[] psArray, Student[] stds, SchoolClass[] schoolClasses) {
        super();
        setup();
        //this.data = xmlReader;
        ArrayList<Program> ps = new ArrayList<Program>(); //à supprimer quand XMLReader prêt !
        for(Program p : psArray) ps.add(p);

        //Barre de menu en haut :
        menu(); //(fonction à modifier,juste un test)

        //Différentes pages (onglets) :
        ProgramTabPanel programTabPanel = new ProgramTabPanel(ps, stds);
        StudentTabPanel studentTabPanel = new StudentTabPanel(stds, schoolClasses);
        ClassesManagementPanel classesManagementPanel = new ClassesManagementPanel(ps);

        //Gestion des onglets :
        JTabbedPane tabs = new JTabbedPane(); //tabs regroupe tous les onglets
        tabs.setTabPlacement(JTabbedPane.TOP); //place la barre des onglets en haut
        tabs.addTab("Programmes", programTabPanel);
        tabs.addTab("Étudiants", studentTabPanel);
        tabs.addTab("Organisation des cours", classesManagementPanel);
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
