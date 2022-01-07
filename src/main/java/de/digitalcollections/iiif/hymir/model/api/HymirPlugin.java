package de.digitalcollections.iiif.hymir.model.api;

/**
 * This is the root interface for all Hymir Plugins
 */
public interface HymirPlugin {
    /**
     * Just an identifying String, can also include a version number and description.
     *
     * @return the name of the Plugin
     */
    public String name();

    /**
     * A marker interface to filter out buildin plugins
     */
    public interface Buildin extends HymirPlugin {

    }
}
