package de.digitalcollections.iiif.hymir.image.business.api;

import java.awt.image.BufferedImage;

public interface ImageQualityService {
  public String getName();
  public BufferedImage processImage(BufferedImage img);
}
