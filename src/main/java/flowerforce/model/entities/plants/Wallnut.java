package flowerforce.model.entities.plants;

import flowerforce.model.utilities.RenderingInformation;
import flowerforce.model.utilities.TimerImpl;
import javafx.geometry.Point2D;

/**
 * Models a Wallnut, a tank plant that loses life the more time goes by.
 */
public class Wallnut extends AbstractPlant {

    private static final int NUT_HEALTH = 5000;
    private static final int AUTO_DAMAGE = 30;
    private static final int DAMAGE_PERIOD = RenderingInformation.getFramesPerSecond();
    private static final int NUT_COST =  50;

    /**
     * 
     * @param pos the initial position to place the entity in
     */
    public Wallnut(final Point2D pos) {
        super(pos, new TimerImpl(DAMAGE_PERIOD), NUT_HEALTH, NUT_COST,  RechargeTimes.getSlowRechargeTime(), "wallnut");
    }

    /**
     * Gets auto-damage.
     */
    @Override
    public void updateState() {
        super.updateState();
        if (this.getTimer().isReady()) {
            this.receiveDamage(AUTO_DAMAGE);
        }
    }

}
