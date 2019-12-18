val finchVersion = "0.31.0"
val circeVersion = "0.11.1"
val scalatestVersion = "3.0.7"
val paradiseVersion = "2.1.0"

lazy val dockerSettings = Seq(
  dockerBaseImage := "adoptopenjdk/openjdk8:alpine",
  packageName in Docker := "scala-hello-world",
  defaultLinuxInstallLocation in Docker := "/app"
)
lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin)
  .settings(
    organization := "com.github.thurstonsand",
    name := "helloworld",
    version := "v0.0.1",
    scalaVersion := "2.12.10",
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full),
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test"
    ),
  ).settings(dockerSettings: _*)