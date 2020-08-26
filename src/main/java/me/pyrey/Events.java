package me.pyrey;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener {

    @EventHandler
    public void onChat(ChatEvent e) {

        if (!(e.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer pp = (ProxiedPlayer) e.getSender();

        if(!pp.hasPermission(Main.PERMISSION)) {
            Main.getInstance().getDatabase().setChatEnabled(pp.getUniqueId(), false);
            return;
        }

        if (e.isCommand() || e.isProxyCommand() || e.isCancelled()) return;
        if (!Main.getInstance().getDatabase().hasChatEnabled(pp.getUniqueId())) return;

        e.setCancelled(true);
        Main.getInstance().send(pp, e.getMessage());
    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {

        ProxiedPlayer pp = e.getPlayer();

        if(!pp.hasPermission(Main.PERMISSION) && !pp.hasPermission(Main.JOINS_PERMISSION)) return;

        Main.getInstance().getDatabase().createPlayer(e.getPlayer().getUniqueId());
        Main.getInstance().sendJoinLeaveMsg(pp, true);

    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {

        ProxiedPlayer pp = e.getPlayer();

        if(!pp.hasPermission(Main.PERMISSION) && !pp.hasPermission(Main.JOINS_PERMISSION)) return;

        Main.getInstance().sendJoinLeaveMsg(pp, false);

    }

}
