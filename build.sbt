ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.0"

lazy val root = project
  .in(file("."))
  .aggregate(api)

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
