name := "multi-project-sbt"

version := "1.0"

scalaVersion := "2.12.5"

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(common, multi1, multi2)

lazy val common = project
  .settings(settings)

lazy val multi1 = project
  .settings(settings)
  .dependsOn(common)

lazy val multi2 = project
  .settings(settings)
  .dependsOn(common)

lazy val settings = Seq(
  scalacOptions ++=  Seq(
    "-unchecked",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "utf8"
  ),
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)
