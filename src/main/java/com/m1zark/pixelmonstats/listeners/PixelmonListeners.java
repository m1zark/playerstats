package com.m1zark.pixelmonstats.listeners;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.m1zark.pixelmonstats.utils.PStats;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.forms.EnumMega;
import com.pixelmonmod.pixelmon.enums.forms.EnumPrimal;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Arrays;

public class PixelmonListeners {
    @SubscribeEvent
    public void pokemonCapture(CaptureEvent.SuccessfulCapture event) {
        Player p = (Player) event.player;
        PStats data = PixelmonStats.getInstance().getSql().getPlayerData(p.getUniqueId());
        EntityPixelmon pokemon = event.getPokemon();

        if(pokemon.getStoragePokemonData().isShiny()) data.setShinyCaptured(data.getShinyCaptured() + 1);
        if(EnumSpecies.legendaries.contains(pokemon.getName()) && !checkMega(pokemon)) data.setLegendCaptured(data.getLegendCaptured() + 1);
        if(EnumSpecies.ultrabeasts.contains(pokemon.getName())) data.setUltrabeastCaptured(data.getUltrabeastCaptured() + 1);

        try {
            int[] temp = Arrays.copyOf(data.getPokemonCaptured(), 891);
            int n = pokemon.getSpecies().getNationalPokedexInteger();
            temp[n] = temp[n] + 1;
            data.setPokemonCaptured(temp);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            if (data.getPokemonCaptured() == null) {
                // empty if block
            }
        }

        PixelmonStats.getInstance().getSql().updatePlayerData(p.getUniqueId(), data);
    }

    @SubscribeEvent
    public void onBattleEnd(BeatWildPixelmonEvent event) {
        Player p = (Player) event.player;
        PStats data = PixelmonStats.getInstance().getSql().getPlayerData(p.getUniqueId());

        for (PixelmonWrapper wrapper : event.wpp.allPokemon) {
            EntityPixelmon pokemon =  wrapper.entity;

            if(checkMega(pokemon)) data.setMegaDefeated(data.getMegaDefeated() + 1);
            if(pokemon.isBossPokemon() && !checkMega(pokemon)) data.setBossDefeated(data.getBossDefeated() + 1);
            if(pokemon.getStoragePokemonData().isShiny() && !checkMega(pokemon)) data.setShinyDefeated(data.getShinyDefeated() + 1);
            if(pokemon.getSpecies().isLegendary() && !checkMega(pokemon)) data.setLegendDefeated(data.getLegendDefeated() + 1);
            if(pokemon.getSpecies().isUltraBeast()) data.setUltrabeastDefeated(data.getUltrabeastDefeated() + 1);

            if(!pokemon.isBossPokemon() && !checkMega(pokemon) && !pokemon.getStoragePokemonData().isShiny() && !EnumSpecies.legendaries.contains(pokemon.getName()) && !EnumSpecies.ultrabeasts.contains(pokemon.getName())) {
                data.setWildDefeated(data.getWildDefeated() + 1);
            }

            try {
                int[] temp = Arrays.copyOf(data.getPokemonDefeated(), 891);
                int n = pokemon.getSpecies().getNationalPokedexInteger();
                temp[n] = temp[n] + 1;
                data.setPokemonDefeated(temp);
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                // empty catch block
            }

            PixelmonStats.getInstance().getSql().updatePlayerData(p.getUniqueId(), data);
        }
    }

    private boolean checkMega(EntityPixelmon pokemon) {
        return pokemon.getFormEnum() == EnumMega.Mega || pokemon.getFormEnum() == EnumPrimal.PRIMAL || pokemon.getFormEnum() == EnumMega.MegaX || pokemon.getFormEnum() == EnumMega.MegaY;
    }
}
