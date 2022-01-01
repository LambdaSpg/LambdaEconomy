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
import java.lang.NumberFormatException

class EcoSetCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val p: Player = sender
            if(p.hasPermission("lambda.cmd.eco.set")){
                if(args.size == 1){
                    var money: Double = 0.0
                    try {
                       money = args[0].toDouble()
                    }catch(e: NumberFormatException){
                        MessageManager.sendPlayerError("Please use /ecoset money player", p)
                        return false;
                    }

                    if(ecoCore.setPlayer(p, money)) {
                        MessageManager.sendPlayerGood("You set your balance to ${money}${ecoCore.currencySign()}", p)
                        MessageManager.sendConsoleEco("The balance of ${p.name} has been set to ${money}${ecoCore.currencySign()} (by: ${p.name})")
                    }else {
                        MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                    }
                }else if(args.size == 2){
                    var money: Double = 0.0
                    try {
                        money = args[0].toDouble()
                    }catch(e: NumberFormatException){
                        e.printStackTrace()
                    }
                    val target: Player? = Bukkit.getPlayer(args[1])

                    if(target != null && ecoCore.setPlayer(target, money)) {
                        MessageManager.sendPlayerGood("You have set the balance of ${target.name} to ${money}${ecoCore.currencySign()}", p)
                        MessageManager.sendConsoleEco("The balance of ${target.name} has been set to ${money}${ecoCore.currencySign()} (by: ${p.name})")
                    }else {
                        MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                    }
                }else MessageManager.sendPlayerError("Please use /ecoset money player", p)
            } else MessageManager.noPermission(p)
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list: ArrayList<String> = ArrayList()
        if(args.size == 1){
            list.add("amount")
        }else if(args.size == 2){
            Bukkit.getOnlinePlayers().forEach { all -> list.add(all.name) }
        }

        return list;
    }
}