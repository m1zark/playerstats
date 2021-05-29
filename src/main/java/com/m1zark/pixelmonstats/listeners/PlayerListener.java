package com.m1zark.pixelmonstats.listeners;

import com.m1zark.pixelmonstats.PixelmonStats;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerListener {
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player player){
        PixelmonStats.getInstance().getSql().addPlayerData(player.getUniqueId());
    }
}
