package de.fusion.deluxecounter.spigot.commands;

import de.fusion.deluxecounter.spigot.DeluxeCounter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeluxeCounterCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender commandSender, Command cmd, String s,
      String[] args) {

    if (args.length == 0) {
      commandSender
          .sendMessage(DeluxeCounter.getConfiguration().getPath("Commands.Help").getStringList());
    } else if (args.length == 1) {
      if (args[0].equalsIgnoreCase("about")) {
        commandSender.sendMessage(
            DeluxeCounter.getPrefix() + "This server is running DeluxeCounter " + DeluxeCounter
                .getInstance().getDescription().getVersion() + " by FusionCoding");
      } else if (args[0].equalsIgnoreCase("reload")) {
        if (commandSender.hasPermission("deluxecounter.perform.reload")) {
          DeluxeCounter.getConfiguration().reload();
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.Reloaded").getString());
        } else {
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      } else if (args[0].equalsIgnoreCase("toggle")) {
        if (commandSender instanceof Player) {
          if (commandSender.hasPermission("deluxecounter.perform.toggle")) {
            if (DeluxeCounter.getInstance().getToggledPlayers().contains(commandSender.getName())) {
              DeluxeCounter.getInstance().getToggledPlayers().remove(commandSender.getName());
              commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
                  .getPath("Commands.Toggle.Other.Disabled").getString());
            } else {
              DeluxeCounter.getInstance().getToggledPlayers().add(commandSender.getName());
              commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
                  .getPath("Commands.Toggle.Other.Enabled").getString());
            }
          } else {
            commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
                .getPath("Commands.NoPermissions").getString());
          }
        } else {
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.NoPlayer").getString());
        }
      } else if (args[0].equalsIgnoreCase("allow")) {
        if (commandSender.hasPermission("deluxecounter.perform.allow")) {
          DeluxeCounter.getInstance().setAllowConnections(true);
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.AllowConnections").getString());

        } else {
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      } else if (args[0].equalsIgnoreCase("disallow")) {
        if (commandSender.hasPermission("deluxecounter.perform.disallow")) {
          DeluxeCounter.getInstance().setAllowConnections(false);
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.DisallowConnections").getString());

        } else {
          commandSender.sendMessage(DeluxeCounter.getPrefix() + DeluxeCounter.getConfiguration()
              .getPath("Commands.NoPermissions").getString());
        }
      }
    }
    return true;
  }
}
