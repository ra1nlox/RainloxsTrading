package md.rainlox.rainloxs_trading;

import md.rainlox.rainloxs_trading.commands.Trade.Trade;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public final Logger log = Logger.getLogger("RainloxsTrading");

    public FileConfiguration config;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("trade")).setExecutor(new Trade(this));

        config = this.getConfig();

        log.info(config.getString("messages.onEnable"));
    }

    @Override
    public void onDisable() {
        log.info(config.getString("messages.onDisable"));
    }

}
