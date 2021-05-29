package com.m1zark.pixelmonstats.commands;

import com.m1zark.pixelmonstats.PixelmonStats;
import com.m1zark.pixelmonstats.enums.StatTypes;
import com.m1zark.pixelmonstats.utils.PStats;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

public class Leaderboard implements CommandExecutor {
    @Override public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        List<PStats> statsList = PixelmonStats.getInstance().getSql().getAllPlayerData();

        if(args.getOne("type").isPresent()) {
            StatTypes tier = (StatTypes) args.getOne("type").get();
        }

        return CommandResult.success();
    }
}
