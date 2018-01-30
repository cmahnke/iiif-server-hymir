package de.digitalcollections.iiif.hymir.frontend;

import de.digitalcollections.iiif.hymir.model.exception.InvalidParametersException;
import de.digitalcollections.iiif.hymir.model.exception.ResolvingException;
import de.digitalcollections.iiif.hymir.model.exception.ResourceNotFoundException;
import de.digitalcollections.iiif.hymir.model.exception.UnsupportedFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidParametersException.class)
  public void handleInvalidParametersException(Exception exception) {
    // NOP
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResolvingException.class)
  public void handleResolvingException(Exception exception) {
    // NOP
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public void handleResourceNotFoundException(Exception exception) {
    // NOP
  }

  @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(UnsupportedFormatException.class)
  public void handleUnsupportedFormatException(Exception exception) {
    // NOP
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UnsupportedOperationException.class)
  public void handleUnsupportedOperationException(Exception exception) {
    // NOP
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public void handleAllOther(Exception exception) {
    LOGGER.error("exception stack trace", exception);
  }
  // NOP
}
