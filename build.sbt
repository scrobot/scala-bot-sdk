name := "scala-bot-sdk"

version := "0.1"

scalaVersion := "2.11.11"

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

val dialogPlatform = "0.2.0.54"
val monix = "2.3.3"
val caffeine = "2.6.2"
val cats = "1.6.0"
val circe = "0.9.3"
val scalatest = "3.0.4"
val bouncyCastle = "1.55"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "io.monix" %% "monix" % monix,
  "org.typelevel" %% "cats-core" % cats,
  "im.dlg" %% "dialog-platform-services" % dialogPlatform,
  "com.github.ben-manes.caffeine" % "caffeine"  % caffeine,
  "io.circe" %% "circe-core" % circe,
  "io.circe" %% "circe-generic" % circe,
  "io.circe" %% "circe-parser" % circe
)

