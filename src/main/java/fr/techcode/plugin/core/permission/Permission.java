package fr.techcode.plugin.core.permission;

import fr.techcode.plugin.core.TCCore;
import fr.techcode.plugin.core.command.handle.CommandSource;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Permission {

    // Deny message by default
    public String deny;
    // Permission to check
    private String permission;
    // Type of check
    private boolean checkConsole;

    /**
     * Create a permission representation
     */
    public Permission() {
        this(true);
    }

    /**
     * Create a permission representation
     *
     * @param display | Display or not the deny message
     */
    public Permission(boolean display) {
        deny = (display) ? (new StringBuilder(64)).append(ChatColor.RED).append("Vous n'avez pas le droit de faire cela.").toString() : null;
        permission = null;
        checkConsole = false;
    }

    /**
     * Get permission
     *
     * @return | Permission check
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Set permission
     *
     * @param permission | Permission check
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Get if the console call is allow
     *
     * @return | Console call allow
     */
    public boolean isCheckConsole() {
        return checkConsole;
    }

    /**
     * Set if the console call is check
     *
     * @param check | Console call check
     */
    public void setCheckConsole(boolean check) {
        checkConsole = check;
    }

    /**
     * Do check and return status
     *
     * @param sender | Sender of the command
     * @return | Status of check
     */
    public boolean check(CommandSender sender) {
        return checkConsole(sender) || checkPlayer(sender);
    }

    /**
     * Set deny message for this permission
     *
     * @param deny | New deny message
     */
    public void setDeny(String deny) {
        this.deny = deny;
    }

    /**
     * Release all resources
     */
    public void dispose() {
        permission = null;
        deny = null;
    }

    /**
     * Do check console
     *
     * @param sender | Sender of the command
     * @return | Status of check
     */
    private boolean checkConsole(CommandSender sender) {
        if (checkConsole && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(deny);
            return true;
        }
        return false;
    }

    /**
     * Do check player
     *
     * @param sender | Sender of the command
     * @return | Status of check
     */
    public boolean checkPlayer(CommandSender sender) {
        if (permission == null || TCCore.getPermission().has(sender, permission)) return false;
        if (deny != null) sender.sendMessage(deny);
        return true;
    }

}
