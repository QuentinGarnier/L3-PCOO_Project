import graphics.GMWindow;
import xmlreader.XMLReader;

/**
 * @author Quentin Garnier
 *         Léa Bloom
 *         Miléna Kostov
 */

public class Main {
    public static void main(String[] args) {
        XMLReader.read("data/data.xml");

        GMWindow gmWindow = new GMWindow();
        gmWindow.display();
    }
}
