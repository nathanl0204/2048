import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EcouteurCase implements EventHandler<ActionEvent> {
    private Jeu jeu;
    private String direction;

    public EcouteurCase(Jeu jeu, String direction) {
        this.jeu = jeu;
        this.direction = direction;
    }

    @Override
    public void handle(ActionEvent e) {
        jeu.jouer(direction);
    }
}