package at.lambdaspg.lambdaeconomy.commands

import at.lambdaspg.lambdaeconomy.ColorManager
import at.lambdaspg.lambdaeconomy.commands.subcommands.*
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.awt.Color

class EcoCommandManager() : CommandExecutor, TabCompleter {

    private val subCommands: ArrayList<SubCommand> = ArrayList()

    init {
        subCommands.add(SetSubCommand())
        subCommands.add(GetSubCommand())
        subCommands.add(TakeSubCommand())
        subCommands.add(GiveSubCommand())
        subCommands.add(BalanceTopSubCommand())

    }


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val p: Player = sender
            if(args.isNotEmpty()){
                for(i in 0 until getSubCommands().size){
                    if(args[0] == getSubCommands()[i].getName()){
                        getSubCommands()[i].perform(p, args)
                    }
                }
            }else {
                p.sendMessage("${ColorManager.limePastelGreen()}--------------------------------")
                for (i in 0 until getSubCommands().size) {
                    p.sendMessage(
                        getSubCommands()[i].getSyntax() + " - " + getSubCommands()[i].getDescription()
                    )
                }
                p.sendMessage("${ColorManager.limePastelGreen()}--------------------------------")
            }
        }
        return false
    }

    fun  getSubCommands() : ArrayList<SubCommand>{
        return subCommands
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if(args.size == 1){
            val list: ArrayList<String> = ArrayList()
            for(i in 0 until getSubCommands().size){
                list.add(getSubCommands()[i].getName())
            }

            return list
        }else {

            for (i in 0 until getSubCommands().size) {
                if (args[0] == getSubCommands()[i].getName()) {
                    return getSubCommands()[i].tab(args)
                }
            }
        }
        return null
    }
}