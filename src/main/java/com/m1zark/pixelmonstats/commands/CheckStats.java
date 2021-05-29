package com.m1zark.pixelmonstats.commands;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.m1zark.pixelmonstats.utils.PStats;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CheckStats implements CommandExecutor {
    @Override public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = (Player) src;
        PStats stats = PixelmonStats.getInstance().getSql().getPlayerData(player.getUniqueId());

        if(args.getOne("pokemon").isPresent()) {
            EnumSpecies pokemon = (EnumSpecies) args.getOne("pokemon").get();

            player.sendMessage(Text.of(TextColors.GRAY, "Stats for: ", TextColors.GREEN, pokemon.getPokemonName()));
            try {
                player.sendMessage(Text.of(TextColors.AQUA, "Defeated: ", TextColors.GREEN, stats.getPokemonDefeated()[pokemon.getNationalPokedexInteger()]));
                player.sendMessage(Text.of(TextColors.AQUA, "Captured: ", TextColors.GREEN, stats.getPokemonCaptured()[pokemon.getNationalPokedexInteger()]));
            } catch (IndexOutOfBoundsException e) {
                //
            }
        } else {
            player.sendMessage(Text.of(TextColors.AQUA, "Wild Pok\u00E9mon Defeated: ", TextColors.GREEN, stats.getWildDefeated()));
            player.sendMessage(Text.of(TextColors.AQUA, "Boss Pok\u00E9mon Defeated: ", TextColors.GREEN, stats.getBossDefeated()));
            player.sendMessage(Text.of(TextColors.AQUA, "Mega Pok\u00E9mon Defeated: ", TextColors.GREEN, stats.getMegaDefeated()));
            player.sendMessage(Text.of(TextColors.AQUA, "Shiny Pok\u00E9mon Defeated: ", TextColors.GREEN, stats.getShinyDefeated()));
            player.sendMessage(Text.of(TextColors.AQUA, "Shiny Pok\u00E9mon Captured: ", TextColors.GREEN, stats.getShinyCaptured()));
            player.sendMessage(Text.of(TextColors.AQUA, "Legendary Defeated/Captured: ", TextColors.GREEN, stats.getLegendDefeated(), TextColors.GRAY, " / ", TextColors.GREEN, stats.getLegendCaptured()));
            player.sendMessage(Text.of(TextColors.AQUA, "Ultrabeast Defeated/Captured: ", TextColors.GREEN, stats.getUltrabeastDefeated(), TextColors.GRAY, " / ", TextColors.GREEN, stats.getUltrabeastCaptured()));
        }

        return CommandResult.success();
    }
}
