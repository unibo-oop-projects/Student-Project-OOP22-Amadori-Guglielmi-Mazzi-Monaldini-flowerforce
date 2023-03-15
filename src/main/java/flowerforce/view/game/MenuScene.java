package flowerforce.view.game;

import java.io.IOException;
import java.util.Optional;

import flowerforce.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Models the Menu screen.
 */
public class MenuScene implements FlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/SimpleMenu.fxml";
    private static final String IMAGE_NAME = "menu.png";
    private final Scene scene;

    /**
     * Creates a new Menu view.
     * @param application the application that displays the scene
     * @param mainController the main controller
     * @throws IOException
     */
    public MenuScene(final FlowerForceApplication application, final Controller mainController) throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(FXML_PATH));
        loader.setController(new MenuSceneController(application, mainController));
        final AnchorPane root = loader.load();

        this.scene = FlowerForceApplication.getScaledScene(root, IMAGE_NAME);
    }

    /**
     * Returns the class' scene.
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }
}
