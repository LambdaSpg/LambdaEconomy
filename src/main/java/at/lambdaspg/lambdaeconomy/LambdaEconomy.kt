package at.lambdaspg.lambdaeconomy

import at.lambdaspg.lambdaeconomy.commands.EcoGetCommand
import at.lambdaspg.lambdaeconomy.commands.EcoSetCommand
import at.lambdaspg.lambdaeconomy.economy.EconomyCore
import at.lambdaspg.lambdaeconomy.economy.EconomyHandler
import at.lambdaspg.lambdaeconomy.listeners.PlayerJoinListener
import net.milkbowl.vault.economy.Economy
import org.bstats.bukkit.Metrics
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager

class LambdaEconomy : JavaPlugin() {

    companion object{
        lateinit var ecoCore: EconomyCore
        lateinit var plugin: LambdaEconomy
        lateinit var connectionS: String
        lateinit var ecoHandler: EconomyHandler
        lateinit var configuration: FileConfiguration

        fun getInstance() : LambdaEconomy{
            return plugin;
        }

        fun getEconomyHandler() : EconomyHandler{
            return ecoHandler
        }

        fun getDatabaseConnectionString() : String{
            return connectionS
        }

        fun getConfig() : FileConfiguration{
            return this.configuration
        }
    }

    override fun onEnable() {
        plugin = this;

        configuration = config;
        setupConfig()
        setupDatabase()
        instanceClasses()

        if(setupEconomy()){
            MessageManager.sendConsoleGood("Economy has been registered!")
        }else{
            MessageManager.sendConsoleError("Vault is missing, could not register Economy!")
            MessageManager.sendConsoleInfo("Disabling Plugin")
            server.pluginManager.disablePlugin(this)
        }

        setupCommands()
        setupListeners()
        val metrics = Metrics(this, 13744)
    }

    private fun setupConfig() {
        if(config.getString("Server.name") == null) {
            config.set("Server.name", "ChangeInConfig")
            config.set("Server.currency.singular", "Euro")
            config.set("Server.currency.plural", "Euro")
            config.set("Server.currency.sign", "€")
        }

        this.saveConfig()
        this.saveDefaultConfig()
    }

    private fun setupListeners() {
        server.pluginManager.registerEvents(PlayerJoinListener(), this)
    }

    private fun setupCommands() {
        getCommand("ecoset")!!.setExecutor(EcoSetCommand())
        getCommand("ecoget")!!.also { s ->
            s.setExecutor(EcoGetCommand())
            s.tabCompleter = EcoGetCommand()
        }
        //getCommand("createaccount")!!.setExecutor(EcoCreateAccountCommand())
    }

    private fun setupDatabase() {

        connectionS = "jdbc:sqlite:${dataFolder}\\economy.db"

        val connection: Connection = DriverManager.getConnection(connectionS)
        val statement = connection.createStatement()
        statement.queryTimeout = 30

        statement.executeUpdate("create table if not exists MONEY(id STRING, money DOUBLE)")
        connection.close()
    }

    private fun setupEconomy(): Boolean {
        if(server.pluginManager.getPlugin("Vault") == null){
            return false;
        }
        server.servicesManager.register(Economy::class.java, ecoCore, this, ServicePriority.Highest)
        return true;
    }

    private fun instanceClasses() {
        ecoCore = EconomyCore()
        ecoHandler = EconomyHandler()
    }

    override fun onDisable() {

    }
}