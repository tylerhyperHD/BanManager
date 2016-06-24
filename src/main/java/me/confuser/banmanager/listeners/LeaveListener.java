package me.confuser.banmanager.listeners;

import me.confuser.banmanager.BanManager;
import me.confuser.banmanager.data.PlayerHistoryData;
import me.confuser.bukkitutil.listeners.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import me.confuser.banmanager.PluginLogger;

public class LeaveListener extends Listeners<BanManager> {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (plugin.getConfiguration().isWarningMutesEnabled()) {
            plugin.getPlayerWarnStorage().removeMute(event.getPlayer().getUniqueId());
        }

        if (plugin.getConfiguration().isLogIpsEnabled()) {
            final PlayerHistoryData data = plugin.getPlayerHistoryStorage().remove(event.getPlayer().getUniqueId());

            if (data == null) {
                PluginLogger.warn("Could not find " + event.getPlayer().getName() + " session history, perhaps they "
                        + "disconnected too quickly?");
                return;
            }

            data.setLeave(System.currentTimeMillis() / 1000L);

            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

                @Override
                public void run() {
                    try {
                        plugin.getPlayerHistoryStorage().create(data);
                    } catch (SQLException e) {
                        PluginLogger.warn(e);
                    }
                }
            });
        }
    }
}
