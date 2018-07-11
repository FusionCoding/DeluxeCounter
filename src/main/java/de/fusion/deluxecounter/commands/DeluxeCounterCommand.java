package de.fusion.deluxecounter.commands;

import net.md_5.bungee.api.CommandSender;
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
        }
    }
}
