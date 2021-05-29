package com.m1zark.pixelmonstats.utils;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.m1zark.pixelmonstats.StatsInfo;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.rojo8399.placeholderapi.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class Placeholders {
    public static void register(Object plugin) {
        PlaceholderService placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService.class);
        placeholderService.loadAll(new Placeholders(), plugin).forEach(b -> {
            try {
                b.version(StatsInfo.VERSION).author("m1zark").buildAndRegister();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Placeholder(id="defeatedpokemon")
    public String pokemonDefeated(@Source Player player, @Token String token) throws NoValueException {
        try {
            PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
            int i = Integer.parseInt(token);

            return i >= 1 && i <= EnumSpecies.values().length+1 ? String.valueOf(stats.getPokemonDefeated()[i]) : "0";
        } catch (NumberFormatException e) {
            throw new NoValueException(token + " isn't a valid integer.");
        }
    }

    @Placeholder(id="capturedpokemon")
    public String pokemonCaptured(@Source Player player, @Token String token) throws NoValueException {
        try {
            PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
            int i = Integer.parseInt(token);

            return i >= 1 && i <= EnumSpecies.values().length + 1 ? String.valueOf(stats.getPokemonCaptured()[i]) : "0";
        } catch (NumberFormatException e) {
            throw new NoValueException(token + " isn't a valid integer.");
        }
    }

    @Placeholder(id="wilddefeated")
    public String wildDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getWildDefeated());
    }

    @Placeholder(id="bossdefeated")
    public String bossDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getBossDefeated());
    }

    @Placeholder(id="megadefeated")
    public String megaDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getMegaDefeated());
    }

    @Placeholder(id="legenddefeated")
    public String legendsDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getLegendDefeated());
    }

    @Placeholder(id="legendcaptured")
    public String legendsCaptured(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getLegendCaptured());
    }

    @Placeholder(id="ultrabeastdefeated")
    public String ultrabeastDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getUltrabeastDefeated());
    }

    @Placeholder(id="ultrabeastcaptured")
    public String ultrabeastCaptured(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getUltrabeastCaptured());
    }

    @Placeholder(id="shinydefeated")
    public String shiniesDefeated(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getShinyDefeated());
    }

    @Placeholder(id="shinycaptured")
    public String shiniesCaptured(@Source Player player) {
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());
        return String.valueOf(stats.getShinyCaptured());
    }
}
