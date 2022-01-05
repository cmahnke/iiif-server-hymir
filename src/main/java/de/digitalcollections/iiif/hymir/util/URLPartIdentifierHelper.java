package de.digitalcollections.iiif.hymir.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

public class URLPartIdentifierHelper {
    public static String extractIdentifier (String identifier, HttpServletRequest req) {
      String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
      String id;
      if (identifier == null) {
        id = new AntPathMatcher().extractPathWithinPattern(pattern, (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
      } else {
        id = identifier;
      }

      if (pattern.contains("**")) {
        int cutPos = pattern.indexOf("**") + "**".length();
        String cutOff = pattern.substring(cutPos);
        return id.replace(cutOff, "");
      }
      return id;
    }
}
