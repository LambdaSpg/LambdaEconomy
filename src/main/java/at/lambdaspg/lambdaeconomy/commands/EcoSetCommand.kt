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
                        MessageManager.sendPlayerError("Bitte verwende /ecoset money player", p)
                        return false;
                    }

                    if(ecoCore.setPlayer(p, money)) {
                        MessageManager.sendPlayerGood("Du hast dein Geld auf ${money}€ gesetzt", p)
                        MessageManager.sendConsoleEco("Das Geld von ${p.name} wurde auf ${money}${ecoCore.currencySign()} gesetzt (von: ${p.name})")
                    }else {
                        MessageManager.sendPlayerError("Etwas ist schiefgegangen, bitte versuche es später erneut", p)
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
                        MessageManager.sendPlayerGood("Du hast das Geld von ${target.name} auf ${money}€ gesetzt", p)
                        MessageManager.sendConsoleEco("Das Geld von ${target.name} wurde auf ${money}${ecoCore.currencySign()} gesetzt (von: ${p.name})")
                    }else {
                        MessageManager.sendPlayerError("Etwas ist schiefgegangen, bitte versuche es später erneut", p)
                    }
                }else MessageManager.sendPlayerError("Bitte verwende /ecoset <amount> [player]", p)
            } else MessageManager.noPermission(p)
        }

        return false
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val list: ArrayList<String> = ArrayList()
        if(args.size == 1){
            list.add("amount")
        }else if(args.size == 2){
            Bukkit.getOnlinePlayers().forEach { all -> list.add(all.name) }
        }

        return list;
    }
}