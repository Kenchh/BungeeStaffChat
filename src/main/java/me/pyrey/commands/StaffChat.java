package me.pyrey.commands;

import me.pyrey.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedData;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChat extends Command {

    public StaffChat() {
        super("staffchat", Main.PERMISSION, "sc");
    }

    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer pp = (ProxiedPlayer) sender;

        if(!pp.hasPermission(Main.PERMISSION)) {
            pp.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to do this!"));
            return;
        }

        if (args.length >= 1) {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++)  sb.append(args[i]).append(" ");

            Main.getInstance().send(pp, sb.toString());

        } else {
            sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/sc <message>");
        }

    }

    private void sendMessage(ProxiedPlayer pp, String text) {
        pp.sendMessage(new TextComponent(text));
    }

}
