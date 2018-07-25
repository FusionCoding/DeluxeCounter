package de.fusion.deluxecounter.bungee.commands;

import de.fusion.deluxecounter.bungee.DeluxeCounter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DeluxeCounterCommand extends Command {

  public DeluxeCounterCommand(String name) {
    super(name);
  }

  @Override
  public String[] getAliases() {
    return new String[]{"dc", "counter"};
  }

  @Override
  public void execute(CommandSender commandSender, String[] args) {

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
        if (commandSender instanceof ProxiedPlayer) {
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
  }
}
