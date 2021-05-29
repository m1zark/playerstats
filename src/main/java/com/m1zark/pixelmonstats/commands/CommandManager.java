package com.m1zark.pixelmonstats.commands;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public void registerCommands(PixelmonStats plugin) {
        Sponge.getCommandManager().register(plugin, stats, "pstats","pixelmonstats");
    }

    private CommandSpec stats = CommandSpec.builder()
            .arguments(GenericArguments.optionalWeak(GenericArguments.enumValue(Text.of("pokemon"), EnumSpecies.class)))
            .permission("pixelmonstats.player.command")
            .executor(new CheckStats())
            .build();
}
