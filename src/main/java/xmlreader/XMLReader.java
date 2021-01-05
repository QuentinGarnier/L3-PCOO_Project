package xmlreader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import program.Program;
import student.Student;
import teachingunit.Grade;
import teachingunit.SchoolClass;
import teachingunit.block.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julien Provillard
 *         Quentin Garnier
 *         Léa Bloom
 */


public class XMLReader {
    private static ArrayList<Program> programs = new ArrayList<Program>();
    private static ArrayList<Student> students = new ArrayList<Student>();
    private static ArrayList<SchoolClass> schoolClasses = new ArrayList<SchoolClass>();


    public static void read(String path) {
        try {

            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file); // ouverture et lecture du fichier XML
            doc.getDocumentElement().normalize(); // normalise le contenu du fichier, opération très conseillée
            Element root = doc.getDocumentElement(); // la racine de l'arbre XML

            // c'est parti pour l'exploration de l'arbre
            List<Element> rootChildren;

            for (int indexTagName = 0; indexTagName < 3; indexTagName++) {
                switch (indexTagName) {
                    case 0:
                        //Cas "course" (1ere itération) :
                        rootChildren = getChildren(root, "course");
                        for (Element eElement : rootChildren) {
                            String cod = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                            String nam = eElement.getElementsByTagName("name").item(0).getTextContent();
                            int credits = Integer.parseInt(eElement.getElementsByTagName("credits").item(0).getTextContent());
                            schoolClasses.add(new SchoolClass(nam, cod, credits));
                        }
                        break;

                    case 1:
                        //Cas "program" (3e itération) :
                        rootChildren = getChildren(root, "program");
                        for (Element eElement : rootChildren) {

                            String id = eElement.getElementsByTagName("identifier").item(0).getTextContent();
                            String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                            Program program = new Program(name, id);

                            List<Element> items = getChildren(eElement, "item");
                            List<Element> options = getChildren(eElement, "option");
                            List<Element> composites = getChildren(eElement, "composite");

                            ArrayList<Block> blocks = new ArrayList<Block>();

                            //Ajout des blocs simples :
                            for (Element e : items) {
                                String eCode = e.getTextContent();
                                for (SchoolClass scl : schoolClasses)
                                    if (scl.getCode().equals(eCode)) blocks.add(new SimpleBlock(scl));
                                ;
                            }

                            //Ajout des blocs à options :
                            for (Element e : options) {
                                String eCode = e.getElementsByTagName("identifier").item(0).getTextContent();
                                String eName = e.getElementsByTagName("name").item(0).getTextContent();
                                List<Element> eItems = getChildren(e, "item");
                                ArrayList<SchoolClass> eCoursesList = new ArrayList<SchoolClass>();
                                for (Element eItem : eItems) {
                                    String eItemCode = eItem.getTextContent();
                                    for (SchoolClass scl : schoolClasses)
                                        if (scl.getCode().equals(eItemCode)) eCoursesList.add(scl);
                                }
                                int nbCredits = eCoursesList.get(0).getNbCredits();
                                OptionsBlock newBlock = new OptionsBlock(eName, eCode, eCoursesList.toArray(new SchoolClass[0]), nbCredits);
                                blocks.add(newBlock);
                            }

                            //Ajout des blocs composites :
                            for (Element e : composites) {
                                String eCode = e.getElementsByTagName("identifier").item(0).getTextContent();
                                String eName = e.getElementsByTagName("name").item(0).getTextContent();
                                List<Element> eItems = getChildren(e, "item");
                                ArrayList<SchoolClass> eCoursesList = new ArrayList<SchoolClass>();
                                for (Element eItem : eItems) {
                                    String eItemCode = eItem.getTextContent();
                                    for (SchoolClass scl : schoolClasses)
                                        if (scl.getCode().equals(eItemCode)) eCoursesList.add(scl);
                                }
                                CompositeBlock newBlock = new CompositeBlock(eName, eCode, eCoursesList.toArray(new SchoolClass[0]));
                                blocks.add(newBlock);
                            }

                            program.setBlocks(blocks.toArray(new Block[0]));
                            programs.add(program);
                        }
                        break;

                    case 2:
                        //Cas "student" (2e itération) :
                        rootChildren = getChildren(root, "student");
                        for (Element eElement : rootChildren) {

                            int id = Integer.parseInt(eElement.getElementsByTagName("identifier").item(0).getTextContent());
                            String firstname = eElement.getElementsByTagName("name").item(0).getTextContent();
                            String name = eElement.getElementsByTagName("surname").item(0).getTextContent();
                            String prog = eElement.getElementsByTagName("program").item(0).getTextContent();
                            Student std = new Student(id, firstname, name);

                            //Attribution du programme (la liste des programmes doit impérativement avoir été faite avant, ce qui est ici le cas) :
                            for (Program p : programs) if (p.getCode().equals(prog)) std.setProgram(p);

                            //Attribution des notes :
                            List<Element> eGrades = getChildren(eElement, "grade");
                            for (Element e : eGrades) {
                                String eCode = e.getElementsByTagName("item").item(0).getTextContent();
                                String eValue = e.getElementsByTagName("value").item(0).getTextContent();
                                std.addGrade(new Grade(eValue.equals("ABI")? -1: Double.parseDouble(eValue), eCode));
                            }

                            students.add(std);
                        }
                        break;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error: reading XML file failed.");
        }
    }


    public static ArrayList<Program> getPrograms() {
        return programs;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static ArrayList<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public static void addProgram(Program p) {
        programs.add(p);
    }

    public static void addStudent(Student s) {
        students.add(s);
    }

    public static void addCourse(SchoolClass c) {
        schoolClasses.add(c);
    }

    public static void removeStudent(int index) {
        students.remove(index);
    }


    private static List<Element> getChildren(Element item, String name) {
        NodeList nodeList = item.getChildNodes();
        List<Element> children = new ArrayList<Element>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i); // cas particulier pour nous où tous les noeuds sont des éléments
                if (element.getTagName().equals(name)) {
                    children.add(element);
                }
            }
        }
        return children;
    }
}