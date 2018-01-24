package de.digitalcollections.iiif.hymir.presentation.backend;

import de.digitalcollections.core.business.api.ResourceService;
import de.digitalcollections.core.model.api.MimeType;
import de.digitalcollections.core.model.api.resource.Resource;
import de.digitalcollections.core.model.api.resource.enums.ResourcePersistenceType;
import de.digitalcollections.core.model.api.resource.exceptions.ResourceIOException;
import de.digitalcollections.iiif.hymir.model.exception.ResolvingException;
import de.digitalcollections.iiif.hymir.presentation.backend.api.PresentationRepository;
import de.digitalcollections.iiif.model.jackson.IiifObjectMapper;
import de.digitalcollections.iiif.model.sharedcanvas.Collection;
import de.digitalcollections.iiif.model.sharedcanvas.Manifest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Default implementation trying to get manifest.json from an resolved URI as String and returning Manifest instance.
 */
@Repository
public class PresentationRepositoryImpl implements PresentationRepository {

  private static final String COLLECTION_PREFIX = "collection-";
  private static final Logger LOGGER = LoggerFactory.getLogger(PresentationRepositoryImpl.class);

  @Autowired
  private IiifObjectMapper objectMapper;

  @Autowired
  private ResourceService resourceService;

  @Override
  public Collection getCollection(String name) throws ResolvingException {
    // to get a regex resolable pattern we add a static prefix for collections
    String collectionName = COLLECTION_PREFIX + name;
    try {
      Resource resource = resourceService.get(collectionName, ResourcePersistenceType.REFERENCED, MimeType.MIME_APPLICATION_JSON);
      return objectMapper.readValue(getResourceJson(resource.getUri()), Collection.class);
    } catch (IOException ex) {
      LOGGER.info("Could not retrieve collection {}", collectionName, ex);
      throw new ResolvingException("No collection for name " + collectionName);
    }
  }

  @Override
  public Manifest getManifest(String identifier) throws ResolvingException {
    try {
      Resource resource = resourceService.get(identifier, ResourcePersistenceType.REFERENCED, MimeType.MIME_APPLICATION_JSON);
      return objectMapper.readValue(getResourceJson(resource.getUri()), Manifest.class);
    } catch (IOException ex) {
      LOGGER.info("Error getting manifest for identifier " + identifier, ex);
      throw new ResolvingException("No manifest for identifier " + identifier);
    }
  }

  @Override
  public Instant getManifestModificationDate(String identifier) throws ResolvingException {
    return getResourceModificationDate(identifier);
  }

  @Override
  public Instant getCollectionModificationDate(String identifier) throws ResolvingException {
    return getResourceModificationDate(identifier);
  }

  private Instant getResourceModificationDate(String identifier) throws ResolvingException {
    try {
      Resource resource = resourceService.get(identifier, ResourcePersistenceType.REFERENCED, MimeType.MIME_APPLICATION_JSON);
      return Instant.ofEpochMilli(resource.getLastModified());
    } catch (ResourceIOException ex) {
      throw new ResolvingException("No manifest for identifier " + identifier);
    }
  }

  protected String getResourceJson(URI resourceUri) throws ResolvingException {
    try (InputStream is = resourceService.getInputStream(resourceUri)) {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new ResolvingException(e);
    }
  }
}
