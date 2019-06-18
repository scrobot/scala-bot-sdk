package im.dlg.api

import java.util.concurrent.{Executor, Executors}

import im.dlg.configuration.DialogConfig

object DialogExecutor extends Executor{

  private val executor: Executor = Option(DialogConfig.getPoolParallelism())
    .map(Executors.newFixedThreadPool)
    .getOrElse(Executors.newFixedThreadPool(16))

  override def execute(command: Runnable) = executor.execute(command)

}
