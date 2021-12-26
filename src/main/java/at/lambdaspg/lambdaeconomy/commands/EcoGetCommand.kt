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
                        MessageManager.sendPlayerInfo("Der Spieler ${target.name} hat ${ecoHandler.getMoney(p.uniqueId)}${ecoCore.currencySign()}", p)
                    }else MessageManager.sendPlayerError("Dieser Spieler existiert nicht", p);
                }else {
                    if(ecoHandler.containsPlayer(p.uniqueId)) {
                        MessageManager.sendPlayerInfo("Du hast ${ecoHandler.getMoney(p.uniqueId)}${ecoCore.currencySign()}", p)
                    }else MessageManager.sendPlayerError("Du besitzt keinen Account, dies ist ziemlich sicher ein Fehler, bitte melde dich bei einem Administrator", p)
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