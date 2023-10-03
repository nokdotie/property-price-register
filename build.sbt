import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneOffset}

ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "ie.nok"
ThisBuild / version := DateTimeFormatter
  .ofPattern("yyyyMMdd.HHmmss.n")
  .withZone(ZoneOffset.UTC)
  .format(Instant.now())

lazy val root = project
  .in(file("."))
  .aggregate(
    `property-price-register`
  )

lazy val `property-price-register` = project
  .settings(
    githubOwner := "nok-ie",
    githubRepository := "property-price-register",
    resolvers += Resolver.githubPackages("nok-ie"),
    libraryDependencies ++= List(
      "dev.zio" %% "zio" % "2.0.15",
      "dev.zio" %% "zio-http" % "0.0.5",
      "dev.zio" %% "zio-json" % "0.6.0",
      "dev.zio" %% "zio-streams" % "2.0.15",
      "com.github.tototoshi" %% "scala-csv" % "1.3.10",
//      "com.google.cloud" % "google-cloud-firestore" % "3.13.3",
//      "com.firebase" % "geofire-java" % "3.0.0",
//      "org.apache.pdfbox" % "pdfbox" % "2.0.29",
//      "ie.nok" %% "scala-libraries" % "20230711.132642.684556505",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test
    )
  )
