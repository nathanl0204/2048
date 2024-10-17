import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;

public class VuePlateau extends BorderPane implements Observateur {
    private Jeu jeu;
    private GridPane plateau; // GridPane pour les cases du plateau

    public VuePlateau(Jeu jeu) {
        this.jeu = jeu;

        // Enregistrer la vue comme observateur du modèle Jeu
        jeu.ajouterObservateur(this);

        // Construction du plateau dans un GridPane
        plateau = new GridPane();
        construirePlateau();

        // Création des boutons de direction
        Button boutonHaut = new Button("Haut");
        Button boutonBas = new Button("Bas");
        Button boutonGauche = new Button("Gauche");
        Button boutonDroite = new Button("Droite");

        // Liaison des boutons avec les écouteurs de direction
        boutonHaut.setOnAction(new EcouteurCase(jeu, "haut"));
        boutonBas.setOnAction(new EcouteurCase(jeu, "bas"));
        boutonGauche.setOnAction(new EcouteurCase(jeu, "gauche"));
        boutonDroite.setOnAction(new EcouteurCase(jeu, "droite"));

        // Mise en page des boutons de direction autour du plateau
        BorderPane.setAlignment(boutonHaut, Pos.CENTER);
        BorderPane.setAlignment(boutonBas, Pos.CENTER);
        BorderPane.setAlignment(boutonGauche, Pos.CENTER);
        BorderPane.setAlignment(boutonDroite, Pos.CENTER);

        // Ajout des boutons de direction au BorderPane
        this.setTop(boutonHaut);
        this.setBottom(boutonBas);
        this.setLeft(boutonGauche);
        this.setRight(boutonDroite);

        // Ajout du plateau au centre du BorderPane
        this.setCenter(plateau);
    }

    // Méthode pour construire le plateau initial avec les boutons représentant les cases
    private void construirePlateau() {
        plateau.getChildren().clear(); // Efface uniquement le contenu du GridPane

        // Création des cases sous forme de boutons
        for (int i = 0; i < jeu.size(); i++) {
            for (int j = 0; j < jeu.size(); j++) {
                Button btn = new Button(String.valueOf(jeu.getCase(i, j)));
                btn.setMinSize(100, 100); // Taille des boutons pour une meilleure lisibilité
                plateau.add(btn, j, i); // Place chaque bouton représentant une case à sa position
            }
        }
    }

    @Override
    public void reagir() {
        // Lorsque le modèle notifie un changement, reconstruire le plateau
        construirePlateau();
    }
}
