package im.dlg.configuration

import com.typesafe.config.{Config, ConfigFactory}
import im.dlg.configuration.DialogConfig.config

trait NetworKConfig {
  
  def getHost() = config.getString("botsdk.network.host")

  def getPort() = config.getInt("botsdk.network.port")
  
}

trait BotConnectionConfig {
  
  def getToken() = config.getString("botsdk.bot.token")
  
  def getBotName() = config.getString("botsdk.bot.name")
  
}

trait ExecutorsConfig {
  
  def getPoolParallelism() = config.getInt("botsdk.fork-join-pool.parallelism")
  
}

object DialogConfig extends NetworKConfig
  with BotConnectionConfig
  with ExecutorsConfig {

  val config: Config = ConfigFactory.load("dialog")

  def isSecure() = if(config.getIsNull("dialog.botsdk.is-secure")) false else config.getBoolean("dialog.botsdk.is-secure")
}
