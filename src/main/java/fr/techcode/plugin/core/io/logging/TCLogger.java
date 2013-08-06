package fr.techcode.plugin.core.io.logging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class TCLogger {

    // Get the minecraft logger
    private Logger logger = Logger.getLogger("Minecraft");
    // Precalculate prefix
    private String prefix;
    // Cached all elements of the next log before build
    private List<String> cached;

    /**
     * Create a logger
     *
     * @param name | Name of the plugin
     */
    public TCLogger(String name) {
        this.prefix = name;
        cached = new ArrayList<String>(16);
    }

    /**
     * Add an element for the next log
     *
     * @param el | Element
     */
    public TCLogger add(Object el) {
        cached.add(String.valueOf(el));
        return this;
    }

    /**
     * Process info next log
     */
    public void info() {
        logger.info(process());
    }

    /**
     * Process severe next log
     */
    public void severe() {
        logger.severe(process());
    }

    /**
     * Process warning next log
     */
    public void warning() {
        logger.warning(process());
    }

    /**
     * Get prefix of the logger
     *
     * @return | Prefix logger
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Release all resource
     */
    public void dispose() {
        cached = null;
        logger = null;
        prefix = null;
    }

    /**
     * Process the next log
     *
     * @return | Next log
     */
    private String process() {
        // Get some stuff & append prefix
        StringBuilder builder = new StringBuilder(256);
        Iterator<String> it = cached.iterator();
        builder.append(prefix);

        // Iterate over all elements
        while (it.hasNext()) {
            builder.append(it.next());
            it.remove();
        }

        // Return next log
        return builder.toString();
    }

}

