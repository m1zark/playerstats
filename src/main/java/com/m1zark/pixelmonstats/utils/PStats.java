package com.m1zark.pixelmonstats.utils;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PStats {
    private String playerUUID;
    private int wildDefeated = 0;
    private int bossDefeated = 0;
    private int megaDefeated = 0;
    private int shinyDefeated = 0;
    private int shinyCaptured = 0;
    private int legendCaptured = 0;
    private int legendDefeated = 0;
    private int ultrabeastCaptured = 0;
    private int ultrabeastDefeated = 0;
    private int[] pokemonDefeated = new int[EnumSpecies.values().length + 1];
    private int[] pokemonCaptured = new int[EnumSpecies.values().length + 1];

    public PStats(String id, int wildDefeated, int bossDefeated, int megaDefeated, int shinyDefeated, int shinyCaptured, int legendDefeated, int legendCaptured, int ultrabeastCaptured, int ultrabeastDefeated, int[] pokemonDefeated, int[] pokemonCaptured) {
        this.playerUUID = id;
        this.wildDefeated = wildDefeated;
        this.bossDefeated = bossDefeated;
        this.megaDefeated = megaDefeated;
        this.shinyDefeated = shinyDefeated;
        this.shinyCaptured = shinyCaptured;
        this.legendDefeated = legendDefeated;
        this.legendCaptured = legendCaptured;
        this.ultrabeastCaptured = ultrabeastCaptured;
        this.ultrabeastDefeated = ultrabeastDefeated;
        this.pokemonDefeated = pokemonDefeated;
        this.pokemonCaptured = pokemonCaptured;
    }
}
