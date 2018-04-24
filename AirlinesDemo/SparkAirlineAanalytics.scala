hdfs dfs -put /home/manoj/hadoop-work/Intellipat-Hadoop/Ecosystems_Excersize_MK/Spark/mk-handson/AirlinesProject/airports.csv /user/training/data_sets/


-> Using Databricks CSV parsing library

Open Spark shell with Databricks library


-----------------------------TRANSFORMATION START---------------------------------
spark-shell --packages com.databricks:spark-csv_2.10:1.0.3

val sqlContext = new org.apache.spark.sql.SQLContext(sc)
import sqlContext.implicits._ 

val airportDF = sqlContext.load("com.databricks.spark.csv", Map("path" -> "hdfs://localhost:8020/user/training/data_sets/airports.csv", "header" -> "true"))

airportDF.show

scala> airportDF.show
+---------+--------------------+--------------+----------------+---+----+---------+----------+--------+--------+---+--------------------+
|AirportID|                Name|          City|         Country|FAA|ICAO| Latitude| Longitude|Altitude|Timezone|DST|                  Tz|
+---------+--------------------+--------------+----------------+---+----+---------+----------+--------+--------+---+--------------------+
|        1|              Goroka|        Goroka|Papua New Guinea|GKA|AYGA|-6.081689|145.391881|    5282|      10|  U|Pacific/Port_Moresby|
|        2|              Madang|        Madang|Papua New Guinea|MAG|AYMD|-5.207083|  145.7887|      20|      10|  U|Pacific/Port_Moresby|
|        3|         Mount Hagen|   Mount Hagen|Papua New Guinea|HGU|AYMH|-5.826789|144.295861|    5388|      10|  U|Pacific/Port_Moresby|
|        4|              Nadzab|        Nadzab|Papua New Guinea|LAE|AYNZ|-6.569828|146.726242|     239|      10|  U|Pacific/Port_Moresby|
|        5|Port Moresby Jack...|  Port Moresby|Papua New Guinea|POM|AYPY|-9.443383| 147.22005|     146|      10|  U|Pacific/Port_Moresby|
|        6|          Wewak Intl|         Wewak|Papua New Guinea|WWK|AYWK|-3.583828|143.669186|      19|      10|  U|Pacific/Port_Moresby|
|        7|          Narsarsuaq|  Narssarssuaq|       Greenland|UAK|BGBW|61.160517|-45.425978|     112|      -3|  E|     America/Godthab|
|        8|                Nuuk|      Godthaab|       Greenland|GOH|BGGH|64.190922|-51.678064|     283|      -3|  E|     America/Godthab|
|        9|   Sondre Stromfjord|   Sondrestrom|       Greenland|SFJ|BGSF|67.016969|-50.689325|     165|      -3|  E|     America/Godthab|
|       10|      Thule Air Base|         Thule|       Greenland|THU|BGTL|76.531203|-68.703161|     251|      -4|  E|       America/Thule|
|       11|            Akureyri|      Akureyri|         Iceland|AEY|BIAR|65.659994|-18.072703|       6|       0|  N|  Atlantic/Reykjavik|
|       12|         Egilsstadir|   Egilsstadir|         Iceland|EGS|BIEG|65.283333|-14.401389|      76|       0|  N|  Atlantic/Reykjavik|
|       13|        Hornafjordur|          Hofn|         Iceland|HFN|BIHN|64.295556|-15.227222|      24|       0|  N|  Atlantic/Reykjavik|
|       14|             Husavik|       Husavik|         Iceland|HZK|BIHU|65.952328|-17.425978|      48|       0|  N|  Atlantic/Reykjavik|
|       15|          Isafjordur|    Isafjordur|         Iceland|IFJ|BIIS|66.058056|-23.135278|       8|       0|  N|  Atlantic/Reykjavik|
|       16|Keflavik Internat...|      Keflavik|         Iceland|KEF|BIKF|   63.985|-22.605556|     171|       0|  N|  Atlantic/Reykjavik|
|       17|      Patreksfjordur|Patreksfjordur|         Iceland|PFJ|BIPA|65.555833|   -23.965|      11|       0|  N|  Atlantic/Reykjavik|
|       18|           Reykjavik|     Reykjavik|         Iceland|RKV|BIRK|    64.13|-21.940556|      48|       0|  N|  Atlantic/Reykjavik|
|       19|        Siglufjordur|  Siglufjordur|         Iceland|SIJ|BISI|66.133333|-18.916667|      10|       0|  N|  Atlantic/Reykjavik|
|       20|      Vestmannaeyjar|Vestmannaeyjar|         Iceland|VEY|BIVM|63.424303|-20.278875|     326|       0|  N|  Atlantic/Reykjavik|
+---------+--------------------+--------------+----------------+---+----+---------+----------+--------+--------+---+--------------------+
only showing top 20 rows


