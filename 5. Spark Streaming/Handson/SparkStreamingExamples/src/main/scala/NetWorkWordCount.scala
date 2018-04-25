
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.storage._
import org.apache.log4j._

object NetWorkWordCount {
  
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  val conf = new SparkConf().setMaster("local[2]").setAppName("NetWorkWordCount")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc,Seconds(5))
  
  //ssc.checkpoint("hdfs://localhost:9000/spark")
  
  def main(args: Array[String]): Unit = {

    val data = ssc.socketTextStream("localhost",4455,org.apache.spark.storage.StorageLevel.MEMORY_ONLY)

    val words = data.flatMap(_.split(" ")).map(w => (w,1))
    val wordcounts = words.reduceByKey(_+_)
    wordcounts.print
    /// /wordcounts.foreachRDD{ rdd => if(rdd.count>0)println(rdd)}

    ssc.start
    ssc.awaitTermination()
  }
}