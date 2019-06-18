package im.dlg

import com.typesafe.scalalogging.LazyLogging
import im.dlg.configuration.BotConfig
import io.grpc.netty.NettyChannelBuilder
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

trait ChannelWrapper {
  def connect()
  def getChannel(): ManagedChannel
}

class NettyChannelWrapper(config: BotConfig) extends ChannelWrapper with LazyLogging{

  var channel: Option[ManagedChannel] = None

  override def connect() = {
    channel match {
      case Some(i) => i.shutdown()
      case None => logger.debug("channel is empty")
    }

    channel = Option(NettyChannelBuilder.forAddress(config.host, config.port))
      .map(builder => {
        if(!config.isSecure) builder.usePlaintext(true)

        builder.build()
      })
  }

  override def getChannel(): ManagedChannel = channel.orNull
}
