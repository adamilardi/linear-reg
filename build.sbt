name := "linear-reg"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "com.twitter" %% "algebird-core" % "0.3.0",
    "org.specs2" %% "specs2" % "1.13" % "test",
    "org.scalacheck" %% "scalacheck" % "1.10.0"
)