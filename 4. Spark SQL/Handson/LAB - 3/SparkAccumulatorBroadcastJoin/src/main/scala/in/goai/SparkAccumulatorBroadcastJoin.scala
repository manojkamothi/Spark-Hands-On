package in.goai
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


object  SparkAccumulatorBroadcastJoin  {

  def main(args: Array[String]) {

     val conf = new SparkConf().setAppName("SparkAccumulatorBroadcastJoin")
     val sc = new SparkContext(conf)

     val payRDD = sc.textFile("userInfo.csv")
     val payPair = payRDD.map(x => (x.split(",")(0),x))

     val usrRDD = sc.textFile("payments.csv")
     val usrPair = usrRDD.map(x =>(x.split(",")(0),x.split(",")(2)))

     val usrMap = usrPair.collectAsMap()
     val r = payPair.map(v => (v._1,(usrMap(v._1),v._2)))

     r.foreach(println)
     r.saveAsTextFile("results")

   }
}
