import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object AccumulatorsTest {
  
  def main(args: Array[String]): Unit = {
    
     val conf = new SparkConf().setAppName("SparkAccumulatorBroadcastJoin").setMaster("local[*]")
     val sc = new SparkContext(conf)
     val purchaseInDelhi = sc.accumulator(0)
     
     val payRDD = sc.textFile("/user/cloudera/datasets/userInfo.txt")
     val payPair = payRDD.map(x => (x.split(",")(0),x))
	   val usrRDD = sc.textFile("/user/cloudera/datasets/payMents.txt")
     val usrPair = usrRDD.map(
       x => {
         if(x.split(",")(1)=="Delhi") {
           purchaseInDelhi += 1
         }
       (x.split(",")(0),x.split(",")(2))
       }
     )
     
      val usrMap = usrPair.collectAsMap()
     val r = payPair.map(v => (v._1,(usrMap(v._1),v._2)))

     r.foreach(println)
     r.saveAsTextFile("results")
     println("No of Delhi purchase " + purchaseInDelhi.value)
  }
}