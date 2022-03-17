package at.lambdaspg.lambdaeconomy.commands.subcommands

import at.lambdaspg.lambdaeconomy.ColorManager
import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import at.lambdaspg.lambdaeconomy.commands.SubCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GetSubCommand : SubCommand {
    override fun getName(): String {
        return "get"
    }

    override fun perform(p: Player, args: Array<out String>) {
        if (p.hasPermission("lambda.cmd.eco.get")) {
            if(args.size == 2){
                if(!p.hasPermission("lambda.cmd.eco.get.others")) {
                    MessageManager.noPermission(p);
                    return
                }
                val target: Player? = Bukkit.getPlayer(args[1])
                if(target != null){
                    MessageManager.sendPlayerInfo("The Player ${ColorManager.good()}${target.name} §7 has ${ColorManager.good()}${LambdaEconomy.ecoHandler.getMoney(p.uniqueId)}${LambdaEconomy.ecoCore.currencySign()}", p)
                }else MessageManager.sendPlayerError("This Player does not exist", p);
            }else {
                if(LambdaEconomy.ecoHandler.containsPlayer(p.uniqueId)) {
                    MessageManager.sendPlayerInfo("You have ${ColorManager.good()}${LambdaEconomy.ecoHandler.getMoney(p.uniqueId)}${LambdaEconomy.ecoCore.currencySign()}", p)
                }else MessageManager.sendPlayerError("You do not have an account, this is almost certainly a mistake, please contact an administrator.", p)
            }
        } else MessageManager.noPermission(p)
    }

    override fun tab(args: Array<out String>): MutableList<String> {
        val list: ArrayList<String> = ArrayList()
        if(args.size == 2){
            Bukkit.getOnlinePlayers().forEach { all -> list.add(all.name) }
        }

        return list;
    }

    override fun getSyntax(): String {
        return "/eco get player"
    }

    override fun getDescription(): String {
        return "Returns the Balance of the Player"
    }
}