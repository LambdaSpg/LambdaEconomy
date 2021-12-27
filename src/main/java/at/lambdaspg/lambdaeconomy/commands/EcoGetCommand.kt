package at.lambdaspg.lambdaeconomy.commands

import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoCore
import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoHandler
import at.lambdaspg.lambdaeconomy.MessageManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class EcoGetCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val p: Player = sender
            if (p.hasPermission("lambda.cmd.eco.get")) {
                if(args.size == 1){
                    if(!p.hasPermission("lambda.cmd.eco.get.others")) {
                        MessageManager.noPermission(p);
                        return false;
                    }
                    val target: Player? = Bukkit.getPlayer(args[0])
                    if(target != null){
                        MessageManager.sendPlayerInfo("The Player ${target.name} has ${ecoHandler.getMoney(p.uniqueId)}${ecoCore.currencySign()}", p)
                    }else MessageManager.sendPlayerError("This Player does not exist", p);
                }else {
                    if(ecoHandler.containsPlayer(p.uniqueId)) {
                        MessageManager.sendPlayerInfo("You have ${ecoHandler.getMoney(p.uniqueId)}${ecoCore.currencySign()}", p)
                    }else MessageManager.sendPlayerError("You do not have an account, this is almost certainly a mistake, please contact an administrator.", p)
                }
            }
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val list: ArrayList<String> = ArrayList()
        if(args.size == 1){
            Bukkit.getOnlinePlayers().forEach { all -> list.add(all.name) }
        }

        return list;
    }


}