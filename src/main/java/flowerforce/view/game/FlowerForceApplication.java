package flowerforce.view.game;

import java.io.File;
import java.io.IOException;

import flowerforce.controller.Controller;
import flowerforce.controller.ControllerImpl;
import flowerforce.controller.GameLoop;
import flowerforce.controller.GameLoopImpl;
import javafx.animation.AnimationTimer;
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
            this.setScene(this.sceneClass.getScene());
            final GameLoop gameLoop = new GameLoopImpl(this.controller.getGameEngine(), this.controller.startNewLevelGame(levelId));

            AnimationTimer animationTimer = new AnimationTimer()
            {
                public void handle(long currentNanoTime)
                {
                    System.out.print(currentNanoTime + " : ");
                    if(gameLoop.isRunning()) {
                        gameLoop.singleTick();
                    }
                    else {
                        this.stop();
                    }
                }
            };
            animationTimer.start();
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
        //background's dimensions
        final Dimension2D imgDimensions = getImgDimensions(imageName);
        //app's dimensions
        final Dimension2D appDimensions = getAppDimensionFromImage(imgDimensions);
        //calculation of scale factors
        final double scaleFactorWidth = appDimensions.getWidth() / imgDimensions.getWidth();
        final double scaleFactorHeight = appDimensions.getHeight() / imgDimensions.getHeight();
        final Scale scaleTransformation = new Scale(scaleFactorWidth, scaleFactorHeight, 0, 0);
        root.getTransforms().add(scaleTransformation);
        return new Scene(root, appDimensions.getWidth(), appDimensions.getHeight());
    }

    /**
     * Returns the Application dimensions based on a given background image.
     * @param imgDimensions image's dimensions in pixel
     * @return a Dimension2D representing app's dimensions
     */
    public static Dimension2D getAppDimensionFromImage(final Dimension2D imgDimensions) {
        //screen's dimensions
        final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //calculation of app's width
        double appSizeWidth = SCREEN_FILL_INDEX * screenBounds.getWidth();
        //calculation of app's height
        double appSizeHeight = appSizeWidth / imgDimensions.getWidth() * imgDimensions.getHeight();
        //case where app's height would be greater than screen's height
        if (appSizeHeight > screenBounds.getHeight()) {
            appSizeHeight = screenBounds.getHeight();
            appSizeWidth = appSizeHeight / imgDimensions.getHeight() * imgDimensions.getWidth();
        }
        return new Dimension2D(appSizeWidth, appSizeHeight);
    }

    /**
     * Gets an image's dimension.
     * @param imgName the image name (located in the standard image folder)
     * @return a Dimension2D contaning image's dimensions
     */
    public static Dimension2D getImgDimensions(final String imgName) {
        final String imgPath = "flowerforce" + File.separator + "game" + File.separator + "images" + File.separator + imgName;
        final Image image = new Image(imgPath);
        //image's dimensions
        return new Dimension2D(image.getWidth(), image.getHeight());
    }
}
