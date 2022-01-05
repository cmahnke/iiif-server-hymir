package de.digitalcollections.iiif.hymir.image.business;

import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class NopImageQualityService implements ImageQualityService {
    @Override
    public String getName() {
        return "nop";
    }

    @Override
    public void setIdentifier(String identifier) { }

    @Override
    public BufferedImage processImage(BufferedImage img) {
        return img;
    }
}
