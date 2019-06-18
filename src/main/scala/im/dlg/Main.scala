package im.dlg

import im.dlg.api.InternalApi
import im.dlg.configuration.BotConfigurator
import org.slf4j.LoggerFactory

object Main {

  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger("Main")

    val config = new BotConfigurator()
      .addHost("tls://epm.dlg.im")
      .addPort(443)
      .addIsSecure(false)
      .addToken("a7d1fe1409b06bd75a19aea9c66f63b461ca1db7")
      .addBotName("scala")
      .build()

    val api = new InternalApi(config)
    api.start()

    logger.debug(config.host + ":" + config.port + "?security=" + config.isSecure)

    while(true) {

    }
  }

}
