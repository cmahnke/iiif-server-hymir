package de.digitalcollections.iiif.hymir.image.business;

import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import de.digitalcollections.iiif.model.image.ImageApiProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class NoopImageQualityService implements ImageQualityService {
    @Override
    public ImageApiProfile.Quality getQuality() {
        return new ImageApiProfile.Quality("nop");
    }

    @Override
    public void setIdentifier(String identifier) { }

    @Override
    public BufferedImage processImage(BufferedImage img) {
        return img;
    }

    @Override
    @Value("${custom:image.quality.noop:false}")
    public boolean enabled() {
        return false;
    }
}
