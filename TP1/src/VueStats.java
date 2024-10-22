import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class VueStats extends Pane implements Observateur {
    private Label statsLabel;
    private Jeu jeu;

    public VueStats(Jeu jeu) {
        this.jeu = jeu;
        statsLabel = new Label("Parties jouées : " + jeu.getNbJouees() + " | Parties gagnées : " + jeu.getNbGagnees());
        getChildren().add(statsLabel);
        jeu.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
        statsLabel.setText("Parties jouées : " + jeu.getNbJouees() + " | Parties gagnées : " + jeu.getNbGagnees());
    }

    @Override
    public void defaite() {
        // Aucune action nécessaire ici
    }
}