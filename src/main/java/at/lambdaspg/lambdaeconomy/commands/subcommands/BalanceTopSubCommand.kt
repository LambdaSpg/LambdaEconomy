package at.lambdaspg.lambdaeconomy.commands.subcommands

import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.commands.SubCommand
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

class BalanceTopSubCommand : SubCommand {

    override fun getName(): String {
        return "balancetop"
    }

    override fun perform(p: Player, args: Array<out String>) {
        if (p.hasPermission("lambda.cmd.eco.balancetop")) {
            val map = LambdaEconomy.ecoHandler.getMoneyMap()
            val list: List<Map.Entry<UUID, Double>> = ArrayList<Map.Entry<UUID, Double>>(map.entries)
            Collections.sort(
                list
            ) { (_, value): Map.Entry<UUID, Double>, (_, value1): Map.Entry<UUID, Double> ->
                java.lang.Double.compare(
                    value,
                    value1
                ) * -1
            }
            p.sendMessage("Top 10--------------------")
            for ((key, value) in list) {
                //TODO ("Add username to database and fetch it here (save the username on join)")
                p.sendMessage("${LambdaEconomy.ecoHandler.getName(key)} - $value")
            }
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