package at.lambdaspg.lambdaeconomy.commands.subcommands

import at.lambdaspg.lambdaeconomy.ColorManager
import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import at.lambdaspg.lambdaeconomy.commands.SubCommand
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

class BalanceTopSubCommand : SubCommand {

    override fun getName(): String {
        return "balancetop"
    }

    override fun perform(p: Player, args: Array<out String>) {
        if (p.hasPermission("lambda.eco.balancetop")) {
            val map = LambdaEconomy.ecoHandler.getMoneyMap()
            val list: List<Map.Entry<UUID, Double>> = ArrayList<Map.Entry<UUID, Double>>(map.entries)
            Collections.sort(
                list
            ) { (_, value): Map.Entry<UUID, Double>, (_, value1): Map.Entry<UUID, Double> ->
                value.compareTo(value1) * -1
            }
            MessageManager.sendPlayerGood("--------------------------------", p)
            var i = 0
            for ((key, value) in list) {
                if(i == 9) break
                p.sendMessage("${MessageManager.prefix} ${LambdaEconomy.ecoHandler.getName(key)} - $value")
                i++
            }
            MessageManager.sendPlayerGood("--------------------------------", p)
        }
    }

    override fun tab(args: Array<out String>): MutableList<String> {
        return ArrayList()
    }


    override fun getSyntax(): String {
        return "/eco balancetop"
    }

    override fun getDescription(): String {
        return "shows you the top 10 users"
    }
}