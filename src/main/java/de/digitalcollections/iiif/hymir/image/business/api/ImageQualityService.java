package de.digitalcollections.iiif.hymir.image.business.api;

import de.digitalcollections.iiif.hymir.model.api.HymirPlugin;
import de.digitalcollections.iiif.model.image.ImageApiProfile;

import java.awt.image.BufferedImage;

/**
 * This interface is used to provide different implementations for IIIF qualities
 */
public interface ImageQualityService extends HymirPlugin {
    /**
     * This will be used by the ImageService to decide whether a service should be used.
     *
     * @return ImageApiProfile.Quality The quality whis is implemented by this Service
     */
    public ImageApiProfile.Quality getQuality();



    /**
     * This method need to be implemented to do the actual transformation
     *
     * @param identifier The identifier of the image, might be null depending on implementation
     * @param img The image to be transformed
     * @return BufferedImage   The transformed image
     */
    public BufferedImage processImage(String identifier, BufferedImage img);

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
}
