package flowerforce.view;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameViewImpl extends Application implements GameView  {

    public final static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("flowerforce/game/Garden.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Plant vs Zombie - Level 1");
        primaryStage.getIcons().add(new Image("flowerforce/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void renderCoins() {

    }
}
