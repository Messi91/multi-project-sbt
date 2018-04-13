name := "multi-project-sbt"

version := "1.0"

scalaVersion in ThisBuild := "2.12.5"

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(common, multi1, multi2)

lazy val common = project
  .settings(settings,
    libraryDependencies ++= commonDependencies)

lazy val multi1 = project
  .settings(settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.monocleCore,
      dependencies.monocleMacro
    )
  ).dependsOn(common)

lazy val multi2 = project
  .settings(settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.pureconfig
    )
  ).dependsOn(common)

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
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val dependencies = new {
  val typesafeConfigV = "1.3.1"
  val pureconfigV     = "0.8.0"
  val monocleV        = "1.4.0"
  val akkaV           = "2.5.6"
  val scalatestV      = "3.0.4"
  val scalacheckV     = "1.13.5"

  val typesafeConfig = "com.typesafe"               % "config"                   % typesafeConfigV
  val akka           = "com.typesafe.akka"          %% "akka-stream"             % akkaV
  val monocleCore    = "com.github.julien-truffaut" %% "monocle-core"            % monocleV
  val monocleMacro   = "com.github.julien-truffaut" %% "monocle-macro"           % monocleV
  val pureconfig     = "com.github.pureconfig"      %% "pureconfig"              % pureconfigV
  val scalatest      = "org.scalatest"              %% "scalatest"               % scalatestV
  val scalacheck     = "org.scalacheck"             %% "scalacheck"              % scalacheckV
}

lazy val commonDependencies = Seq(
  dependencies.typesafeConfig,
  dependencies.akka,
  dependencies.scalatest  % "test",
  dependencies.scalacheck % "test"
)
