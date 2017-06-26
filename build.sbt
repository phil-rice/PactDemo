name := "Pact-Demo"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

val versions = new {
  val scala = "2.11.8"
  //  val scala = "2.12.1"
  val finatra = "2.2.0"
  val scalatest = "3.0.1"
  val scalapact = "2.1.3"
  val mockito = "1.10.19"
  val guice = "4.0"
}
lazy val commonSettings = Seq(
  version := "1.0",
  organization := "org.validoc",
  publishMavenStyle := true,
  scalaVersion := versions.scala,
  scalacOptions ++= Seq("-feature"),
  libraryDependencies += "org.mockito" % "mockito-all" % versions.mockito % "test",
  libraryDependencies += "org.scalatest" %% "scalatest" % versions.scalatest % "test"
)

lazy val finatraSettings = commonSettings ++ Seq(
  libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra,
  libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra % "test",
  libraryDependencies += "com.twitter" %% "inject-server" % versions.finatra % "test",
  libraryDependencies += "com.twitter" %% "inject-app" % versions.finatra % "test",
  libraryDependencies += "com.twitter" %% "inject-core" % versions.finatra % "test",
  libraryDependencies += "com.twitter" %% "inject-modules" % versions.finatra % "test",
  libraryDependencies += "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  libraryDependencies += "com.twitter" %% "finatra-jackson" % versions.finatra % "test",

  libraryDependencies += "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  libraryDependencies += "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  libraryDependencies += "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  libraryDependencies += "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  libraryDependencies += "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
  libraryDependencies += "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test" classifier "tests",
  libraryDependencies += "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests"
)

lazy val pactConsumerSettings = finatraSettings ++ Seq(
  libraryDependencies += "com.itv" %% "scalapact-scalatest" % versions.scalapact
)
lazy val utilities = (project in file("modules/utilities")).
  settings(finatraSettings: _*)

lazy val androidApp = (project in file("modules/androidApp")).dependsOn(utilities).aggregate(utilities).
  settings(pactConsumerSettings: _*)

lazy val iosApp = (project in file("modules/iosApp")).dependsOn(utilities).aggregate(utilities).
  settings(pactConsumerSettings: _*)

lazy val provider = (project in file("modules/provider")).dependsOn(utilities).aggregate(utilities).
  settings(finatraSettings: _*)
