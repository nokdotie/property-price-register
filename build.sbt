import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneOffset}

ThisBuild / scalaVersion := "3.3.0"
ThisBuild / organization := "ie.nok"
ThisBuild / version := DateTimeFormatter
  .ofPattern("yyyyMMdd.HHmmss.n")
  .withZone(ZoneOffset.UTC)
  .format(Instant.now())

lazy val root = project
  .in(file("."))
  .aggregate(
    api,
    `property-price-register`
  )

lazy val api = project
  .settings(
    dockerRepository := Some("gcr.io/deed-ie/property-price-register"),
    dockerAliases ++= Seq(
      s"time-${Environment.instant}",
      s"sha-${Environment.gitShortSha1}"
    )
      .map(Option.apply)
      .map(dockerAlias.value.withTag),
    dockerExposedPorts ++= Seq(8080),
    libraryDependencies ++= List(
      "dev.zio" %% "zio-http" % "0.0.5"
    )
  )
  .enablePlugins(JavaAppPackaging, DockerPlugin)

lazy val `property-price-register` = project
  .settings(
    githubOwner := "nok-ie",
    githubRepository := "property-price-register",
    resolvers += Resolver.githubPackages("nok-ie")
  )
