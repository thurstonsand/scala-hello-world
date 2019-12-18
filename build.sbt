val finchVersion = "0.31.0"
val circeVersion = "0.11.1"
val scalatestVersion = "3.0.7"
val paradiseVersion = "2.1.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.github.thurstonsand",
    name := "helloworld",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.10",
    // scalafmtOnCompile := true,
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full),
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test"
    )
  )