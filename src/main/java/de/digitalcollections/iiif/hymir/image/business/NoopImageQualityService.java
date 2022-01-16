package de.digitalcollections.iiif.hymir.image.business;

import de.digitalcollections.iiif.hymir.image.business.api.ImageQualityService;
import de.digitalcollections.iiif.hymir.model.api.HymirPlugin;
import de.digitalcollections.iiif.model.image.ImageApiProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class NoopImageQualityService implements ImageQualityService.Tile, HymirPlugin.Buildin {
    @Value("${custom.image.quality.noop.enabled:false}")
    private boolean enabled = false;

    @Value("${custom.image.quality.noop.name:noop}")
    private String name;

    public String name() {
        return "Noop Image Quality Plugin, just returns the given image";
    }

    @Override
    public ImageApiProfile.Quality getQuality() {
        return new ImageApiProfile.Quality(name);
    }

    @Override
    public BufferedImage processTile(String identifier, BufferedImage img) {
        return img;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public boolean hasAlpha() {
        return false;
    }
}
