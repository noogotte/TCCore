package fr.techcode.plugin.core.command.util;

import org.bukkit.ChatColor;

public class UsageGenerator {

    // Builder for fast generation
    private StringBuilder builder;

    /**
     * Create an usage generator
     */
    public UsageGenerator() {
        builder = new StringBuilder(256);
    }

    /**
     * Generate usage from information
     *
     * @param access | Canonical access
     * @param args   | Arguments
     * @param desc   | Description
     */
    public String quickGen(String access, String args, String desc) {
        String persit = builder
                .append(ChatColor.RED).append(access).append((args.isEmpty()) ? "" : ' ')
                .append(ChatColor.GOLD).append(args)
                .append(ChatColor.WHITE).append(" | ")
                .append(ChatColor.YELLOW).append(desc).toString();
        builder.setLength(0);
        return persit;
    }

    /**
     * Generate usage from information
     *
     * @param access | Canonical access
     * @param args   | Arguments
     * @param desc   | Description
     */
    public static String generate(String access, String args, String desc) {
        return (new StringBuilder(64))
                .append(ChatColor.RED).append(access).append((args.isEmpty()) ? "" : ' ')
                .append(ChatColor.GOLD).append(args)
                .append(ChatColor.WHITE).append(" | ")
                .append(ChatColor.YELLOW).append(desc).toString();
    }

}
