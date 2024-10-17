import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class EcouteurObjectif implements EventHandler<ActionEvent> {
    private Jeu jeu;

    public EcouteurObjectif(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void handle(ActionEvent e) {
        // Crée une boîte de dialogue pour demander un nouvel objectif
        TextInputDialog dialog = new TextInputDialog(String.valueOf(jeu.getObjectif()));
        dialog.setTitle("Changer l'objectif");
        dialog.setHeaderText("Définir un nouvel objectif pour le jeu");
        dialog.setContentText("Objectif : ");

        // Attend l'entrée de l'utilisateur
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(objectifStr -> {
            try {
                int nouvelObjectif = Integer.parseInt(objectifStr);
                jeu.setObjectif(nouvelObjectif); // Met à jour l'objectif du jeu
                jeu.notifierObservateurs(); // Notifie les observateurs du changement
            } catch (NumberFormatException nfe) {
                System.out.println("Entrée invalide pour l'objectif");
            }
        });
    }
}