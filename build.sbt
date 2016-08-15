name := """atheneum"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.hibernate.ogm" % "hibernate-ogm-bom" % "5.0.1.Final",
  "org.hibernate.ogm" % "hibernate-ogm-cassandra" % "5.0.1.Final",
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.jboss.jbossts" % "jbossjta" % "4.16.6.Final"
)
