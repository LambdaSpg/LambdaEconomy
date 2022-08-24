package at.lambdaspg.lambdaeconomy.commands.subcommands

import at.lambdaspg.lambdaeconomy.MessageManager
import at.lambdaspg.lambdaeconomy.commands.SubCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.NumberFormatException

class PaySubCommand : SubCommand {
    override fun getName(): String {
        return "pay"
    }

    override fun perform(p: Player, args: Array<out String>) {
        if(p.hasPermission("lambda.eco.pay")){
            if(args.size == 3){
                var money: Double = 0.0
                try {
                    money = args[1].toDouble()
                }catch(e: NumberFormatException){
                    MessageManager.sendPlayerError("Please use /eco give money player", p)
                    return
                }
                val target = Bukkit.getPlayer(args[2])
                if(target == null) {

                }
            }else {
                MessageManager.sendPlayerError("Please use ${getSyntax()}", p);
            }
        }
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
        return "/eco pay amount player"
    }

    override fun getDescription(): String {
        return "pay a player a specific amount of money"
    }
}