package me.pyrey.commands;

import me.pyrey.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Chat extends Command {

    public Chat() {
        super("chat", Main.PERMISSION);
    }

    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer pp = (ProxiedPlayer) sender;

        if(!pp.hasPermission(Main.PERMISSION)) {
            pp.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to do this!"));
            return;
        }

        if (args.length == 1) {

            // INVALID CHAT
            if (!args[0].equalsIgnoreCase("all") && !args[0].equalsIgnoreCase("staff")
            && !args[0].equalsIgnoreCase("a") && !args[0].equalsIgnoreCase("s")) {
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/chat <all|staff|a|s>");
                return;
            }

            boolean staff = args[0].equalsIgnoreCase("staff") || args[0].equalsIgnoreCase("s");
            Main.getInstance().getDatabase().setChatEnabled(pp.getUniqueId(), staff);

            String string = staff ? ChatColor.RED + "STAFF" : ChatColor.RED + "ALL";
            sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Your chat has been set to " + string + ChatColor.GRAY + ".");
        } else {
            // NO ARGUMENTS
            sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/chat <all|staff|a|s>");
        }

    }

    private void sendMessage(ProxiedPlayer pp, String text) {
        pp.sendMessage(new TextComponent(text));
    }

}
