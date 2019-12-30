import coursier.maven.MavenRepository
import mill._
import scalalib._

import $ivy.`com.lihaoyi::mill-contrib-bloop:$MILL_VERSION`

object helloWorld extends ScalaModule {
  def scalaVersion = "2.12.10"

  def repositories = super.repositories ++ Seq(
    MavenRepository("https://oss.sonatype.org/content/repositories/releases")
  )
  def finchVersion = "0.31.0"
  def circeVersion = "0.11.1"
  def scalatestVersion = "3.0.7"

  def ivyDeps = Agg(
    ivy"com.github.finagle::finchx-core:$finchVersion",
    ivy"com.github.finagle::finchx-circe:$finchVersion",
    ivy"io.circe::circe-generic:$circeVersion"
  )

  object test extends Tests {
    def ivyDeps = Agg(ivy"org.scalatest::scalatest:$scalatestVersion")
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
