import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EcouteurNouveau implements EventHandler<ActionEvent> {
    private Jeu jeu;

    public EcouteurNouveau(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void handle(ActionEvent e) {
        jeu.nouveau();
    }
}