package flowerforce.model.entities;

import javafx.geometry.Point2D;

public class BulletFactoryImpl implements BulletFactory {

    private static final int STANDARD_DAMAGE = 20;
    private static final int FIRE_DAMAGE = STANDARD_DAMAGE * 2;

    @Override
    public Bullet createStandardBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE);
    }

    @Override
    public Bullet createFireBullet(final Point2D pos) {
        return new BulletImpl(pos, FIRE_DAMAGE, z -> z.warmUp());
    }

    @Override
    public Bullet createSnowdBullet(final Point2D pos) {
        return new BulletImpl(pos, STANDARD_DAMAGE, z -> z.freeze());
    }
    
}
