package flowerforce.view.game;

import java.io.IOException;

/**
 * Models the scene of the game, extends {@link AbstractFlowerForceScene}.
 */
public class GameScene extends AbstractFlowerForceScene {

    private static final String FXML_PATH = "flowerforce/game/fxml/Game.fxml";
    private static final String IMAGE_NAME = "Garden.png";

    /**
     * Creates a new Game view.
     * @param application which sets the scene
     * @throws IOException if some files couldn't be found
     */
    public GameScene(final FlowerForceApplication application) throws IOException {
        super(FXML_PATH, IMAGE_NAME, new GameSceneController(application));
    }
}
