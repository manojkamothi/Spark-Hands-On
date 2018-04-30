import org.apache.spark._
import org.apache.spark.streaming._


A StreamingContext object can also be created from an existing SparkContext object.

val sc = ...                // existing SparkContext
val ssc = new StreamingContext(sc, Seconds(1))

// Create a DStream that will connect to hostname:port, like localhost:9999
val lines = ssc.socketTextStream("localhost", 9999)

// Split each line into words
val words = lines.flatMap(_.split(" "))

// Count each word in each batch
val pairs = words.map(word => (word, 1))
val wordCounts = pairs.reduceByKey(_ + _)

// Print the first ten elements of each RDD generated in this DStream to the console
wordCounts.print()

ssc.start()             // Start the computation
ssc.awaitTermination()  // Wait for the computation to terminate


$ nc -lk 9999

Then, in a different terminal, you can start the example by using

Then, any lines typed in the terminal running the netcat server will be counted and printed on screen every second.