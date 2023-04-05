package flowerforce.model.game;

import flowerforce.model.entities.Zombie;
import flowerforce.model.entities.ZombieFactory;
import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is an implementation of {@link CreationZombie}.
 */
public class CreationZombieImpl implements CreationZombie {
    private final int zombieMaxDifficulty;
    private final List<Function<Point2D, Zombie>> zombies;
    private int levelZombieToSpawn;
    private int prevRow = -1;

    private static final Point2D TEMPORARY_POSITION = new Point2D(0, 0);

    /**
     * @param zombies the list of zombie type to spawn
     */
    public CreationZombieImpl(final List<Function<Point2D, Zombie>> zombies) {
        this.zombies = zombies;
        this.zombieMaxDifficulty = zombies.stream()
                .mapToInt(z -> z.apply(TEMPORARY_POSITION).getDifficulty())
                .max()
                .orElse(0);
        this.levelZombieToSpawn = zombies.stream()
                .mapToInt(z -> z.apply(TEMPORARY_POSITION).getDifficulty())
                .min()
                .orElse(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Zombie creationZombie(final int delta) {
        final var zombieToSpawn = zombies.stream()
                .filter(z -> z.apply(TEMPORARY_POSITION).getDifficulty() <= levelZombieToSpawn)
                .collect(Collectors.toSet());

        int randomValue = new Random().nextInt(zombieToSpawn.stream()
                .mapToInt(z -> z.apply(TEMPORARY_POSITION).getDifficulty() + delta)
                .sum() + 1);

        int row;
        do {
            row = new Random().nextInt(Yard.getRowsNum());
        } while (row == prevRow);
        prevRow = row;

        for (final var zombie : zombieToSpawn) {
            randomValue = randomValue - (zombieMaxDifficulty + delta - zombie.apply(TEMPORARY_POSITION).getDifficulty());
            if (randomValue <= 0) {
                return zombie.apply(
                        Yard.getEntityPosition(
                                row,
                                Yard.getColsNum() - 1
                        ));
            }
        }
        return ZombieFactory.basic(Yard.getEntityPosition(
                row,
                Yard.getColsNum() - 1
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseLevelZombieToSpawn() {
        if (levelZombieToSpawn < zombieMaxDifficulty) {
            levelZombieToSpawn += levelZombieToSpawn;
        }
    }
}