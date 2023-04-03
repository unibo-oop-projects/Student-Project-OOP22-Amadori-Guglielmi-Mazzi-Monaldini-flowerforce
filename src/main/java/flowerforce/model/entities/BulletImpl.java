package flowerforce.model.entities;

import java.util.Optional;
import java.util.function.Consumer;

import flowerforce.model.utilities.RenderingInformation;
import javafx.geometry.Point2D;

/**
 * Models a generic bullet.
 */
public class BulletImpl extends AbstractEntity implements Bullet {

    private static final double SECS_PER_CELL = 0.2;
    private static final double DELTA = RenderingInformation.getDeltaFromSecondsPerCell(SECS_PER_CELL);

    private final int damage;
    private boolean hasHit;
    private Optional<Consumer<Zombie>> actionOverZombie = Optional.empty();

    /**
     * 
     * @param pos the initial position to place the bullet in
     * @param damage the damage that the bullet does to zombies
     * @param bulletName the bullet's name
     */
    public BulletImpl(final Point2D pos, final int damage, final String bulletName) {
        super(pos, bulletName);
        this.damage = damage;
    }

    /**
     * 
     * @param pos the initial position to place the bullet in
     * @param damage the damage that the bullet does to zombies
     * @param bulletName the bullet's name
     * @param action an action to do on a zombie when hit
     */
    public BulletImpl(final Point2D pos, final int damage, final String bulletName, final Consumer<Zombie> action) {
        this(pos, damage, bulletName);
        this.actionOverZombie = Optional.of(action);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        this.setPosition(new Point2D(this.getPosition().getX() + DELTA, this.getPosition().getY()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.hasHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Zombie zombie) {
        this.hasHit = true;
        zombie.receiveDamage(this.damage);
        this.actionOverZombie.ifPresent(x -> x.accept(zombie));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDeltaMovement() {
        return DELTA;
    }

}
