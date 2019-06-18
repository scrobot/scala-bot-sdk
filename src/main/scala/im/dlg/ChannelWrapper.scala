package im.dlg

import java.net.URI
import java.security.Security
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import im.dlg.configuration.BotConfig
import io.grpc.netty.NettyChannelBuilder
import io.grpc.ManagedChannel
import org.bouncycastle.jce.provider.BouncyCastleProvider

trait ChannelWrapper {
  def connect()
  def getChannel(): ManagedChannel
}

class NettyChannelWrapper(config: BotConfig) extends ChannelWrapper with LazyLogging{

  var channel: Option[ManagedChannel] = None

  override def connect() = {
    Security.addProvider(new BouncyCastleProvider)

    channel match {
      case Some(i) => i.shutdown()
      case None => logger.debug("channel is empty")
    }

    logger.debug(s"${config.host}:${config.port}")

    channel = Option(NettyChannelBuilder.forTarget(URI.create(s"${config.host}:${config.port}").getAuthority))
      .map(builder => {
        if(!config.isSecure) builder.usePlaintext(false)

        builder.keepAliveTimeout(10, TimeUnit.SECONDS)

        builder.build()
      })
  }

  override def getChannel(): ManagedChannel = channel.orNull
}
