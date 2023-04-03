package flowerforce.controller.utilities;

import flowerforce.common.ResourceFinder;
import flowerforce.view.entities.CardView;
import flowerforce.view.entities.CardViewImpl;
import flowerforce.view.entities.EntityView;
import flowerforce.view.entities.EntityViewImpl;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Pair;

/**
 * This utility class convert an entity given from the model into an entity that can be drawn in the view.
 */
public final class EntityConverterImpl implements EntityConverter {
    private static final String IMAGES_EXTENSION = ".png";
    private static final String CARD_SUFFIX = "_card";
    private final double yardRatioHeight;
    private final double yardRatioWidth;

    /**
     * Create a new instance of EntityConverter, setting all the information.
     * @param modelYardDimension The dimensione of the yard of the model
     * @param viewYardDimension The dimension of the yard of the view
     */
    public EntityConverterImpl(final Dimension2D modelYardDimension, final Dimension2D viewYardDimension) {
        this.yardRatioHeight = viewYardDimension.getHeight() / modelYardDimension.getHeight();
        this.yardRatioWidth =  viewYardDimension.getWidth() / modelYardDimension.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getPlantView(final Pair<String, Point2D> plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getKey().concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertPlantPosition(plantInfo.getValue(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getZombieView(final Pair<String, Point2D> zombieInfo) {
        final String completeImagePath = ResourceFinder.getZombieImagePath(
                zombieInfo.getKey().concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertZombiePosition(zombieInfo.getValue(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityView getBulletView(final Pair<String, Point2D> bulletInfo) {
        final String completeImagePath = ResourceFinder.getBulletImagePath(
                bulletInfo.getKey().concat(IMAGES_EXTENSION));
        final Point2D newPosition = convertBulletPosition(bulletInfo.getValue(), completeImagePath);
        return new EntityViewImpl(newPosition, completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardView getCardView(final Pair<String, Integer> plantInfo) {
        final String completeImagePath = ResourceFinder.getPlantImagePath(
                plantInfo.getKey().concat(CARD_SUFFIX).concat(IMAGES_EXTENSION));
        return new CardViewImpl(plantInfo.getValue(), completeImagePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeZombieViewPosition(final EntityView entityView, final Point2D newPosition) {
        entityView.setPosition(convertZombiePosition(newPosition, entityView.getPlaceableImage().getUrl()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeBulletViewPosition(final EntityView entityView, final Point2D newPosition) {
        entityView.setPosition(convertBulletPosition(newPosition, entityView.getPlaceableImage().getUrl()));
    }
    private Point2D convertPlantPosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                getImageWidth(imagePath),
                getImageHeight(imagePath));

        return outputPosition;
    }

    private Point2D convertZombiePosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                0,
                getImageHeight(imagePath));

        return outputPosition;
    }

    private Point2D convertBulletPosition(final Point2D originalPosition, final String imagePath) {
        //Convert the model position multiplying for the yardSizeFactor
        Point2D outputPosition = new Point2D(
                originalPosition.getX() * this.yardRatioWidth,
                originalPosition.getY() * this.yardRatioHeight);

        //Get the placing position
        outputPosition = outputPosition.subtract(
                getImageWidth(imagePath),
                getImageHeight(imagePath));
        return outputPosition.subtract(0, 80); //TODO: modify
    }

    private static double getImageWidth(final String path) {
        return getImageDimension(path, true);
    }

    private static double getImageHeight(final String path) {
        return getImageDimension(path, false);
    }

    private static double getImageDimension(final String path, final boolean isWidth) {
        if (path == null) {
            return -1;
        }
        try {
            return isWidth ? new Image(path).getWidth() : new Image(path).getHeight();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }
}