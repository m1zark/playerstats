package com.m1zark.pixelmonstats;

import com.google.inject.Inject;
import com.m1zark.pixelmonstats.commands.CommandManager;
import com.m1zark.pixelmonstats.storage.DataSource;
import com.m1zark.pixelmonstats.listeners.PixelmonListeners;
import com.m1zark.pixelmonstats.listeners.PlayerListener;
import com.m1zark.pixelmonstats.utils.Placeholders;
import com.pixelmonmod.pixelmon.Pixelmon;
import lombok.Getter;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.AsynchronousExecutor;
import org.spongepowered.api.scheduler.SpongeExecutorService;
import org.spongepowered.api.scheduler.SynchronousExecutor;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(id=StatsInfo.ID,name=StatsInfo.NAME,version=StatsInfo.VERSION,description=StatsInfo.DESCRIPTION,authors="m1zark")
@Getter
public class PixelmonStats {
    @Inject private Logger logger;
    @Inject private PluginContainer pC;
    @Inject @ConfigDir(sharedRoot = false) private Path configDir;
    private static PixelmonStats instance;
    private DataSource sql;
    @SynchronousExecutor private SpongeExecutorService sync;
    @AsynchronousExecutor private SpongeExecutorService async;

    private boolean enabled = true;

    @Listener
    public void onServerStart(GameInitializationEvent event) {
        instance = this;
        StatsInfo.startup();

        this.enabled = StatsInfo.dependencyCheck();
        if(this.enabled) {
            this.sync = Sponge.getScheduler().createSyncExecutor(this);
            this.async = Sponge.getScheduler().createAsyncExecutor(this);

            Pixelmon.EVENT_BUS.register(new PixelmonListeners());
            Sponge.getEventManager().registerListeners(this, new PlayerListener());

            // Initialize data source and creates tables
            this.sql = new DataSource("PIXELMON_PLAYER_DATA");
            this.sql.createTables();

            new CommandManager().registerCommands(this);
        }
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent e) {
        if (Sponge.getPluginManager().isLoaded("placeholderapi")) Placeholders.register(this);
    }

    @Listener public void onServerStop(GameStoppingEvent event) {
        try {
            this.sql.shutdown();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public static PixelmonStats getInstance() { return instance; }

    public Optional<ConsoleSource> getConsole() {
        return Optional.ofNullable(Sponge.isServerAvailable() ? Sponge.getServer().getConsole() : null);
    }
}
