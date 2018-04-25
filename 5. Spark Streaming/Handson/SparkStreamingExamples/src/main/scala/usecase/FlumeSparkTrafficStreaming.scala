package usecase

import com.google.gson.Gson
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SQLContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.collection.JavaConversions._

object FlumeSparkTrafficStreaming {
  
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val conf = new SparkConf().setMaster("local[2]").setAppName(this.getClass.getName)
  val ssc = new StreamingContext(conf,Seconds(5))
  val sqlContext = new SQLContext(ssc.sparkContext)
  import sqlContext.implicits._

  def main (args: Array[String]): Unit = {

    val gson = new Gson();
    val data = FlumeUtils.createPollingStream(ssc,"10.1.7.9",6666,StorageLevel.MEMORY_ONLY)
      .map(x => x.event)
      .map(x => new String(x.getBody.array()))

    data.foreachRDD{

      rdd =>

        val records =  rdd.flatMap{

          line =>
            val gson = new Gson();
            val data1 = gson.fromJson(line,classOf[java.util.HashMap[String,java.util.ArrayList[java.util.Map[String,String]]]])
            data1.get("incidents").toList.map(x => gson.toJson(x))
        }

        val trafficData = sqlContext.read.json(records)
        if(trafficData.count>0)
          trafficData.show
          trafficData.registerTempTable("traffic")
          sqlContext.sql("select count(*) from traffic").show


    }

    ssc.start()
    ssc.awaitTermination()
  }

}