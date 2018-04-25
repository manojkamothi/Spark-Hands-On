package usecase


import com.google.gson.Gson
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.JavaConversions._


/**
  * Created by Ashok on 5/9/2017.
  */
object KafkaSparkWeatherStreaming {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val (zkQuorum, group, topics, numThreads) = ("10.1.7.9:2181", "kelly", "kafkaTest", "2")
  val sparkConf = new SparkConf().setAppName("KafkaStreaming").setMaster("local[2]")
  val sc = new SparkContext(sparkConf)
  val ssc = new StreamingContext(sc, Seconds(5))
  val sqlContext = new SQLContext(sc)

  def main (args: Array[String]): Unit = {

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val stream = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

    stream.print()

    stream.foreachRDD{

      rdd =>

        val records =  rdd.flatMap{

          line =>
            val gson = new Gson();
            val data1 = gson.fromJson(line,classOf[java.util.HashMap[String,java.util.ArrayList[java.util.Map[String,String]]]])
            data1.get("list").toList.map(x => gson.toJson(x))
        }

        val weatherData = sqlContext.read.json(records)
        if(weatherData.count>0)
          weatherData.show
          weatherData.registerTempTable("weather")
          sqlContext.sql("select count(*) from weather").show


    }
    ssc.start()
    ssc.awaitTermination()
  }

}
