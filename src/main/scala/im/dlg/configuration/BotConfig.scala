package im.dlg.configuration

import com.typesafe.config.ConfigFactory

object BotConfig {

  def loadByDefaults() = {
    val config = ConfigFactory.load("dialog")

    new BotConfig(
      config.getString("dialog.botsdk.host"),
      config.getInt("dialog.botsdk.port"),
      if(config.getIsNull("dialog.botsdk.is-secure")) false else config.getBoolean("dialog.botsdk.is-secure"),
      config.getString("dialog.botsdk.bot-token"),
      config.getString("dialog.botsdk.bot-name")
    )
  }

}

class BotConfig(val host: String,
                val port: Int,
                val isSecure: Boolean,
                val token: String,
                val botName: String
               )
