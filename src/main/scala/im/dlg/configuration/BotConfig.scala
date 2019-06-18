package im.dlg.configuration

import com.typesafe.config.ConfigFactory

object BotConfig {

  def loadByDefaults() = {
    val config = ConfigFactory.load("dialog")

    new BotConfig(config.getString("dialog.botsdk.host"), config.getInt("dialog.botsdk.port"), false)
  }

}

class BotConfig(val host: String, val port: Int, val isSecure: Boolean)
