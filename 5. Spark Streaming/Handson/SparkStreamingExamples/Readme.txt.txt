follow the instructiosn to Run these Programs

make sure that your flume and kafka installed in your VMS

copy the all flume configurations files into your flume conf directory

copy the spark-streaming-flume-sink_2.10-1.6.0.jar to your flume lib folder
--------------------------------------
compile the project by below command
----------------------------------------
sbt compile
--------------------------------------
package the project to jar files
----------------------------------
sbt package


---------------------
NetWorkWordCount Example
------------------------

step 1) open terminal and create a socket for sending messages using below command

nc -lk 4444

****dont close this terminal


step 2) submit your application to spark

spark-submit --class NetWorkWordCount --master "local[*]" $STREAMING_EXAMPLES_HOME/target/scala-2.10/sparkstreamingexamples_2.10-1.0.jar



----------------------
 flume Hbase Example 
----------------------
run flume vmstatspark.conf using below command

step 1) flume-ng agent -n tier1 -f /usr/lib/flume-ng/conf/vmstatspark.conf

******dont close that terminal

step 2) submit your application to spark

spark-submit --class hbase.FlumeToHbase --master "local[*]" --packages org.apache.spark:spark-streaming-flume_2.10:1.6.0,it.nerdammer.bigdata:spark-hbase-connector_2.10:1.0.3 --exclude-packages javax.servlet:javax.servlet-api,org.mortbay.jetty:jetty,org.mortbay.jetty:servlet-api-2.5 $STREAMING_EXAMPLES_HOME/target/scala-2.10/sparkstreamingexamples_2.10-1.0.jar 


----------------------
flume Hive Example 
----------------------

repeat step 1 from flume hbase Example

step 2) submit your application to spark

spark-submit --class hive.FlumeToHive --master "local[*]" --packages org.apache.spark:spark-streaming-flume_2.10:1.6.0 $STREAMING_EXAMPLES_HOME/target/scala-2.10/sparkstreamingexamples_2.10-1.0.jar 


---------------------------
kafka HBase example
---------------------------

run flume flume-kafka.conf using below command

step 1) flume-ng agent -n tier1 -f /usr/lib/flume-ng/conf/flume-kafka.conf

******dont close that terminal

step 2) submit your application to spark

spark-submit --class hbase.KafkaToHbase --master "local[*]" --packages org.apache.spark:spark-streaming-kafka_2.10:1.6.0,it.nerdammer.bigdata:spark-hbase-connector_2.10:1.0.3 --exclude-packages javax.servlet:javax.servlet-api,org.mortbay.jetty:jetty,org.mortbay.jetty:servlet-api-2.5 $STREAMING_EXAMPLES_HOME/target/scala-2.10/sparkstreamingexamples_2.10-1.0.jar 


----------------------
Kafka Hive Example 
----------------------

repeat step 1 from kafka hbase Example

step 2) submit your application to spark

spark-submit --class hive.KafkaToHive --master "local[*]" --packages org.apache.spark:spark-streaming-kafka_2.10:1.6.0 $STREAMING_EXAMPLES_HOME/target/scala-2.10/sparkstreamingexamples_2.10-1.0.jar 


use cases
-----------------------
Place the weatherdata.sh, trafficdata.sh files /home/cloudera/ location

create kafkaTopic kafkaTest or ur favorite name(if changed change the topic name in flume configuration and in the application also)

weather kafka streaming run flume-http-source.conf  configuration

then run the KafkaWeatherStreaming Example 

for traffic data streaming run httpSource.conf configuration

then run the FlumetrafficStreaming Example.
