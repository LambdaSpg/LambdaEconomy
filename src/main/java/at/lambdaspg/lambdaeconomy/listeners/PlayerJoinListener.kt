package at.lambdaspg.lambdaeconomy.listeners

import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener{
    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        val p = event.player;
        if(LambdaEconomy.ecoHandler.containsPlayer(p.uniqueId)){
            return;
        }else {
            LambdaEconomy.ecoHandler.addPlayer(p.uniqueId, p.name);
            MessageManager.sendConsoleEco("Account has been created for ${p.name}")
        }
    }
}