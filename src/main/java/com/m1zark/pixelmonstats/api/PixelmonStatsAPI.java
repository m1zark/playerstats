package com.m1zark.pixelmonstats.api;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.m1zark.pixelmonstats.utils.PStats;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PixelmonStatsAPI {
    private PStats stats;
    private UUID uuid;

    public PixelmonStatsAPI(UUID uuid) {
        this.stats = PixelmonStats.getInstance().getSql().getPlayerData(uuid);
        this.uuid = uuid;
    }

    public void updatePlayer() {
        PixelmonStats.getInstance().getSql().updatePlayerData(this.uuid, this.stats);
    }
}
