import org.apache.ivy.core.module.descriptor.ExcludeRule

name := "SparkStreamingExamples"

version := "1.0"

scalaVersion := "2.10.6"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.10
libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.0"

libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.0"

libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.0"

// https://mvnrepository.com/artifact/com.databricks/spark-csv_2.10
libraryDependencies += "com.databricks" % "spark-csv_2.10" % "1.5.0"

libraryDependencies += "com.databricks" % "spark-xml_2.10" % "0.4.1"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.0"

libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.0"

libraryDependencies += "org.apache.spark" % "spark-streaming-flume_2.10" % "1.6.0"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.0"

libraryDependencies += "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.6.0"


libraryDependencies += "it.nerdammer.bigdata" % "spark-hbase-connector_2.10" % "1.0.3" exclude("javax.servlet","javax.servlet-api") exclude("org.mortbay.jetty" ,"jetty") exclude("org.mortbay.jetty","servlet-api-2.5")