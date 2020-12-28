package graphics;

import student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class: GradesManagement Window (GMWindow)
 * @author Quentin Garnier
 */

public class GMWindow extends JFrame {
    private TabModel tabModel = new TabModel();
    private JTable tab;

    public GMWindow() {
        super();
        setup();

        //Titre :
        JPanel titleP = new JPanel();
        JLabel titleL = new JLabel("Étudiants", JLabel.CENTER);
        titleL.setFont(size(32, Font.BOLD));
        titleP.add(titleL);
        getContentPane().add(titleP, BorderLayout.NORTH);

        //Menu :
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(size(30, Font.PLAIN));

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

        //Tableau :
        tab = new JTable(tabModel);
        tab.getTableHeader().setReorderingAllowed(false);
        for(int i=0; i<tab.getModel().getColumnCount(); i++) tab.getColumnModel().getColumn(i).setMinWidth(180);
        JScrollPane scroll = new JScrollPane(tab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scroll, BorderLayout.CENTER);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Boutons "Ajouter" et "Supprimer" :
        JPanel buttons = new JPanel();
        buttons.add(new JButton(new AddAction()));
        buttons.add(new JButton(new RemoveAction()));
        getContentPane().add(buttons, BorderLayout.SOUTH);


    }

    private void setup() {
        setTitle("GM - GradesManagement");
        setSize(1400,900);
        setMinimumSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setResizable(true); //true à l'avenir en adaptant les éléments à la taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void display() {
        setVisible(true);
    }

    private Font size(int x, int fontstyle) {
        return new Font("Serif", fontstyle, x);
    }

    private class AddAction extends AbstractAction {
        private AddAction() {
            super("Ajouter");
        }

        public void actionPerformed(ActionEvent e) {
            tabModel.addStudent(new Student(21604250, "Emma", "Kitty"));
        }
    }

    private class RemoveAction extends AbstractAction {
        private RemoveAction() {
            super("Supprimer");
        }

        public void actionPerformed(ActionEvent e) {
            int[] selection = tab.getSelectedRows();
            for(int i = selection.length - 1; i>= 0; i--) tabModel.removeStudent(selection[i]);
        }
    }
}
