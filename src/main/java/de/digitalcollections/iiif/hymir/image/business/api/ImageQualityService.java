package de.digitalcollections.iiif.hymir.image.business.api;

import de.digitalcollections.iiif.model.image.ImageApiProfile;

import java.awt.image.BufferedImage;

public interface ImageQualityService {
  public ImageApiProfile.Quality getQuality();
  public void setIdentifier(String identifier);
  public BufferedImage processImage(BufferedImage img);
  public boolean enabled();
}
