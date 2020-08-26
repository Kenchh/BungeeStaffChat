package me.pyrey.commands;

import me.pyrey.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class Staff extends Command {

    public Staff() {
        super("staff", Main.PERMISSION, "s");
    }

    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer pp = (ProxiedPlayer) sender;

        if(!pp.hasPermission(Main.PERMISSION)) {
            pp.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to do this!"));
            return;
        }

        if(args.length == 0) {
            help(pp);
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("alerts")) {
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff alerts <on|off>");
                return;
            }

            if(args[0].equalsIgnoreCase("chat")) {
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff chat <on|off>");
                return;
            }

            if(args[0].equalsIgnoreCase("joins")) {
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff joins <on|off>");
                return;
            }

            help(pp);

            return;
        }

        if(args.length >= 2) {
            if(args[0].equalsIgnoreCase("alerts")) {

                if(!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
                    sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff alerts <on|off>");
                    return;
                }

                boolean hasEnabled = args[1].equalsIgnoreCase("on");
                Main.getInstance().getDatabase().setAlertsEnabled(pp.getUniqueId(), hasEnabled);

                String toggle = hasEnabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "All staff alerts are now " + toggle + ChatColor.GRAY + ".");
                return;
            }

            if(args[0].equalsIgnoreCase("chat")) {

                if(!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
                    sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff chat <on|off>");
                    return;
                }

                boolean chat = args[1].equalsIgnoreCase("on");
                Main.getInstance().getDatabase().setChatAlertsEnabled(pp.getUniqueId(), chat);

                String toggle = chat ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "You staff-chat alerts are now " + toggle + ChatColor.GRAY + ".");
                return;
            }

            if(args[0].equalsIgnoreCase("joins")) {

                if(!args[1].equalsIgnoreCase("on") && !args[1].equalsIgnoreCase("off")) {
                    sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Usage: " + ChatColor.RED + "/staff joins <on|off>");
                    return;
                }

                boolean hasEnabled = args[1].equalsIgnoreCase("on");
                Main.getInstance().getDatabase().setJoinsEnabled(pp.getUniqueId(), hasEnabled);

                String toggle = hasEnabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
                sendMessage(pp, Main.PREFIX + ChatColor.GRAY + "Your staff join/leave messages are now " + toggle + ChatColor.GRAY + ".");
                return;
            }

            help(pp);

            return;
        }
    }

    private void help(ProxiedPlayer pp) {
        for(String msg : Arrays.asList(
                Main.PREFIX + ChatColor.LIGHT_PURPLE + "/s alerts <on|off>",
                ChatColor.GRAY + "Turns on/off all alerts, so staffchat & joins",
                Main.PREFIX + ChatColor.LIGHT_PURPLE + "/s chat <on|off>",
                ChatColor.GRAY + "Turns on/off staffchat messages",
                Main.PREFIX + ChatColor.LIGHT_PURPLE + "/s joins <on|off>",
                ChatColor.GRAY + "Turns on/off join and leave messages from staff members"
        )) {
            sendMessage(pp, msg);
        }
    }

    private void sendMessage(ProxiedPlayer pp, String text) {
        pp.sendMessage(new TextComponent(text));
    }

}
