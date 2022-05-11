package at.lambdaspg.lambdaeconomy.commands

import at.lambdaspg.lambdaeconomy.commands.subcommands.GetSubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BalanceCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            GetSubCommand().perform(sender, args);
        }
        return false;
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return GetSubCommand().tab(args)
    }

}