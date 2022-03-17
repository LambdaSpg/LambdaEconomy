package at.lambdaspg.lambdaeconomy

import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


object FileManager {
    fun logEcoChange(text: String){
        val logFile: File = File("${LambdaEconomy.getInstance().dataFolder}/eco.log")
        if(!logFile.exists()) logFile.createNewFile()


        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val localDate: String = sdf.format(Date())

        FileOutputStream(logFile, true).bufferedWriter().use { out ->
            out.appendLine("$localDate | $text")
        }
    }
}