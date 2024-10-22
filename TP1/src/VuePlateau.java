import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VuePlateau extends HBox implements Observateur {
    private Jeu jeu;
    private GridPane plateau; // GridPane pour les cases du plateau
    private Button btnHaut, btnBas, btnGauche, btnDroite;

    public VuePlateau(Jeu jeu) {
        this.jeu = jeu;

        // Enregistrer la vue comme observateur du modèle Jeu
        jeu.ajouterObservateur(this);

        // Construction du plateau dans un GridPane
        plateau = new GridPane();
        plateau.setPadding(new Insets(10));
        plateau.setHgap(10);
        plateau.setVgap(10);

        // Création des boutons de direction
        btnHaut = new Button("Haut");
        btnBas = new Button("Bas");
        btnGauche = new Button("Gauche");
        btnDroite = new Button("Droite");

        // Définir la taille des boutons pour les rendre cohérents
        btnHaut.setPrefSize(60, 40);
        btnBas.setPrefSize(60, 40);
        btnGauche.setPrefSize(60, 40);
        btnDroite.setPrefSize(60, 40);

        // Écouteurs pour les boutons de direction
        btnHaut.setOnAction(new EcouteurCase(jeu, "haut"));
        btnBas.setOnAction(new EcouteurCase(jeu, "bas"));
        btnGauche.setOnAction(new EcouteurCase(jeu, "gauche"));
        btnDroite.setOnAction(new EcouteurCase(jeu, "droite"));

        // Créer un GridPane pour organiser les boutons en croix
        GridPane boutonsPane = new GridPane();
        boutonsPane.setHgap(10);
        boutonsPane.setVgap(10);
        boutonsPane.setPadding(new Insets(10));

        // Positionner les boutons dans une croix
        boutonsPane.add(btnHaut, 1, 0);
        boutonsPane.add(btnGauche, 0, 1);
        boutonsPane.add(btnDroite, 2, 1);
        boutonsPane.add(btnBas, 1, 2);

        // Ajouter le plateau de jeu et les boutons dans une HBox
        this.setSpacing(20); // Espace entre le plateau et les boutons
        this.setPadding(new Insets(20));

        // Aligner le contenu à gauche pour le plateau et au centre pour les boutons
        this.setAlignment(Pos.CENTER_LEFT);

        // Ajouter le plateau et les boutons à la HBox
        this.getChildren().addAll(plateau, boutonsPane);

        // Ajoute VuePlateau comme observateur du jeu
        jeu.ajouterObservateur(this);
        construirePlateau();
    }

    // Méthode pour construire le plateau initial avec les boutons représentant les cases
    private void construirePlateau() {
        plateau.getChildren().clear(); // Efface uniquement le contenu du GridPane

        // Création des cases sous forme de boutons
        for (int i = 0; i < jeu.size(); i++) {
            for (int j = 0; j < jeu.size(); j++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100); // Taille des boutons des cases du plateau
                int valeur = jeu.getCase(i, j);
                if (valeur != 0) {
                    btn.setText(String.valueOf(valeur));
                } else {
                    btn.setText(""); // Vide si la case est 0
                }

                // Ajouter la case au plateau
                plateau.add(btn, j, i);
            }
        }
    }

    @Override
    public void reagir() {
        // Lorsque le modèle notifie un changement, reconstruire le plateau
        construirePlateau();
    }
}
