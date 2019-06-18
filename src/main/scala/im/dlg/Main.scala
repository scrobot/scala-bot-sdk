package im.dlg

import im.dlg.configuration.{BotConfig, BotConfigurator}

object Main {

  def main(args: Array[String]): Unit = {
    val config = new BotConfigurator()
      .addHost("dlg.im")
      .addPort(80)
      .addIsSecure(false)
      .build()

    val config2 = BotConfig.loadByDefaults()

    println(config.host + ":" + config.port + "?security=" + config.isSecure)
    println(config2.host + ":" + config2.port + "?security=" + config2.isSecure)
  }

}
