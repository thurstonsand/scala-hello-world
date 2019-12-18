import sbtdocker.immutable.Dockerfile

val finchVersion = "0.31.0"
val circeVersion = "0.11.1"
val scalatestVersion = "3.0.7"
val paradiseVersion = "2.1.0"

lazy val dockerSettings = Seq(
  dockerfile in docker := {
    val classpath = (managedClasspath in Compile).value
    val jarFile = sbt.Keys.`package`.in(Compile, packageBin).value
    val jarTarget = s"/app/${jarFile.getName}"
    val classpathString = (classpath.files.map("/app/" + _.getName) :+ jarTarget).mkString(":")
    val mainClassPath = (mainClass in (Compile, packageBin)).value getOrElse sys.error("expected exactly one main class")
    Dockerfile.empty
      .from("adoptopenjdk/openjdk8:alpine")
      .add(classpath.files, "/app/")
      .add(jarFile, jarTarget)
      .run("addgroup -S gogroup && adduser -S gouser -G gogroup")
      .user("gouser")
      .cmd("java", "-cp", classpathString, mainClassPath)
  },
  imageNames in docker := Seq(
    ImageName(s"${organization.value}/${name.value}:latest"),
    ImageName(
      namespace = Some(organization.value),
      repository = name.value,
      tag = Some(s"v${version.value}")
    )
  )
)
lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin)
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
    ),
  ).settings(dockerSettings: _*)