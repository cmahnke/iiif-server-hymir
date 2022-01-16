package de.digitalcollections.iiif.hymir.image.business.api;

import de.digitalcollections.iiif.hymir.model.api.HymirPlugin;
import de.digitalcollections.iiif.model.image.ImageApiProfile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageReader;

/**
 * This interface is used to provide different implementations for IIIF qualities
 */
public abstract interface ImageQualityService extends HymirPlugin {
    /**
     * This will be used by the ImageService to decide whether a service should be used.
     *
     * @return ImageApiProfile.Quality The quality whis is implemented by this Service
     */
    public ImageApiProfile.Quality getQuality();

    /**
     * Check if the plugin should be used.
     *
     * @return whether the service should be used and advertised
     */
    public boolean enabled();

    /**
     * Set if the image should be read with alpha channel
     * @return whether the resulting image might has a alpha channel
     */
    public boolean hasAlpha();

    interface Tile extends ImageQualityService {
        /**
         * This method need to be implemented to do the actual transformation of a image tile
         *
         * @param identifier The identifier of the image, might be null depending on implementation
         * @param img The image to be transformed
         * @return BufferedImage   The transformed image
         */
        public BufferedImage processTile(String identifier, BufferedImage img);
    }

    interface Source extends ImageQualityService {
        /**
         * This method need to be implemented to do the actual transformation of the whole image
         *
         * @param identifier The identifier of the image, might be null depending on implementation
         * @param inputStream The IInputStream source of the Image
         * @return A ImageReader providing the processed image
         */
        public ImageReader processStream(String identifier, InputStream inputStream);
    }
}
