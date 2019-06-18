package im.dlg.configuration

import im.dlg.configuration.BotConfigurator.Config.{BotName, Host, IsSecure, Port, Token}

object BotConfigurator {

  sealed trait Config
  object Config {
    sealed trait Empty extends Config
    sealed trait Host extends Config
    sealed trait Port extends Config
    sealed trait IsSecure extends Config
    sealed trait Token extends Config
    sealed trait BotName extends Config

    type FullConfig = Empty with Host with Port with IsSecure
  }

}

class BotConfigurator[Config <: BotConfigurator.Config](params: Map[String, Any] = Map()) {

  private val HOST = "host"
  private val PORT = "port"
  private val IS_SECURE = "is_secure"
  private val TOKEN = "bot_token"
  private val BOT_NAME = "bot_name"

  def addHost(host: String): BotConfigurator[Config with Host] = new BotConfigurator(params + (HOST -> host))

  def addPort(port: Int): BotConfigurator[Config with Port] = new BotConfigurator(params + (PORT -> port))

  def addIsSecure(isSecure: Boolean): BotConfigurator[Config with IsSecure] = new BotConfigurator(params + (IS_SECURE -> isSecure))

  def addToken(token: String): BotConfigurator[Config with Token] = new BotConfigurator(params + (TOKEN -> token))

  def addBotName(name: String): BotConfigurator[Config with BotName] = new BotConfigurator(params + (BOT_NAME -> name))

  def build() = new BotConfig(
    params(HOST).asInstanceOf[String],
    params(PORT).asInstanceOf[Int],
    params(IS_SECURE).asInstanceOf[Boolean],
    params(TOKEN).asInstanceOf[String],
    params(BOT_NAME).asInstanceOf[String]
  )
}
