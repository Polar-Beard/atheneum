name := """atheneum"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache,
  javaWs,
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
  "org.hibernate.ogm" % "hibernate-ogm-bom" % "5.0.1.Final",
  "org.hibernate.ogm" % "hibernate-ogm-cassandra" % "5.0.1.Final",
  "org.hibernate.ogm" % "hibernate-ogm-infinispan" % "5.0.1.Final",
  "org.hibernate.ogm" % "hibernate-ogm-core" % "5.0.1.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.21.Final",
  "org.jboss.jbossts" % "jbossjta" % "4.16.6.Final",
  "com.google.inject.extensions" % "guice-persist" % "4.1.0",
  "com.google.code.gson" % "gson" % "2.7"
)
