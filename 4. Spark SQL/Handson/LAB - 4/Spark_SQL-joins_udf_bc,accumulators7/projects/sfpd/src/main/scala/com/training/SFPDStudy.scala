package com.training

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object SFPDStudy {

  case class Incidents(incidentnum: String, category: String, description: String, dayofweek: String, date: String,
    time: String, pddistrict: String, resolution: String, address: String, X: Float, Y: Float, pdid: String)
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("SFPD")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val sfpdRDD = sc.textFile("file:/E:/balajiworkspace/Mapr/data/sfpd.csv").map(inc => inc.split(","))
    val sfpdCase = sfpdRDD.map(inc => Incidents(inc(0), inc(1), inc(2), inc(3), inc(4), inc(5),
      inc(6), inc(7), inc(8), inc(9).toFloat, inc(10).toFloat, inc(11)))

    val sfpdDF = sfpdCase.toDF()

    sfpdDF.registerTempTable("sfpd")

    sqlContext.sql("select * from sfpd").show

    //1. Top 5 Districts
    val incByDist = sfpdDF.groupBy("pddistrict").count.sort($"count".desc).show(5)

    val topByDistSQL = sqlContext.sql("SELECT pddistrict, count(incidentnum) AS inccount FROM sfpd GROUP BY pddistrict ORDER BY inccount DESC LIMIT 5")
    //2. What are the top ten resolutions?
    val top10Res = sfpdDF.groupBy("resolution").count.sort($"count".desc)
    top10Res.show(10)
    val top10ResSQL = sqlContext.sql("SELECT resolution, count(incidentnum) AS inccount FROM sfpd GROUP BY resolution ORDER BY inccount DESC LIMIT 10")
    //3. Top 3 categories
    val top3Cat = sfpdDF.groupBy("category").count.sort($"count".desc).show(3)
    val top3CatSQL = sqlContext.sql("SELECT category, count(incidentnum) AS inccount FROM sfpd GROUP BY category ORDER BY inccount DESC LIMIT 3")
    //4. Save the top 10 resolutions to a JSON file.
    top10ResSQL.toJSON.foreach(println) //saveAsTextFile("/user/user01/output")

    //1. define funciton 
    def getyear(s: String): String = {
      val year = s.substring(s.lastIndexOf('/') + 1)
      year
    }
    //2. register the function as a udf 
    sqlContext.udf.register("getyear", getyear _)

    //3. count inc by year
    val incyearSQL = sqlContext.sql("SELECT getyear(date), count(incidentnum) AS countbyyear FROM sfpd GROUP BY getyear(date) ORDER BY countbyyear DESC")
    incyearSQL.collect.foreach(println)

    //4. Category, resolution and address of reported incidents in 2014 
    val inc2014 = sqlContext.sql("SELECT category,address,resolution, date FROM sfpd WHERE getyear(date)='14'")
    inc2014.collect.foreach(println)

    //5. Vandalism only in 2014 with address, resolution and category
    val van2015 = sqlContext.sql("SELECT category,address,resolution, date FROM sfpd WHERE getyear(date)='15' AND category='VANDALISM'")
    van2015.collect.foreach(println)
    van2015.count
  }
}