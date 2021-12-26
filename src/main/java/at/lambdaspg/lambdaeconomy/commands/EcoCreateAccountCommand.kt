package at.lambdaspg.lambdaeconomy.commands

import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoHandler
import at.lambdaspg.lambdaeconomy.MessageManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Deprecated("This Command is not to be used because account creation is done automatically on joining the server")
class EcoCreateAccountCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val p: Player = sender
            if (p.hasPermission("lambda.cmd.eco.create")) {
                if(ecoHandler.containsPlayer(p.uniqueId)){
                    MessageManager.sendPlayerError("Du besitzt schon einen Account", p)
                }else {
                    ecoHandler.addPlayer(p.uniqueId)
                    MessageManager.sendPlayerGood("Du hast dir erfolgreich einen Account erstellt", p)
                    MessageManager.sendConsoleEco("Der Spieler ${p.name} hat sich einen Account erstellt")
                }
            }
        }

        return false
    }
}