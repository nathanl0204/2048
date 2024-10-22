import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.application.Platform;

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
        plateau.setStyle("-fx-background-color: #BBADA0; -fx-padding: 20px; -fx-border-radius: 10;");

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

        btnHaut.setMinWidth(120);
        btnBas.setMinWidth(120);
        btnGauche.setMinWidth(120);
        btnDroite.setMinWidth(120);

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

                // Définir les couleurs des cases en fonction de leur valeur
                String couleur = getColorForValue(valeur);
                btn.setStyle("-fx-background-color: " + couleur + "; -fx-border-color: #BBADA0; -fx-border-width: 2px");

                btn.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Police du texte
                btn.setTextAlignment(TextAlignment.CENTER);

                // Ajouter la case au plateau
                plateau.add(btn, j, i);
            }
        }
    }

    // Méthode pour gérer les couleurs des cases en fonction de leur valeur
    private String getColorForValue(int valeur) {
        switch (valeur) {
            case 2: return "#EEE4DA";
            case 4: return "#EDE0C8";
            case 8: return "#F2B179";
            case 16: return "#F59563";
            case 32: return "#F67C5F";
            case 64: return "#F65E3B";
            case 128: return "#EDCF72";
            case 256: return "#EDCC61";
            case 512: return "#EDC850";
            case 1024: return "#EDC53F";
            case 2048: return "#EDC22E";
            default: return "#3C3A32"; // Couleur pour valeurs supérieures à 2048
        }
    }

    @Override
    public void reagir() {
        // Lorsque le modèle notifie un changement, reconstruire le plateau
        construirePlateau();
    }

    // Méthode pour afficher un message de défaite
    @Override
    public void defaite() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Défaite");
        alert.setHeaderText("Partie terminée !");
        alert.setContentText("Aucun mouvement possible, vous avez perdu !");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }
}
