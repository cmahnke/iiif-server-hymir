package de.digitalcollections.iiif.hymir.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class URLPartIdentifierHelper {

    public static Map<String, String> getParams(HttpServletRequest req) {
        String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String wildcard = new AntPathMatcher().extractPathWithinPattern(pattern, (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
        Map<String, String> params = null;
        // Check if we could handle the pattern
        if (pattern.split("\\*\\*", -1).length > 2) {
            throw new IllegalStateException("Only one wildcard supported");
        }

        if (pattern.contains("**")) {
            //Handle everything before **
            int cutPos = pattern.indexOf("**") + "**".length();
            String cutOff = pattern.substring(cutPos);
            wildcard = wildcard.replace(cutOff, "");
            //Handle everything after **
            params = new AntPathMatcher().extractUriTemplateVariables(pattern, (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));

            if (!params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    cutOff = cutOff.replaceAll("\\{" + entry.getKey() + ":?.*?\\}", entry.getValue());
                }
                wildcard = wildcard.substring(0, wildcard.length() - cutOff.length());
                params.put("**", wildcard);
            }
        }
        if (params != null && !params.isEmpty()) {
            return params;
        }
        params = new HashMap<String, String>();
        if (wildcard != null) {
            params.put("**", wildcard);
        }

        return params;
    }

    public static String getParam(String param, HttpServletRequest req) {
        return getParams(req).get(param);
    }

    public static String extractRequestWildcard(HttpServletRequest req) {
        return getParams(req).get("**");
    }

    public static String extractIdentifier(String identifier, HttpServletRequest req) {
        if (identifier == null) {
            return getParam("**", req);
        }
        return identifier;
    }

}
