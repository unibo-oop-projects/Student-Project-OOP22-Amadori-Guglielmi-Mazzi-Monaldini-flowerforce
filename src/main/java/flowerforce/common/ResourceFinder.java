package flowerforce.common;

import java.io.File;
import java.util.Optional;

/**
 * Utility class to get resources complete path.
 */
public final class ResourceFinder {

    private static final String PROJECT_FOLDER_ABSOLUTE_PATH = System.getProperty("user.dir");
    private static final String RESOURCES_FOLDER_PATH = "src" + File.separator + "main" + File.separator
            + "resources" + File.separator + "flowerforce" + File.separator + "game";
    private static final String RESOURCES_FOLDER_ABSOLUTE_PATH = PROJECT_FOLDER_ABSOLUTE_PATH
            + File.separator + RESOURCES_FOLDER_PATH;

    private static final String IMAGES_FOLDER_NAME = "images";
    private static final String FXML_FOLDER_NAME = "fxml";
    private static final String SAVING_FOLDER_NAME = "savings";

    private ResourceFinder() {

    }

    /**
     * Get the complete path of an image.
     * @param filename The complete name of the image requested
     * @return An optional of the image path, empty if the requested image do not exist in the images folder
     */
    public static Optional<String> getImagePath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + IMAGES_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the complete path of a fxml file.
     * @param filename The complete name of the fxml file requested
     * @return An optional of the fxml file path, empty if the requested fxml file do not exist in the fxml folder
     */
    public static Optional<String> getFXMLPath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + FXML_FOLDER_NAME + File.separator + filename);
    }

    /**
     * Get the complete path of a saving file.
     * @param filename The complete name of the saving file requested
     * @return An optional of the saving file path, empty if the requested saving file do not exist in the savings folder
     */
    public static Optional<String> getSavingFilePath(final String filename) {
        return returnOptional(RESOURCES_FOLDER_ABSOLUTE_PATH + File.separator + SAVING_FOLDER_NAME + File.separator + filename);
    }

    private static Optional<String> returnOptional(final String path) {
        final File file = new File(path);
        return file.exists() ? Optional.of(path) : Optional.empty();
    }
}