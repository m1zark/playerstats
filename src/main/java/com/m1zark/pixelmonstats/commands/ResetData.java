package com.m1zark.pixelmonstats.commands;

import com.m1zark.pixelmonstats.PixelmonStats;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ResetData implements CommandExecutor {
    @Override public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        PixelmonStats.getInstance().getSql().clearTables();

        src.sendMessage(Text.of("All extra pixelmon data has been deleted."));

        return CommandResult.success();
    }
}
