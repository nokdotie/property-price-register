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
    githubOwner := "nokdotie",
    githubRepository := "property-price-register",
    resolvers += Resolver.githubPackages("nokdotie")
  )
