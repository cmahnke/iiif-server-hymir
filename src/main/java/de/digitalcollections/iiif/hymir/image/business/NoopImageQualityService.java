package de.digitalcollections.iiif.hymir.image.business;

import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import de.digitalcollections.iiif.model.image.ImageApiProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class NoopImageQualityService implements ImageQualityService {
    @Value("${custom.image.quality.noop.enabled:false}")
    private boolean enabled = false;

    @Value("${custom.image.quality.noop.name:noop}")
    private String name;

    @Override
    public ImageApiProfile.Quality getQuality() {
        return new ImageApiProfile.Quality(name);
    }

    @Override
    public void setIdentifier(String identifier) { }

    @Override
    public BufferedImage processImage(BufferedImage img) {
        return img;
    }

    @Override

    public boolean enabled() {
        return enabled;
    }
}
