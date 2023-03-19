package flowerforce.view.game;

import java.io.File;
import java.io.IOException;

import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import javafx.application.Application;
import javafx.geometry.Dimension2D;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This is an implementation of {@link GameEngine}.
 */
public final class FlowerForceApplication extends Application implements FlowerForceView {

    private static final double SCREEN_FILL_INDEX = 0.8;

    private Controller controller;//The controller of the game

    //TODO: use generic separators "/" "\"
    private static final String GAMEICON_PATH = "flowerforce/icon.png";
    private Stage stage;
    private FlowerForceScene sceneClass; 

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.controller = new ControllerImpl();//Instantiate the Controller
        //TODO: setStageSize()
        //this.stage.setFullScreen(true);
        this.stage.setResizable(false);
        this.stage.setTitle("Flower Force");
        this.stage.getIcons().add(new Image(GAMEICON_PATH));
        this.stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        this.menu();
    }

    @Override
    public void menu() {
        try {
            FlowerForceScene sceneClass = new MenuScene(this);
            this.setScene(sceneClass.getScene());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void game(final int levelId) {
        try {
            this.sceneClass = new GameScene(this);
            this.controller.startNewLevelGame(levelId);
            this.setScene(this.sceneClass.getScene());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    private void setScene(final Scene scene) {
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Produces a scene scaled on screen's dimensions.
     * @param root the root element to resize
     * @param imageName the image to take proportions from
     * @return a scaled scene based on screen's dimensions
     * @throws IOException
     */
    public static Scene getScaledScene(final AnchorPane root, final String imageName) throws IOException {
        final String imgPath = "flowerforce" + File.separator + "game" + File.separator + "images" + File.separator + imageName;
        final Image image = new Image(imgPath);
        //background's dimensions
        final double imgWidth = image.getWidth();
        final double imgHeight = image.getHeight();
        final Dimension2D appDimensions = getAppDimensionFromImage(imgWidth, imgHeight);
        //calculation of scale factors
        final double scaleFactorWidth = appDimensions.getWidth() / imgWidth;
        final double scaleFactorHeight = appDimensions.getHeight() / imgHeight;
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight, 0, 0);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appDimensions.getWidth(), appDimensions.getHeight());
    }

    /**
     * Returns the Application dimensions based on a given background image.
     * @param imgWidth image's width
     * @param imgHeight image's height
     * @return
     */
    public static Dimension2D getAppDimensionFromImage(final double imgWidth, final double imgHeight) {
        //screen's dimensions
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //calculation of app's width
        double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        //calculation of app's height
        double appSizeHeight = appSizeWidth / imgWidth * imgHeight;
        //case where app's height would be greater than screen's height
        if (appSizeHeight > screenBounds.getHeight()) {
            appSizeHeight = screenBounds.getHeight();
            appSizeWidth = appSizeHeight / imgHeight * imgWidth;
        }
        return new Dimension2D(appSizeWidth, appSizeHeight);
    }
}