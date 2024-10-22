import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Jeu jeu = new Jeu();
        VueMenu vueMenu = new VueMenu(jeu);
        VueStats vueStats = new VueStats(jeu);
        VuePlateau vuePlateau = new VuePlateau(jeu);

        BorderPane root = new BorderPane();
        root.setTop(vueMenu);
        root.setCenter(vuePlateau);
        root.setBottom(vueStats);

        Scene scene = new Scene(root, 400, 400);

        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Jeu 2048");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}