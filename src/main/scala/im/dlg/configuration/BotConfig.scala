package im.dlg.configuration

import com.typesafe.config.ConfigFactory

object BotConfig {

  def loadByDefaults() = {
    val config = ConfigFactory.load("dialog")

    new BotConfig(
      DialogConfig.getHost(),
      DialogConfig.getPort(),
      DialogConfig.isSecure(),
      DialogConfig.getToken(),
      DialogConfig.getBotName()
    )
  }

}

class BotConfig(val host: String,
                val port: Int,
                val isSecure: Boolean,
                val token: String,
                val botName: String
               ) {

  override def toString = s"BotConfig(host=$host, port=$port, isSecure=$isSecure, token=$token, botName=$botName)"
}
