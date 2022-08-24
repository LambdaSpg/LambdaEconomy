package at.lambdaspg.lambdaeconomy.commands.subcommands

import at.lambdaspg.lambdaeconomy.ColorManager
import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import at.lambdaspg.lambdaeconomy.commands.SubCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class SetSubCommand : SubCommand {
    override fun getName(): String {
        return "set"
    }

    override fun perform(p: Player, args: Array<out String>) {
        if(p.hasPermission("lambda.eco.set")){
            if(args.size == 2){
                var money: Double = 0.0
                try {
                    money = args[1].toDouble()
                }catch(e: NumberFormatException){
                    MessageManager.sendPlayerError("Please use /ecoset money player", p)
                    return
                }

                if(LambdaEconomy.ecoCore.setPlayer(p, money)) {
                    MessageManager.sendPlayerGood("You set your balance to ${ColorManager.good()}${money}${LambdaEconomy.ecoCore.currencySign()}", p)
                    MessageManager.sendConsoleEco("The balance of ${ColorManager.good()}${p.name} §7has been set to ${ColorManager.good()}${money}${LambdaEconomy.ecoCore.currencySign()} (by: ${p.name})")
                }else {
                    MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                }
            }else if(args.size == 3){
                var money: Double = 0.0
                try {
                    money = args[1].toDouble()
                }catch(e: NumberFormatException){
                    MessageManager.sendPlayerError("Please use /ecoset money player", p)
                    return
                }
                val target = Bukkit.getPlayer(args[2])
                if(target == null) {
                    if (LambdaEconomy.ecoCore.setPlayer(args[2], money)) {
                        MessageManager.sendPlayerGood(
                            "You have set the balance of ${ColorManager.good()}${args[2]} §7to ${ColorManager.good()}${money}${LambdaEconomy.ecoCore.currencySign()}", p
                        )
                        MessageManager.sendEcoChange(p, p, money)
                    } else {
                        MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                    }
                }else {
                    if(LambdaEconomy.ecoCore.setPlayer(target, money)){
                        MessageManager.sendPlayerGood(
                            "You have set the balance of ${ColorManager.good()}${args[2]} §7to ${ColorManager.good()}${money}${LambdaEconomy.ecoCore.currencySign()}", p
                        )
                        MessageManager.sendEcoChange(target, p, money)
                    } else {
                        MessageManager.sendPlayerError("Something went wrong, please try again later", p)
                    }
                }
            }else MessageManager.sendPlayerError("Please use /ecoset money player", p)
        } else MessageManager.noPermission(p)
    }

    override fun tab(args: Array<out String>): MutableList<String> {
        val list: ArrayList<String> = ArrayList()
        if(args.size == 2){
            list.add("amount")
        }else if(args.size == 3){
            Bukkit.getOnlinePlayers().forEach { all -> list.add(all.name) }
        }

        return list;
    }

    override fun getSyntax(): String {
        return "/eco set player money"
    }

    override fun getDescription(): String {
        return "Sets the Money of a Player"
    }
}