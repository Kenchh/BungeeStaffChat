package me.pyrey;

import me.pyrey.commands.Chat;
import me.pyrey.commands.Staff;
import me.pyrey.commands.StaffChat;
import me.pyrey.config.MySQLConfig;
import me.pyrey.database.ConnectionPoolManager;
import me.pyrey.database.SQLManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static String PERMISSION = "swine.staffchat";
    public static String JOINS_PERMISSION = "swine.staffchat.joins";
    public static String PREFIX = ChatColor.translateAlternateColorCodes('&', "&d&lSTAFF&8&l> ");

    private MySQLConfig sqlConfig;
    private SQLManager sqlManager;

    private static Main instance;
    private LuckPerms api;

    @Override
    public void onDisable() {
        this.getDatabase().onDisable();
    }

    @Override
    public void onEnable() {

        instance = this;
        sqlConfig = new MySQLConfig();

        api = LuckPermsProvider.get();

        ConnectionPoolManager pool = new ConnectionPoolManager(sqlConfig.hostname, sqlConfig.port, sqlConfig.username, sqlConfig.password, sqlConfig.database);
        this.sqlManager = new SQLManager(pool, "staffchat");

        log("[Swine-StaffChat] Enabled!");

        getProxy().getPluginManager().registerCommand(this, new StaffChat());
        getProxy().getPluginManager().registerCommand(this, new Chat());
        getProxy().getPluginManager().registerCommand(this, new Staff());
        getProxy().getPluginManager().registerListener(this, new Events());

    }

    public SQLManager getDatabase() {
        return sqlManager;
    }

    private void log(String s) {
        System.out.println(s);
    }

    public static Main getInstance() {
        return instance;
    }

    public void send(ProxiedPlayer pp, String message){

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission(Main.PERMISSION)) continue;
            User u = api.getUserManager().getUser(pp.getUniqueId());
            if (u == null) continue;

            if(!p.getUniqueId().equals(pp.getUniqueId())
                    && (!Main.getInstance().getDatabase().hasAlertsEnabled(p.getUniqueId())
                        || !Main.getInstance().getDatabase().hasChatAlertsEnabled(p.getUniqueId()))) {
                continue;
            }

            String rank = u.getPrimaryGroup();

            /*
                Group group = api.getGroupManager().getGroup(rank);
                String prefix = group == null ? rank.toUpperCase() : group.getDisplayName() == null
                        ? rank.toUpperCase() : group.getDisplayName();
             */

            CachedDataManager data = u.getCachedData();
            String prefix = ChatColor.translateAlternateColorCodes('&',
                    data.getMetaData().getPrefix() == null ? rank.toUpperCase() : data.getMetaData().getPrefix());

            sendMessage(p, Main.PREFIX + ChatColor.GRAY + prefix +
                    pp.getName() + ChatColor.WHITE + ": " + message);
        }
    }

    public void sendJoinLeaveMsg(ProxiedPlayer pp, boolean join) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (!p.hasPermission(Main.PERMISSION)) continue;
            User u = api.getUserManager().getUser(pp.getUniqueId());
            if (u == null) continue;

            if(join == false && p.getUniqueId().equals(pp.getUniqueId())) {
                continue;
            }

            if(!p.getUniqueId().equals(pp.getUniqueId()) &&
                    (!Main.getInstance().getDatabase().hasAlertsEnabled(p.getUniqueId()) || !Main.getInstance().getDatabase().hasJoinsEnabled(p.getUniqueId()))) {
                continue;
            }

            String rank = u.getPrimaryGroup();

            /*
                Group group = api.getGroupManager().getGroup(rank);
                String prefix = group == null ? rank.toUpperCase() : group.getDisplayName() == null
                        ? rank.toUpperCase() : group.getDisplayName();
             */

            CachedDataManager data = u.getCachedData();
            String prefix = ChatColor.translateAlternateColorCodes('&',
                    data.getMetaData().getPrefix() == null ? rank.toUpperCase() : data.getMetaData().getPrefix());

            if(join) {
                sendMessage(p, Main.PREFIX + ChatColor.GRAY + prefix +
                        pp.getName() + ChatColor.GREEN + " has joined the network.");
            } else {
                sendMessage(p, Main.PREFIX + ChatColor.GRAY + prefix +
                        pp.getName() + ChatColor.GRAY + " has left the network.");
            }
        }
    }

    private void sendMessage(ProxiedPlayer pp, String message){
        pp.sendMessage(new TextComponent(message));
    }

}
