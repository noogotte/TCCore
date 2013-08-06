package fr.techcode.plugin.core.command.handle;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class CommandArguments {

    // String array arguments
    private String[] args;
    // Current start index
    private byte current;

    /**
     * Command arguments wrapper
     *
     * @param args | All arguments
     */
    public CommandArguments(String[] args) {
        this.args = args;
        current = 0;
    }

    /**
     * Get first argument
     *
     * @return | First argument
     */
    public String getFirst() {
        return getArg((byte) 0);
    }

    /**
     * Get second argument
     *
     * @return | Second argument
     */
    public String getSecond() {
        return getArg((byte) 1);
    }

    /**
     * Get third argument
     *
     * @return | Third argument
     */
    public String getThird() {
        return getArg((byte) 2);
    }

    /**
     * Get an argument
     *
     * @param index | Index of request argument
     * @return | String argument or null
     */
    public String getArg(byte index) {
        if (index < 0) return null;

        try {
            return args[current + index];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return null;
        }
    }

    /**
     * Increment current index
     *
     * @return | This
     */
    public CommandArguments incrementAndGet() {
        if (current < Byte.MAX_VALUE) current++;
        return this;
    }

    /**
     * Decrement current index
     *
     * @return | This
     */
    public CommandArguments decrementAndGet() {
        if (current > 0) current--;
        return this;
    }

    /**
     * Get size of Command arguments
     *
     * @return | Command arguments length
     */
    public int size() {
        return args.length - current;
    }

    /**
     * Get a world from an argument
     *
     * @param index | Index of request argument
     * @return | World or null
     */
    public World getWorld(byte index) {
        // Attempt to get argument
        String request = getArg(index);
        if (request == null) return null;

        // Attempt to get World
        return Bukkit.getWorld(request);
    }

}
