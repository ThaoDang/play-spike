import play.sbt.PlayJava

name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,QueryDSLPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.10.Final",
  "org.ldaptive" % "ldaptive" % "1.1.0",
  "mysql" % "mysql-connector-java" % "5.1.36",
  javaJdbc,
  cache,
  javaWs
)


fork in run := true