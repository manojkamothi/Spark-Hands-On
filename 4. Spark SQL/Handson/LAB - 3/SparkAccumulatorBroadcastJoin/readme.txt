follow instructions given in the PPT to work, This folder is fully working one
you could also use this to work.
Set your spark path to make this work

To Compile

sbt package

To Run

$SPARK_HOME/bin/spark-submit --master "local[*]" --class in.goai.SparkAccumulatorBroadcastJoin target/scala-2.10/sparkaccumulatorbroadcastjoin_2.10-1.0.jar

The results will be in the folder results
