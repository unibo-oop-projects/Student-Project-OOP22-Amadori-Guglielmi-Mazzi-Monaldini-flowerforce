package flowerforce.model.game;

import javafx.geometry.Dimension2D;

/**
 * Models the world the game's played in.
 */
public interface World {

    /**
     * 
     * @return the current player
     */
    Player getPlayer();

    /**
     * Creates a level game.
     * @param levelId the level to create
     * @return the game to be played
     */
    Game createLevelGame(int levelId);

    /**
     * Creates an infinite game.
     * @return the game to be played
     */
    Game createInfiniteGame();

    /**
     * 
     * @return the maximum number of frames per second the game must be set to.
     */
    int getRenderingInformations();

    /**
     * 
     * @return the dimension of the entire yard
     */
    Dimension2D getYardDimension();

    /**
     * 
     * @return the rows number of yard's cells matrix
     */
    int getRowsNum();

    /**
     *
     * @return the columns number of yard's cells matrix
     */
    int getColsNum();

    /**
     * 
     * @return this World's Shop
     */
    Shop getShop();
}
