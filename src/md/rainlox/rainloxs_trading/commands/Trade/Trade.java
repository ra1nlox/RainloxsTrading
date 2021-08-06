package md.rainlox.rainloxs_trading.commands.Trade;

import md.rainlox.rainloxs_trading.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class Trade implements CommandExecutor {

    private final Main main;


    public Trade(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender senderSender, Command cmd, String label, String[] args) {
        if (!(senderSender instanceof Player)) {
            senderSender.sendMessage(ChatColor.RED + "Command sender must be a player instance!");
            return true;
        }
        if (args.length == 0) {
            return false;
        }

        if (args.length > 1) {
            senderSender.sendMessage(ChatColor.RED + "Too many arguments");
            return false;
        }

        Player sender = (Player) senderSender;
        Player receiver = Bukkit.getServer().getPlayer(args[0]);
        if (receiver == null) {
            sender.sendMessage(ChatColor.RED + Objects.requireNonNull(main.config.getString("messages.error")));
            return true;
        }
        double[] sCoords = {sender.getLocation().getX(), sender.getLocation().getY(), sender.getLocation().getZ()};
        double[] rCoords = {receiver.getLocation().getX(), receiver.getLocation().getY(),
                receiver.getLocation().getZ()};

//        sender coords 10 50 10
//        margin1 20 60 20
//        margin2 0 40 0
//        receiver coords 15 55 6

        double[] range0 = {sCoords[0] + 10, sCoords[1] + 10, sCoords[2] + 10};
        double[] range1 = {sCoords[0] - 10, sCoords[1] - 10, sCoords[2] - 10};

        boolean xx1 = rCoords[0] <= range0[0] & rCoords[0] >= range1[0];
        boolean yy1 = rCoords[1] <= range0[1] & rCoords[1] >= range1[1];
        boolean zz1 = rCoords[2] <= range0[2] & rCoords[2] >= range1[2];

        if (xx1 & yy1 & zz1) {
            ItemStack item = sender.getInventory().getItemInMainHand();
            ItemStack oldItem = sender.getInventory().getItemInMainHand();
            PlayerInventory playerInventory = receiver.getInventory();
            if (playerInventory.getItemInMainHand().getType() == Material.AIR) {
                playerInventory.setItemInMainHand(item);
                oldItem.setAmount(0);
                sender.sendMessage(ChatColor.GREEN + Objects.requireNonNull(main.config.getString("messages.success")) + receiver.getDisplayName());
                return true;
            } else {
                for (int i = 0; i < 36; i++) {
                    if (playerInventory.getItem(i) == null) {
                        playerInventory.setItem(i, item);
                        oldItem.setAmount(0);
                        sender.sendMessage(ChatColor.GREEN + Objects.requireNonNull(main.config.getString("messages.success")));
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + Objects.requireNonNull(main.config.getString("messages.error")));
                receiver.sendMessage(ChatColor.RED + Objects.requireNonNull(main.config.getString("messages.cleanInv")));
                return true;
            }
        } else {
            sender.sendMessage(Objects.requireNonNull(main.config.getString("messages.errors.tooFarError")));
        }
        return false;
    }
}
