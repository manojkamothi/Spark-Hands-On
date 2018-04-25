import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Ashok on 5/8/2017.
  */
object TextFileStreamExample {

  val conf = new SparkConf().setMaster("local[2]").setAppName("TextFileStream")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc,Seconds(5))

  def main (args: Array[String]): Unit = {

    val data = ssc.textFileStream("hdfs://localhost:8020/user/cloudera/test")

    val words = data.flatMap(_.split(" ")).map(w => (w,1))
    val wordcounts = words.reduceByKey(_+_)
    wordcounts.print

    ssc.start
    ssc.awaitTermination()
  }
}