airportDF.registerTempTable("airports")

------------------------------TRANSFORMATION ENDS-----------------------------------

------------------------------ACTION START--------------------------------------

=> Let's find out how many airports are there in South east part in our dataset

scala> sqlContext.sql("select AirportID, Name, Latitude, Longitude from airports where Latitude<0 and Longitude>0").collect.foreach(println)


=> We can do aggregations in sql queries on Spark We will find out how many unique cities have airports in each country
scala> sqlContext.sql("select Country, count(distinct(City)) from airports group by Country").collect.foreach(println)


=> What is average Altitude (in feet) of airports in each Country?

scala> sqlContext.sql("select Country , avg(Altitude) from airports group by Country").collect
res6: Array[org.apache.spark.sql.Row] = Array([Iceland,72.8], [Canada,852.6666666666666], [Greenland,202.75], [Papua New Guinea,1849.0])

scala> sqlContext.sql("select Country , avg(Altitude) from airports group by Country order by avg(Altitude) desc").collect
res7: Array[org.apache.spark.sql.Row] = Array([Papua New Guinea,1849.0], [Canada,852.6666666666666], [Greenland,202.75], [Iceland,72.8])

=> Now to find out in each timezones how many airports are operating?
scala> sqlContext.sql("select Tz , count(Tz) from airports group by Tz order by count(Tz) desc").collect.foreach(println)
[America/Toronto,48]                                                            
[America/Edmonton,27]
[America/Vancouver,19]
[America/Winnipeg,14]
[Atlantic/Reykjavik,10]
[America/Regina,10]
[America/Halifax,9]
[Pacific/Port_Moresby,6]
[America/St_Johns,4]
[America/Coral_Harbour,3]
[America/Godthab,3]
[America/Thule,1]
[America/Dawson_Creek,1]


=> We can also calculate average latitude and longitude for these airports in each country

scala> sqlContext.sql("select Country, avg(Latitude), avg(Longitude) from airports group by Country").collect.foreach(println)
[Iceland,65.0477736,-19.5969224]
[Greenland,67.22490275,-54.124131999999996]
[Canada,53.94868565185185,-93.950036237037]
[Papua New Guinea,-6.118766666666666,145.51532] 


=> Lets count how many different DSTs are there

scala> sqlContext.sql("select DST, count(distinct(DST)) from airports group by DST").collect.foreach(println)
[E,1]                                                                           
[A,1]
[N,1]
[U,1]



scala> sqlContext.sql("select AirportID, Name, Latitude, Longitude from airports where Latitude>0 and Longitude<0").collect.foreach(println)

scala> val NorthWestAirportsDF=sqlContext.sql("select AirportID, Name, Latitude, Longitude from airports where Latitude>0 and Longitude<0")


And save it to CSV file

scala> NorthWestAirportsDF.save("com.databricks.spark.csv", org.apache.spark.sql.SaveMode.ErrorIfExists, Map("path" -> "hdfs://localhost:8020/users/training/results/airportprojects","header"->"true"))

------------------------------ACTION ENDS-------------------------------------------