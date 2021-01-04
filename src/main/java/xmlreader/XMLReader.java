package xmlreader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import program.Program;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.TeachingUnit;
import teachingunit.block.Block;
import org.w3c.dom.NamedNodeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

/**
 * @author Julien Provillard
 *         Quentin Garnier
 */


public class XMLReader {
    private ArrayList<Program> programs;
    private ArrayList<Student> students;
    private ArrayList<SchoolClass> schoolClasses;



    public static void read(String path, String childTagName) {
        ArrayList<SchoolClass> tu = new ArrayList<SchoolClass>();
        try {

            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file); // ouverture et lecture du fichier XML
            doc.getDocumentElement().normalize(); // normalise le contenu du fichier, opération très conseillée
            Element root = doc.getDocumentElement(); // la racine de l'arbre XML

            // c'est parti pour l'exploration de l'arbre
            NodeList nList = doc.getElementsByTagName(childTagName);
            Element racine = doc.getDocumentElement();
            NodeList nl = racine.getChildNodes();
            /*NodeList nodeList = item.getChildNodes();
            public static List<Student> getStudent (Element item, String name){
                public static List<TeachingUnit> getChildren (Element item, String name){

                    if (childTagName == "course") {
                        List<TeachingUnit> test = getChildren(root, childTagName);
                        System.out.println(test);
                    } else if (childTagName == "student") {
                        List<Student> test = getStudent(root, childTagName);
                        System.out.println(test);
                    }
                    else if (childTagName == "program") {
                        List<Program> test = getProgram(root, childTagName);
                        System.out.println(test);
                }
            }*/
            switch (childTagName) {
                case "course":

                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp); //permet de créer le noeud
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            String cod = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                            String nam = eElement.getElementsByTagName("name").item(0).getTextContent();
                            int credits = Integer.parseInt(eElement.getElementsByTagName("credits").item(0).getTextContent());
                            System.out.println(cod + nam + credits);
                            tu.add(new SchoolClass(cod, nam, credits));

                        }
                    }
                    break;

                case "student": //pas complet -> manque grade
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;

                            ArrayList<Student> stds = new ArrayList<Student>();
                            int id = Integer.parseInt(eElement.getElementsByTagName("identifier").item(0).getTextContent());
                            String firstname = eElement.getElementsByTagName("name").item(0).getTextContent();
                            String name = eElement.getElementsByTagName("surname").item(0).getTextContent();
                            String prog = eElement.getElementsByTagName("program").item(0).getTextContent();
                            System.out.println(id + firstname + name + prog);
                            stds.add(new Student(id, firstname, name));


                            //on va chercher à récupérer tous les identifiants de tous les cours pour que l'étudiant
                            //ait toutes les notes de son programme (donc une note par cours dont il s'est inscrit)
                            //et non un seul cours comme nous avons là



                            String code = "";
                            String items = "";

                            Node grade = eElement.getElementsByTagName("grade").item(0);
                            NodeList sousNoeudDeGrade = grade.getChildNodes(); //On récupère tous les enfants directs de grade

                            String cod = eElement.getElementsByTagName("item").item(0).getTextContent();
                            ArrayList<String> listeDesNotes = new ArrayList<String>();                   //La liste qui va stocker toutes les notes en chaines de caractères
                            for (int i = 0; i < sousNoeudDeGrade.getLength(); i++) {

                                //System.out.println(cod);
                                //for (int j = 0; j < stds.size(); j++){
                                //System.out.println("item : " + cod);
                                listeDesNotes.add(sousNoeudDeGrade.item(i).getTextContent() + cod);
                                //tu.add(new SchoolClass(cod, );

                                //}
                                System.out.println("Notes : " + listeDesNotes);
                            }

                            }

                        }
                            
                        break;

                /*case "program": //bloc composite, options, simples
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        System.out.println("-------Program  " + temp + "-----------------------");
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            ArrayList<Program> Pr = new ArrayList<Program>();
                            String code = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                            System.out.println(code + name);
                            Pr.add(new Program(code, name));*/

                case "program":
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;

                            ArrayList<Program> Pr = new ArrayList<Program>();
                            String id = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                            String item = eElement.getElementsByTagName("item").item(0).getTextContent();
                            System.out.println(id + name);
                            Pr.add(new Program(id, name));

                            String identifier = "";
                            String name1 = "";
                            String item1 = "";
                            String item2 = "";

                            Node option = eElement.getElementsByTagName("option").item(0);
                            Node composite = eElement.getElementsByTagName("composite").item(0);

                            NodeList sousNoeudDeOption = option.getChildNodes(); //On récupère tous les enfants directs de grade
                            NodeList sousNoeudDeComposite = composite.getChildNodes(); //On récupère tous les enfants directs de grade

                            ArrayList<String> listeOptions = new ArrayList<String>();
                            ArrayList<String> listeComposites = new ArrayList<String>();

                            //La liste qui va stocker toutes les notes en chaines de caractères
                            for (int i = 0; i < sousNoeudDeOption.getLength(); i++) {
                                listeOptions.add(sousNoeudDeOption.item(i).getTextContent());
                                listeComposites.add(sousNoeudDeComposite.item(i).getTextContent());
                            }
                            System.out.println("Options : " + listeOptions);
                            System.out.println("Composites : " + listeComposites);



                        }

                    }

            }
        }
        catch (Exception e) {
            {
                System.err.println("Error: reading XML file failed.");
            }
        }
    }
}
        class main {
            public static void main(String[] args) {

                XMLReader.read("data/data.xml", "course");
                XMLReader.read("data/data.xml", "student");
                XMLReader.read("data/data.xml", "program");
            }
        }