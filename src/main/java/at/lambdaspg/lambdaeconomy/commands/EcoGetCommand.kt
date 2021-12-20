package at.lambdaspg.lambdaeconomy.commands

import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoCore
import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoHandler
import at.lambdaspg.lambdaeconomy.MessageManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EcoGetCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val p: Player = sender
            if (p.hasPermission("lambda.cmd.eco.get")) {
                if(args.size == 1){
                    TODO()
                }else {
                    if(ecoHandler.containsPlayer(p.uniqueId)) {
                        MessageManager.sendPlayerInfo("Du hast ${ecoHandler.getMoney(p.uniqueId)}${ecoCore.currencySign()}", p)
                    }else MessageManager.sendPlayerError("Du besitzt keinen Account, bitte erstelle dir einen mit /createaccount oder /ecoca", p)
                }
            }
        }
        return false
    }
}