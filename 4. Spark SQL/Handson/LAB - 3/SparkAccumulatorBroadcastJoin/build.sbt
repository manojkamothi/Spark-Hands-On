name := "SparkAccumulatorBroadcastJoin"
version := "1.0"
scalaVersion := "2.10.5"
organization := "in.goai"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.1"
resolvers += Resolver.mavenLocal
