package flowerforce.controller;

import flowerforce.model.game.Game;
import flowerforce.view.game.GameEngine;

/**
 * This is an implementation of {@link GameLoop}.
 */
public class GameLoopImpl implements GameLoop, Runnable {

    private final GameEngine gameEngine;
    private final Game model;
    private static final int FPS = 30;
    private static final long TIME_SLICE = 1_000_000_000 / FPS;

    /**
     * Instantiate a new GameLoop giving it the model and the GameEngine it will communicate with.
     * @param gameEngine The gameEngine to render on the view
     * @param model The GameModel to get the game information
     */
    public GameLoopImpl(final GameEngine gameEngine, final Game model) {
        this.gameEngine = gameEngine;
        this.model = model;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void start() {

    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();
        long timeAccumulator = 0;
        Boolean updated = false;

        while (!this.model.isOver()) {
            final long actualTime = System.nanoTime();
            final long elapsedTime = actualTime - lastUpdateTime;
            lastUpdateTime += elapsedTime;
            timeAccumulator += elapsedTime;

            while (timeAccumulator > TIME_SLICE) {
                this.model.update();
                updated = true;
                timeAccumulator -= TIME_SLICE;
            }

            if (updated) {
                updateView();
                updated = false;
            }
        }
        this.gameEngine.over(this.model.result());
    }

    private void updateView() {
        this.gameEngine.render();
    }
}
