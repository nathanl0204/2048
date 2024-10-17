import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class VueMenu extends MenuBar implements Observateur {
    private Jeu jeu;

    public VueMenu(Jeu jeu) {
        this.jeu = jeu;

        // Création du menu "Jeu"
        Menu menuJeu = new Menu("Jeu");
        MenuItem nouvelItem = new MenuItem("Nouvelle Partie");
        nouvelItem.setOnAction(new EcouteurNouveau(jeu));

        MenuItem changerObjectif = new MenuItem("Changer Objectif");
        changerObjectif.setOnAction(new EcouteurObjectif(jeu));

        menuJeu.getItems().add(nouvelItem);
        menuJeu.getItems().add(changerObjectif);

        this.getMenus().add(menuJeu);
    }

    @Override
    public void reagir() {
        // Mise à jour si nécessaire
    }
}