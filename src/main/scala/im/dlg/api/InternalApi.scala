package im.dlg.api

import com.google.protobuf.ByteString
import com.typesafe.scalalogging.LazyLogging
import im.dlg.NettyChannelWrapper
import im.dlg.configuration.BotConfig
import im.dlg.grpc.services.authentication.{AuthenticationGrpc, RequestStartTokenAuth}
import im.dlg.grpc.services.registration.{RegistrationGrpc, RequestRegisterDevice}
import io.grpc.Metadata
import io.grpc.stub.{AbstractStub, MetadataUtils}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future, Promise}
import scala.util.{Failure, Success}

class InternalApi(private val botConfig: BotConfig) extends LazyLogging {

  implicit private val executionContext: ExecutionContextExecutor = ExecutionContext.fromExecutor(DialogExecutor)

  private val metadata = Promise[Metadata]()

  private val channel = new NettyChannelWrapper(botConfig)
  private val appId = 11011

  def start()= {
    channel.connect()

    logger.debug("start connecting to server")
    logger.debug(botConfig.toString)

    val response = RegistrationGrpc
      .blockingStub(channel.getChannel())
      .registerDevice(RequestRegisterDevice(appId = appId, appTitle = botConfig.botName, deviceTitle = botConfig.botName, clientPk = ByteString.EMPTY))

    println(response.toString())
    logger.debug(s"registration device response -> $response")

    RegistrationGrpc
      .stub(channel.getChannel())
      .registerDevice(RequestRegisterDevice(appId = appId, appTitle = botConfig.botName, deviceTitle = botConfig.botName))
      .map(res => {
        val header = new Metadata
        val key = Metadata.Key.of("x-auth-ticket", Metadata.ASCII_STRING_MARSHALLER)
        header.put(key, res.token)

        header
      })
      .onComplete {
        case Success(meta) => metadata success meta
        case Failure(t) => logger.error(t.getMessage, t)
      }

    metadata.future
      .map(withToken(AuthenticationGrpc.stub(channel.getChannel()), _))
      .flatMap(_.startTokenAuth(
          RequestStartTokenAuth(
            apiKey = "BotSDK",
            appId = appId,
            deviceTitle = "BotWithToken",
            preferredLanguages = Seq("RU"),
            timeZone = Some("+3"),
            token = botConfig.token
          )
        )
      )
      .onComplete {
        case Success(i) => logger.debug(i.user.toString)
        case Failure(exception) => logger.error(exception.getMessage, exception)
      }
  }

  implicit def withToken[T <: AbstractStub[T]](stub: T, meta: Metadata): T = {
    MetadataUtils.attachHeaders(stub, meta)
  }

}