ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.2.2"

lazy val root = project
  .in(file("."))
  .aggregate(api)

lazy val api = project
  .settings(
    dockerRepository := Some("gcr.io/deed-ie/property-price-register"),
    dockerExposedPorts ++= Seq(8080),
    libraryDependencies ++= List(
      "dev.zio" %% "zio-http" % "0.0.5"
    )
  )
  .enablePlugins(JavaAppPackaging, DockerPlugin)
