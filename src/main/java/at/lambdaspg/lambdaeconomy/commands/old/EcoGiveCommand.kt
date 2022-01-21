package at.lambdaspg.lambdaeconomy.commands.old

import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class EcoGiveCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val p: Player = sender
            if(p.hasPermission("lambda.cmd.eco.give")){
                if(args.size == 1){
                    var money: Double = 0.0
                    try {
                        money = args[0].toDouble()
                    }catch(e: NumberFormatException){
                        MessageManager.sendPlayerError("Please use /ecogive money player", p)
                        return false;
                    }

                    if(LambdaEconomy.ecoCore.setPlayer(p, LambdaEconomy.ecoCore.getBalance(p) + money)) {
                        MessageManager.sendPlayerGood("You gave yourself${money}${LambdaEconomy.ecoCore.currencySign()}", p)
                        MessageManager.sendConsoleEco("The balance of ${p.name} has been set to ${money}${LambdaEconomy.ecoCore.currencySign()} (by: ${p.name})")
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

                    if(target != null && LambdaEconomy.ecoCore.setPlayer(target, LambdaEconomy.ecoCore.getBalance(target) + money)) {
                        MessageManager.sendPlayerGood("You gave ${target.name} ${money}${LambdaEconomy.ecoCore.currencySign()}", p)
                        MessageManager.sendConsoleEco("The balance of ${target.name} has been set to ${money}${LambdaEconomy.ecoCore.currencySign()} (by: ${p.name})")
                    }else {
                        MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                    }
                }else MessageManager.sendPlayerError("Please use /ecogive money player", p)
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