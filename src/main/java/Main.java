import graphics.GMWindow;
import xmlreader.XMLReader;

/**
 * @author Quentin Garnier
 *         Léa Bloom
 *         Miléna Kostov
 */

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) XMLReader.read("data/data.xml"); //Chemin de lecture par défaut (sans paramètre)
        else XMLReader.read(args[0]); //Chemin de lecture personnalisé (premier paramètre)

        GMWindow gmWindow = new GMWindow(); //Création de la fenêtre graphique de l'application
        gmWindow.display(); //Affichage de la fenêtre graphique
    }
}
