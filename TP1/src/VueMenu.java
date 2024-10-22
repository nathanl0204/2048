import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

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
        this.setStyle("-fx-background-color: #BBADA0; -fx-font-size: 16px");
    }

    @Override
    public void reagir() {
        // Mise à jour si nécessaire
    }

    @Override
    public void defaite() {
        // Rien à faire pour VueMenu lors d'une défaite
    }
}