package flowerforce.view.game;

import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public final class GameSceneController implements Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ImageView imgBackground;

    @FXML
    private GridPane griglia;

    @FXML
    private Button btnSunflower;

    @FXML
    private Button btnPeashooter;

    @FXML
    private Label lblSunCounter;

    @FXML
    private Canvas cnvYard;

    private final FlowerForceApplication application;
    private final Dimension size;

    public GameSceneController(final FlowerForceApplication application, final Dimension size) {
        this.application = application;
        this.size = size;
    }

    @FXML
    void selectPeashooter(final ActionEvent event) {
        System.out.println("Peashooter selected"); //TODO: remove
    }

    @FXML
    void selectSunflower(final ActionEvent event) {
        System.out.println("Sunflower selected"); //TODO: remove
    }

    @FXML
    void canvasClicked(final MouseEvent event) {
        System.out.println(getRow(event.getY()) + " " + getColumn(event.getX()));
    }

    private int getRow(final double y) {
        return getGridIndex(y, cnvYard.getHeight(), 5); //TODO: remove magic number
    }

    private int getColumn(final double x) {
        return getGridIndex(x, cnvYard.getWidth(), 9); //TODO: remove magic number
    }

    private int getGridIndex(final double val, final double totalLength, final int nSlices) {
        final double span = totalLength / nSlices;
        for (int r = nSlices - 1; r >= 0; r--) {
            if (val >= r * span) {
                return r;
            }
        }
        return 0;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setWindowSize();
    }

    private void setWindowSize() {
        imgBackground.setFitHeight(size.getHeight());
        imgBackground.setFitWidth(size.getWidth());
        gamePane.setPrefWidth(size.getWidth());
        gamePane.setPrefHeight(size.getHeight());
        //TODO: find the correct ratio between canvas and gamePane size
        cnvYard.setWidth(size.getWidth()*0.8);
        cnvYard.setHeight(size.getHeight()*0.8);
    }

    protected void clearCanvas() {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        gc.clearRect(0, 0, size.getWidth(), size.getHeight());
    }

    protected void draw(final Image image, final Point2D pos) {
        GraphicsContext gc = this.cnvYard.getGraphicsContext2D();
        //TODO: must resize correctly the image, depending on screen size
        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), pos.getX(), pos.getY(), image.getWidth(), image.getHeight());
        System.out.println("DRAWN " + image.getHeight());
    }
}
